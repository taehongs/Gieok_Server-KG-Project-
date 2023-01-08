package icu.gieok.controller.member;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.MapService;
import icu.gieok.service.UserService;
import icu.gieok.utils.CheckMember;
import icu.gieok.utils.MailSendService;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.MessageVO;
import icu.gieok.vo.UserVO;

@RequestMapping(value="/member")
@Controller
public class MemberController {
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MapService mapService;
	
	@Autowired 
	private MailSendService mss;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private ModelAndView checkUser; 
	
	
	
	 
	
	// 회원 여부 확인 
	public ModelAndView checkMember(HttpSession session) {
		
		CheckMember checkMember = new CheckMember(session);
		
		return checkMember.getcheckMember();
		
	}
	
	
	@GetMapping("/join")
	public String userJoin() {
		
		return "/member/join";
	}
	
	@ResponseBody
	@PostMapping("/idDupCheck")
	public boolean idDupCheck(@RequestBody Map<String, String> map) {
		
		String id_input = map.get("id_input");
		boolean idDup = true;
		UserVO user = userService.idDupCheck(id_input);
		if(user==null) {
			idDup = false;
		}
		
		return idDup;
	}
	
	@PostMapping("/joinEmail")
	public ModelAndView userJoinEmail(UserVO user) {
		
		if(user.getUser_terms()==null) {
			user.setUser_terms("disagree");
		}else {
			user.setUser_terms("agree");
		}
		
		// 비밀번호 암호화
		String encode_pw = bCryptPasswordEncoder.encode(user.getUser_pw());
		user.setUser_pw(encode_pw);
		
		userService.userInsert(user);

        //임의의 authKey 생성 & 이메일 발송
		
		String email = user.getUser_email()+"@"+user.getUser_domain();
        String user_key = mss.sendAuthMail(email);
        user.setUser_key(user_key);

        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", user.getUser_id());
        map.put("authKey", user.getUser_key());

      //DB에 authKey 업데이트
      userService.updateAuthKey(map);
      
      ModelAndView mv = new ModelAndView();

      String msg = "인증메일이 발송되었습니다!";
      String url = "/";
      
      mv.addObject("msg", msg);
      mv.addObject("url", url);
      mv.setViewName("message");
      
      return mv;
	}
	
