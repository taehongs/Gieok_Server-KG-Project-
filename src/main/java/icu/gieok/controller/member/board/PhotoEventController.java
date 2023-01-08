package icu.gieok.controller.member.board;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.MapService;
import icu.gieok.service.PhotoService;
import icu.gieok.utils.CheckMember;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;

@Controller
public class PhotoEventController {

    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private MapService mapService;

    @Resource(name = "uploadPath")
    private String uploadPath;
    
    private ModelAndView checkUser;
	
	// 회원 여부 확인 
	public ModelAndView checkMember(HttpSession session) {
		
		CheckMember checkMember = new CheckMember(session);
		
		return checkMember.getcheckMember();
		
	}

    /* =====// 게시글 업로드 //===== */
    @GetMapping("/photo_event_upload")
    public ModelAndView photoEventUpload(HttpSession session) {
    	
		checkUser = checkMember(session);
		
		if(checkUser != null) {
			return checkUser;
		}
    	
        return new ModelAndView("/member/photo_event_upload");
    }

    @ResponseBody
    @PostMapping("/photo_event_upload")
    public Map<String, String> photoEventUploadOK(@RequestParam("img") MultipartFile file,
            										@ModelAttribute BoardVO board, HttpSession session) {
    	
        Map<String, String> map = new HashMap<>();
        String msg = "";
        String url = "";
    	
    	
		checkUser = checkMember(session);
		
		if(checkUser != null) {
			msg = "세션이 만료되었습니다! 다시 로그인 해주세요!";
			url = "/login";
		}
    	
    	
        Calendar now = Calendar.getInstance();
        String time = now.get(Calendar.DAY_OF_MONTH) + "" + now.get(Calendar.HOUR) + "" + now.get(Calendar.MINUTE) + ""
                + now.get(Calendar.SECOND);

        int user_code = (int) session.getAttribute("code");
        String user_id = (String) session.getAttribute("id");

        board.setUser_code(user_code);
        board.setBoard_writer(user_id);

        int re = 0;

        if (file.getSize() > 0) {
            try {
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

                String reFileName = user_code + user_id + "event" + time + extension;
                File event_photo = new File(uploadPath + "event", reFileName);

                file.transferTo(event_photo);
                board.setBoard_img(reFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            re = photoService.eventPhotoInsert(board);
        }

        if (re == 1) {
            msg = "등록되었습니다";
            url = "/photo_event_list";
        } else {
            url = "/photo_event_upload";
        }

      
        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    /* =====// 게시판 목록 //===== */
    @GetMapping("/photo_event_list")
    public ModelAndView photoEventList(HttpSession session,
            HttpServletRequest request, String sortBy, String category, String keyword) {

        int page = 1;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        
        int startRow = (page - 1) * 12 + 1;
        int endRow = page * 12;
        
        if(sortBy == null) {
            sortBy = "board_regDate";
        }
        
        if(category == null) {
            category = "";
        }
        
        if(keyword == null) {
            keyword = "";
        }
        
        Map<String, Object> map = new HashMap<>();
        map.put("startRow", startRow);
        map.put("endRow", endRow);
        map.put("category", category);
        map.put("keyword", keyword);
        map.put("sortBy", sortBy);

        int totCnt = photoService.boardListCount(map);
        int totPage = totCnt / 12;
        
        if(totCnt % 12 != 0) {
            totPage++;
        }
        
        int startPage = 0;
        int endPage = 0;
        
        if(page / 10 < 1 || page == 10) {
            startPage = 1;
            endPage = 10;
            
            if(totPage < 10) {
                endPage = totPage;
            }
        } else {
            startPage = page / 10 * 10 + 1;
            endPage = startPage + 9;
            
            if(endPage >= totPage) {
                endPage = totPage;
            }
        }
        
        List<BoardVO> list = photoService.getBoardList(map);
        Map<String, Integer> likeMap = new HashMap<>(); 
        // user_code가 없을 때 처리 
        if(session.getAttribute("code")!=null) {
        	int user_code = (int)session.getAttribute("code");
        	likeMap.put("user_code", user_code);
        }
        
        
        if(list.size() > 0) {
            for(BoardVO board : list) {
                board.setBoard_regDate(board.getBoard_regDate().substring(0, 10));
                likeMap.put("board_no", board.getBoard_no());
                if(session.getAttribute("code")!=null) {
                	String like = photoService.getPhotoEventLikeCheck(likeMap);
                	board.setCheck_like(like);
                }
            }
        }
        
        ModelAndView mv = new ModelAndView();
        
        mv.addObject("board_list", list);
        mv.addObject("page", page);
        mv.addObject("startPage", startPage);
        mv.addObject("endPage", endPage);
        mv.addObject("totCnt", totCnt);
        mv.addObject("totPage", totPage);
        mv.addObject("sortBy", sortBy);
        mv.addObject("category", category);
        mv.addObject("keyword", keyword);
        mv.setViewName("/member/photo_event_list");
        
        return mv;
    }

    /* =====// 게시글 상세 //===== */
    @GetMapping("/photo_event_detail")
    public ModelAndView photoEventDetail(HttpSession session,
            @RequestParam("photo_no") int board_no, BoardVO board) {
        
        Map<String, Object> map = new HashMap<>();
        
        checkUser = checkMember(session);
        
        if(checkUser != null) {
           return checkUser;
        }
        int user_code = (int) session.getAttribute("code");
        
        map.put("user_code", user_code);
        map.put("board_no", board_no);
        
        BoardLikeReportVO boardLikeReport = photoService.getBoardLikeReport(map);
        
        int re = photoService.updateBoardHit(board_no);
        board = photoService.getBoardDetail(board_no);
        
        String province_id = board.getBoard_location();
        String province_name = mapService.getProvinceName(province_id);
        board.setBoard_location(province_name);
        
        ModelAndView mv = new ModelAndView();
        mv.addObject("board", board);
        mv.addObject("boardLikeReport", boardLikeReport);
        mv.setViewName("/member/photo_event_detail");
        
        return mv;
    }
    
    
    
    /* =====// 게시글 수정 //===== */
    @GetMapping("/photo_event_update")
    public ModelAndView photoEventUpdate(
            HttpSession session,
            @RequestParam("photo_no") int board_no, BoardVO board) {
        
        checkUser = checkMember(session);
        
        if(checkUser != null) {
            return checkUser;
        }
        
        board = photoService.getBoardDetail(board_no);

        ModelAndView mv = new ModelAndView();
        mv.addObject("board", board);
        mv.setViewName("/member/photo_event_update");
        
        return mv;
    }
    
    @ResponseBody
    @PostMapping("/photo_event_update")
    public Map<String, String> photoEventUpdateOK(
            @RequestParam("img") MultipartFile file,
            @ModelAttribute BoardVO board, HttpSession session) {
        
        Map<String, String> map = new HashMap<>();
        String msg = "";
        String url = "";
        
        checkUser = checkMember(session);
        
        if(checkUser != null) {
            msg = "세션이 만료되었습니다! 다시 로그인 해주세요!";
            url = "/login";
        }
        
        Calendar now = Calendar.getInstance();
        String time = now.get(Calendar.DAY_OF_MONTH) + "" + now.get(Calendar.HOUR) + "" + now.get(Calendar.MINUTE) + ""
                + now.get(Calendar.SECOND);

        int user_code = (int) session.getAttribute("code");
        String user_id = (String) session.getAttribute("id");
        
        BoardVO board2 = photoService.getBoardDetail(board.getBoard_no());
        
        board.setBoard_img(board2.getBoard_img());

        int re = 0;

        if (file.getSize() > 0) {
            try {
            	
            	File delFile = new File(uploadPath + "event", board.getBoard_img());
            	
            	if(delFile.exists()) {
            		delFile.delete();
            	}
            	
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                
                String reFileName = user_code + user_id + "event" + time + extension;
                File event_photo = new File(uploadPath + "event", reFileName);

                file.transferTo(event_photo);
                board.setBoard_img(reFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        re = photoService.updatePhotoEvent(board);

        if (re == 1) {
            msg = "수정되었습니다";
            url = "/photo_event_list";
        } else {
            url = "/photo_event_update?photo_no=" + board.getBoard_no();
        }
      
        map.put("msg", msg);
        map.put("url", url);

        return map;
    }
    
    /* =====// 게시물 삭제 //===== */
    @GetMapping("/photo_event_delete")
    public ModelAndView photoEventDelete(
            HttpSession session,
            @RequestParam("photo_no") int board_no, BoardVO board) {
        
        checkUser = checkMember(session);
        
        if(checkUser != null) {
            return checkUser;
        }

        String msg = "";
        
        int result = photoService.deletePhotoEvent(board_no);
        
        if(result == 1) {
            msg = "삭제 완료";
        } else {
            msg = "삭제 실패";
        }
        
        
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", msg);
        mv.addObject("url", "/photo_event_list");
        mv.setViewName("message");
        
        return mv;
    }
    
    /* =====// 좋아요 // ===== */
    @ResponseBody // 맵일때 
    @PostMapping("/photo_event_detailLike")
    public Map<String, Object> photoEventLike(
            HttpSession session,
            @RequestBody Map<String, String> code) {
        
    	Map<String, Object> map = new HashMap<>();
    	
    	String msg = "";
    	String url = "";
        
    	checkUser = checkMember(session);
        
        if(checkUser != null) {
            msg = "세션이 만료되었습니다! 다시 로그인 해주세요!";
            url = "/login";
            map.put("msg", msg);
            map.put("url", url);
            
            return map;
        }
        
        // 좋아요를 누를 때
        // 유저 code, 게시판 no가 넘어가고
        // 해당 컬럼 like 혹은 report값이 변경됨
        // board_like값도 변경
        
        int user_code = (int) session.getAttribute("code");
        int board_no = Integer.parseInt(code.get("board_no"));
        
        map.put("board_no", board_no);
        map.put("user_code", user_code);
        
        // 좋아요, 신고 현황 가져올때 값이 없으면 새로 만든 후 가져옴
        BoardLikeReportVO boardLikeReport = photoService.getBoardLikeReport(map);
        if(boardLikeReport == null) {
            int result = photoService.boardLikeReportInsert(map);
            
            if(result == 1) {
                boardLikeReport = photoService.getBoardLikeReport(map);
            } 
        }
        
        String like_check = boardLikeReport.getBoard_like();
        map.put("check", like_check); // 좋아요가 눌려있는지 안눌려있는지
        
        photoService.updatePhotoEventLike(map);
        photoService.updatePhotoEventLikeCheck(map);

        if(like_check.equals("Y")) {
            msg = "좋아요가 취소되었습니다";
        } else if(like_check.equals("N")) {
            msg = "좋아요를 눌렀습니다";
        }
        
        map.put("msg", msg);
        
        return map;
    }
    
    /* ===== 신고 ===== */
    @ResponseBody // 맵일때 
    @PostMapping("/photo_event_detailReport")
    public Map<String, Object> photoEventReport(
            HttpSession session,
            @RequestBody Map<String, String> code) {
        
        checkUser = checkMember(session);
        
        Map<String, Object> map = new HashMap<>();
        
        String msg = "";
        String url = "";
        
        if(checkUser != null) {
            msg = "세션이 만료되었습니다! 다시 로그인 해주세요!";
            url = "/login";
            map.put("msg", msg);
            map.put("url", url);
            
            return map;
        }
        
        int user_code = (int) session.getAttribute("code");
        int board_no = Integer.parseInt(code.get("board_no"));
        
        map.put("board_no", board_no);
        map.put("user_code", user_code);
        

        
        BoardLikeReportVO boardLikeReport = photoService.getBoardLikeReport(map);
        if(boardLikeReport == null) {
        	
            // 신고 당하는 글의 작성자
            int bad_member = photoService.getBoardDetail(board_no).getUser_code();
            map.put("bad_member", bad_member);
        	
        	String report_type = code.get("report_type");
        	map.put("report_type", report_type);
        	
            int result = photoService.boardLikeReportInsert(map);
            
            if(result == 1) {
                boardLikeReport = photoService.getBoardLikeReport(map);
            } 
        }
        
        String report_check = boardLikeReport.getBoard_report();
        String like_check = boardLikeReport.getBoard_like();
        
        map.put("check", like_check);
        
        if(report_check.equals("Y")) {
        	msg = "이미 신고처리된 게시물입니다";
        } else if(report_check.equals("N")) {
        	photoService.updatePhotoEventReportCheck(map);
        	photoService.updatePhotoEventReport(map);
        	
        	if(like_check.equals("Y")) {
        		photoService.updatePhotoEventLike(map);
        		photoService.updatePhotoEventLikeCheck(map);
        	}
        	
        	msg = "게시물이 신고처리되었습니다";
        }
        
        map.put("msg", msg);
        
        return map;
    }
    
    @PostMapping("/photo_event_listDelete")
	public ModelAndView photoEventListDelete(
			HttpSession session,
			@RequestParam List<Integer> photo_no, int page,
			String sortBy, String category, String keyword) {
		
		if(checkUser != null) {
			return null;
		}
		
		List<BoardVO> list = photoService.selectPhotoEventList(photo_no);
		
		int listSize = photo_no.size();
		int result = photoService.deletePhotoEventList(photo_no);
		
		String msg = "";
		
		if(listSize == result) {
			msg = "정상적으로 삭제되었습니다";
		} else {
			msg = "삭제 실패";
		}
		
//		String url = "/photo_event_list?page=" + page + "&sortBy=" + sortBy + "&category=" + category + "&keyword=" + keyword;
		String url = "/photo_event_list?page=" + page;

		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", msg);
		mv.addObject("url", url);
		mv.setViewName("message");
		
		return mv;
	}
}