	@GetMapping("/joinOK")
	public ModelAndView userJoinOK(@RequestParam Map<String, String> map) {
		
		String user_email = map.get("email").split("@")[0];
		String user_domain = map.get("email").split("@")[1];
		
		map.put("user_email", user_email);
		map.put("user_domain", user_domain);
		
		userService.updateAuth(map);
		
		ModelAndView mv = new ModelAndView();
		String msg = "인증이 완료되었습니다! 로그인을 해주세요:)";
		String url = "http://gieok.icu/login";
		mv.addObject("msg", msg);
		mv.addObject("url", url);
		mv.setViewName("message");
		
		return mv;
	}
	
	
	@GetMapping("/logout")
	public String userLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/";
	}
	
	  /* =====// 회원 정보 수정 페이지 이동 //===== */
    @GetMapping("/inform_edit")
    public ModelAndView userInformEdit(HttpSession session) {
    	
    	checkUser = checkMember(session);
    	
    	if(checkUser != null) {
    		return checkUser;
    	}

        String user_id = (String) session.getAttribute("id");
        UserVO user = userService.userSelect(user_id);

        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.setViewName("/member/inform_edit");

        return mv;
    }

    /* =====// 회원 정보 수정 //===== */
    @ResponseBody
    @PostMapping("/inform_edit")
    public UserVO userInformEditOK(@RequestParam("profile") MultipartFile file,
            @ModelAttribute UserVO user, HttpSession session) {
        /**
         * BindingResult -> 프로필 이름값"profile", 멀티파일 file을 연결한 결과값
         * ==> 연결 성공 여부 확인
         * :: 검증시 오류?
         */

        // 파일 객체 생성
        String user_profile = (String) session.getAttribute("profile");

        // if(result.hasErrors()) { // 매칭되지않으면(에러발생시)
        // }
        user.setUser_profile(user_profile); // 로그인한 사용자의 프로필 이미지

        int user_code = (int) session.getAttribute("code");
        String user_id = (String) session.getAttribute("id");

        if (file.getSize() > 0) {
            try {
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

                String reFileName = user_code + user_id + "profile" + extension;
                File user_image = new File(uploadPath + "profile", reFileName); // 경로, 파일명

                file.transferTo(user_image); // 멀티파트 파일로 프로필 이름값이 담긴 사진을 불러와서 옮겨줌 -> user_image
                user.setUser_profile(reFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // 로그인 할때 => default.jpg
        // 수정 => image1.jpg

        int re = userService.updateUserInform(user);

        if (re != 1) {
            user = null;
        }

        session.setAttribute("profile", user.getUser_profile());
        return user;
    }

    /* =====// 비밀번호 변경 //===== */
    @GetMapping("/pw_edit")
    public ModelAndView userPwEdit(HttpSession session) {
    	
    	checkUser = checkMember(session);
    	
    	if(checkUser != null) {
    		return checkUser;
    	}
    	
    	
        return new ModelAndView("/member/pw_edit");
    }

    @PostMapping("/pw_editOK")
    public ModelAndView userPwEditOK(@RequestParam String pw,
            @RequestParam String user_pw, HttpSession session) {

    	checkUser = checkMember(session);
    	
    	if(checkUser != null) {
    		return checkUser;
    	}
    	
    	
        String user_id = (String) session.getAttribute("id");

        Map<String, String> map = new HashMap<>();
        map.put("user_id", user_id);

        String msg = "";
        String url = "";

        UserVO user = userService.userSelect(user_id);

        if (bCryptPasswordEncoder.matches(pw, user.getUser_pw())) {
            if (bCryptPasswordEncoder.matches(user_pw, user.getUser_pw())) {
                msg = "기존과 다른 비밀번호를 입력해주세요";
                url = "/member/pw_edit";
            } else {
            	
            	// 비밀번호 암호화
        		String encode_pw = bCryptPasswordEncoder.encode(user_pw);
        		map.put("user_pw", encode_pw);
        		
                int result = userService.updateUserPw(map);

                if (result == 1) {
                    msg = "변경 완료";
                    url = "/";
                } else {
                    msg = "시스템 오류";
                    url = "/member/pw_edit";
                }
            }
        } else {
            msg = "비밀번호가 다릅니다";
            url = "/member/pw_edit";
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", msg);
        mv.addObject("url", url);
        mv.setViewName("message");

        return mv;
    }

    /* =====// 아이디, 비밀번호 찾기 //===== */
    @GetMapping("/find_id_pw")
    public String userIdPwFind(HttpSession session) {
    	
        return "/member/find_id_pw";
    }

    @ResponseBody
    @PostMapping("/find_id_pwOK")
    public Map<String, String> userIdPwFindOK(@RequestParam Map<String, String> map) {
    	
    	
    	
    	if(checkUser != null) {
    		String error = "세션이 만료되었습니다. 다시 로그인 해주세요";
    		Map<String, String> errorMap = new HashMap<>();
    		errorMap.put("msg", error);
    		errorMap.put("fail", "1");
    		return errorMap;
    	}

        String user_id = map.get("user_id"); // 아이디찾기, 비밀번호찾기 구분

        String msg = "";

        UserVO user = userService.userIdPwFind(map);
        if (user == null) {
            msg = "해당하는 정보가 없습니다.";
        } else {
            if (user_id == null) { // 아이디 찾기
                msg = "아이디는 " + user.getUser_id().substring(0, user.getUser_id().length()/2) + "*** 입니다";
            } else { // 비밀번호 찾기
            	String user_email = map.get("user_email");
            	String user_domain = map.get("user_domain");
            	String full_email = user_email+"@"+user_domain;
            	String temp_pw = mss.resetPw(full_email);
            	String encode_pw = bCryptPasswordEncoder.encode(temp_pw);

            	System.out.println(temp_pw);
            	System.out.println("====================================================");
            	System.out.println(encode_pw);
            	System.out.println(bCryptPasswordEncoder.encode(temp_pw));
            	System.out.println("====================================================");
            	
            	
            	
            	map.put("user_pw", encode_pw);
            	int res = userService.updateUserPw(map);
            	if(res==1) {
            		msg = full_email+"로 임시 비밀번호를 발송했습니다 :)";
            	}else {
            		msg = "시스템 오류! 관리자에게 문의하세요";  
            	}
            }
        }
        Map<String, String> result = new HashMap<>();
        result.put("msg", msg);

        return result;
    }

    /* =====// 회원 탈퇴 //===== */
    @GetMapping("/leave")
    public ModelAndView userLeave(HttpSession session) {
    	checkUser = checkMember(session);
    	
    	if(checkUser != null) {
    		return checkUser;
    	}
    	
        return new ModelAndView("/member/leave");
    }

    @PostMapping("/leaveOK") // name이 user_pw인 값을 찾아서 String 'user_pw'에 넣음(변수명을 기준으로)
    public ModelAndView userLeaveOK(
            @RequestParam String user_pw,
            HttpSession session) {
    	
    	checkUser = checkMember(session);
    	
    	if(checkUser != null) {
    		return checkUser;
    	}
    	
    	
        // @RequestParam("네임값")
        String user_id = (String) session.getAttribute("id");

        String msg = "";
        String url = "";

        UserVO user = userService.userSelect(user_id);

        if (bCryptPasswordEncoder.matches(user_pw, user.getUser_pw())) {
        	int user_code = user.getUser_code();
            int result = userService.userDelete(user_id);
            if (result == 1) {
            	
            	int res = userService.changeStateLeave(user_code);
            	
            	if(res >= 0) {
            		msg = "탈퇴 완료";
            		url = "/";
            		session.invalidate(); // 세션값 삭제
            	}else {
            		 msg = "시스템 오류";
                     url = "/member/leave";
            	}
            	
            } else {
                msg = "시스템 오류";
                url = "/member/leave";
            }
        } else {
            msg = "비밀번호가 다릅니다";
            url = "/member/leave";
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", msg);
        mv.addObject("url", url);
        mv.setViewName("message");

        return mv;
    }
    
    /* =====// 이메일 중복확인 //===== */
    @ResponseBody
    @PostMapping("/emailDupCheck")
    public boolean emailDupCheck(@RequestBody Map<String, String> map) {

        boolean emailDup = true;
        int user = userService.emailDupCheck(map);

        if (user == 0) {
            emailDup = false;
        }

        return emailDup;

    }
    
    // 내가 쓴 리뷰
    @GetMapping("/my_review")
    public ModelAndView myReview(HttpSession session, HttpServletRequest request) {
    	
    	
    	checkUser = checkMember(session);
    	if(checkUser!=null) {
    		return checkUser;
    	}
    	
    	int page = 1;
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int user_code = (int)session.getAttribute("code");
		
		int startRow = (page-1)*10+1;
		int endRow = page*10;
		
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("user_code", user_code);
		
		int totalCount = userService.countMyReview(map);
		
		int totalPage = (totalCount/10);
		if((totalCount%10)!=0) {
			totalPage++;
		}
		
		int startPage = 0;
		int endPage = 0;
		
		
		if(page/10<1 || page==10) {
			startPage = 1;
			endPage = 10;
			if(totalPage<10) {
				endPage = totalPage;
			}
		}else {
			
			startPage = ((page/10)*10)+1;
			endPage = startPage+9;
			
			if(endPage>=totalPage) {
				endPage = totalPage;
			}
			
		}
    	
    	List<AttrReviewVO> review_list = userService.getMyReviewList(map);
    	
    	for(AttrReviewVO review : review_list) {
    		String attr_name = mapService.getAttrName(review.getAttr_code());
    		review.setRev_date(review.getRev_date().substring(0, 10));
    		review.setAttr_name(attr_name);
    	}
    	
    	ModelAndView mv = new ModelAndView();
    	
    	mv.addObject("review_list", review_list);
    	mv.addObject("page", page);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("totalPage", totalPage);
		mv.addObject("totalCount", totalCount);
    	mv.setViewName("/member/my_review");
    	
    	return mv;
    }

    
    @ResponseBody
    @PostMapping("/review_detail")
    public AttrReviewVO reviewDetail(@RequestBody Map<String, String> code, HttpSession session) {
    	
    	checkUser = checkMember(session);
    	if(checkUser!=null) {
    		return null;
    	}
    	
    	
    	int user_code = (int)session.getAttribute("code");
    	int rev_code = Integer.parseInt(code.get("rev_code"));
    	
    	Map<String, Integer> map = new HashMap<>();
    	
    	map.put("user_code", user_code);
    	map.put("rev_code", rev_code);

    	AttrReviewVO review = userService.reviewDetail(map);
    	
    	return review;
    }

    @ResponseBody
    @PostMapping("/review_update")
    public Map<String, String> reviewUpdate(@RequestParam("image") MultipartFile file, AttrReviewVO review,
			 								HttpSession session) throws IllegalStateException, IOException {
    	
    	checkUser = checkMember(session);
    	if(checkUser!=null) {
    		return null;
    	}
    	
    	int user_code = (int)session.getAttribute("code");
    	int rev_code = review.getRev_code();
    	
    	Map<String, Integer> map = new HashMap<>();
    	
    	map.put("user_code", user_code);
    	map.put("rev_code", rev_code);
    	
    	AttrReviewVO prevReview = userService.reviewDetail(map);
    	
    	review.setRev_writer(prevReview.getRev_writer());
    	review.setUser_code(prevReview.getUser_code());
    	
    	if(prevReview.getRev_img()==null) {
    		review.setRev_img("");
    	}else {
    		review.setRev_img(prevReview.getRev_img());
    	}
    	
    	if(file.getSize()>0) {

    		String dir = uploadPath + "review" + "/" + prevReview.getAttr_code();
    		File delFile = new File(dir, review.getRev_img());
    		if((delFile.exists())) {
    			delFile.delete();
    		}
    		
       		int index = file.getOriginalFilename().lastIndexOf(".");
    		String ext = file.getOriginalFilename().substring(index);
    		String prevFileName = "";
    		if(prevReview.getRev_img()!=null) {
    			
    			prevFileName = review.getRev_img().substring(0, review.getRev_img().lastIndexOf("."));
    		}else {
    			
    			Random rand = new Random();
        		int num = rand.nextInt(1000000000);
    			prevFileName = review.getRev_writer() + "-" + review.getUser_code() + "-" + num 	;
    		}
    		
    		String reFileName = prevFileName + ext;
    		
    		File saveFile = new File(dir, reFileName);
    		file.transferTo(saveFile);
    		review.setRev_img(reFileName);
    	}
    	
    	int res = userService.reviewUpdate(review);
    	
    	Map<String, String> result = new HashMap<>();
    	String msg = "";
    	String url = "";
    	
    	if(res==1) {
    		msg = "리뷰가 수정되었습니다!";
    		url = "/member/my_review?page=";
    	}
    	
    	result.put("msg", msg);
    	result.put("url", url);
    	
    	return result;
    }
    
    @ResponseBody
    @PostMapping("/review_delete")
    public Map<String, String> reviewdelete(@RequestBody Map<String, String> code, HttpSession session) {
    	
    	checkUser = checkMember(session);
    	if(checkUser!=null) {
    		return null;
    	}
    	
    	int user_code = (int)session.getAttribute("code");
    	int rev_code = Integer.parseInt(code.get("rev_code"));
    	int page = Integer.parseInt(code.get("page"));
    	
    	Map<String, Integer> map = new HashMap<>();
    	
    	map.put("user_code", user_code);
    	map.put("rev_code", rev_code);
    	
    	int res = userService.reviewDelete(map);

    	Map<String, String> result = new HashMap<>();
    	String msg = "";
    	String url = "";
    	
    	if(res==1) {
    		msg = "리뷰가 삭제되었습니다!";
    		url = "/member/my_review?page="+page;
    	}
    	
    	result.put("msg", msg);
    	result.put("url", url);
    	
    	return result;
    }

    
    // 메세지 보내기 
    @ResponseBody
    @PostMapping("/sendMessage")
    public Map<String, String> sendMessage(@RequestBody Map<String, String> map, HttpSession session) {
    
    	Map<String,String> result = new HashMap<>();
    	
    	checkUser = checkMember(session);
    	if(checkUser!=null) {
    		result.put("msg", "세션이 만료되었습니다!");
    		result.put("url", "/login");
    		
    		return result;
    	}
    	
    	String message_sender = (String)session.getAttribute("id");
    	String message_receiver = map.get("message_receiver");
    	String message_title = map.get("message_title");
    	String message_content = map.get("message_content");
    	
    	MessageVO message = new MessageVO();
    	message.setMessage_sender(message_sender);
    	message.setMessage_receiver(message_receiver);
    	message.setMessage_title(message_title);
    	message.setMessage_content(message_content);
    	
    	int res = userService.sendMessage(message);
    	
    	String msg = "";
    	
    	if(res==1) {
    		msg = "메세지를 보냈습니다 :)";
    	}else {
    		msg = "시스템 오류! 관리자에게 문의하세요:(";
    	}
    	
    	result.put("msg", msg);
    	
    	
    	return result;
    }
    
    // 내 메세지
    @GetMapping("/my_message")
    public ModelAndView myMessage(HttpSession session, String category, String keyword) {
    	
    	checkUser = checkMember(session);
    	if(checkUser!=null) {
    		return checkUser;
    	}
    	
    	Map<String, String> map = new HashMap<>();
    	
    	String message_receiver = (String)session.getAttribute("id");

    	if(category==null) {
    		category = "all";
    	}
    	
    	if(keyword==null) {
    		keyword = "";
    	}
    	
    	
    	map.put("message_receiver", message_receiver);
    	map.put("category", category);
    	map.put("keyword", keyword);
    	
    	List<MessageVO> myMessage = userService.getMyMessage(map);
    	
    	ModelAndView mv = new ModelAndView();
    	mv.addObject("myMessage", myMessage);
    	mv.addObject("category", category);
    	mv.addObject("keyword", keyword);
    	mv.setViewName("/member/my_message");
    	
    	return mv;
    	
    }
    
    @ResponseBody
    @PostMapping("/updateMessageRead")
    public int updateMessageRead(HttpSession session, @RequestBody Map<String, Object> map) {
    	
    	checkUser = checkMember(session);
    	if(checkUser != null) {
    		return -1;
    	}
    	
    	int res = userService.updateMessageRead(map);
    	
    	if(res==1 || res==0) {
    		String message_receiver = (String)session.getAttribute("id");
    		map.put("message_receiver", message_receiver);
    		res = userService.countUnreadMessage(map);
    		session.setAttribute("msgCount", res);
    	}else {
    		res = -2;
    	}
    	
    	return res;
    	
    }
    
    // 메세지 삭제
    @ResponseBody
    @PostMapping("/deleteMessage")
    public Map<String, String> deleteMessage(@RequestBody Map<String, Integer[]> map, HttpSession session) {
    	
    	Map<String, String> result = new HashMap<>();	
    	checkUser = checkMember(session);
    	if(checkUser!=null) {
    		result.put("msg", "세션이 만료되었습니다!");
    		result.put("url", "/login");
    	}
    	
    	
    	
    	Integer[] message_no = map.get("message_no");
    	
    	int res = userService.deleteMessage(message_no);
    	String msg="";
    	String url="";
    	
    	if(res>0) {
    		msg = "총 " + res + "개의 메세지가 삭제되었습니다!";
    		url = "/member/my_message";
    	}
    	
    	result.put("msg", msg);
    	result.put("url", url);
    	
    	return result;
    }
    
    
	

}
