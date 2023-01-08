package icu.gieok.controller.admin.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.BoardService;
import icu.gieok.utils.CheckAdmin;
import icu.gieok.vo.BoardVO;

@RequestMapping(value="/admin")
@Controller
public class AdminBoardController {

	@Autowired
	private BoardService boardService;

	@Resource(name="uploadPath")
	private String uploadPath;

	static int board_no;
	
	private ModelAndView checkUser;
	
	// 관리자 여부 확인 
	public ModelAndView checkAdmin(HttpSession session) {
		
		CheckAdmin checkAdmin = new CheckAdmin(session);
		
		return checkAdmin.getCheckAdmin();
		
	}


	@GetMapping("/board_write")
	public ModelAndView board_write(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception{

		checkUser = checkAdmin(session);
		
		if(checkUser != null) {
			return checkUser;
		}
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		ModelAndView bw = new ModelAndView();
		bw.addObject("page", page);
		bw.setViewName("/admin/board_write");
		return bw;
	}

	@PostMapping("/board_write_ok")
	public ModelAndView board_write_ok(@RequestParam MultipartFile imgfile1, @RequestParam MultipartFile imgfile2,
			BoardVO b, HttpServletRequest request, HttpSession session) throws Exception {
		
		checkUser = checkAdmin(session);
		
		if(checkUser != null) {
			return checkUser;
		}
		

		String name = (String)session.getAttribute("name");
		int code = (int)session.getAttribute("code"); 

		b.setUser_code(code);
		b.setBoard_writer(name);

		if(b.getBoard_type().equals("notice")) {
			b.setBoard_type("1");
			boardService.noticeInsert(b);
		}else {
			String start_date = request.getParameter("start_date");
			String end_date= request.getParameter("end_date");

			String root = uploadPath + "event";

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			int date = cal.get(Calendar.DATE);
			String today = year+"-"+month+"-"+date;
			if(date < 10) {
				today = year+"-"+month+"-0"+date;
			}else {
				today = year+"-"+month+"-"+date;
			}
			
			File eventDir = new File(root+"/"+today);
			if(!(eventDir.exists())) {
				eventDir.mkdir();
			}

			String dir = root + "/" + today;

			File path = new File(dir);

			if(!(path.exists())) {
				path.mkdir();
			}

			Random rand = new Random();

			List<MultipartFile> list = new ArrayList<>();

			list.add(imgfile1);
			list.add(imgfile2);

			String[] fullFileNames = new String[2];

			for(int i=0; i<list.size(); i++) {
				fullFileNames[i] = list.get(i).getOriginalFilename();

				int index = fullFileNames[i].lastIndexOf(".");

				String ext = fullFileNames[i].substring(index);

				int num = rand.nextInt(100000000);

				String reFileName = year+"-"+month+"-"+date + "-" + num + ext;

				File saveFile = new File(dir, reFileName);

				try{
					list.get(i).transferTo(saveFile);
					if(i == 0) {
						b.setBoard_img1(reFileName);
					}if(i == 1) {
						b.setBoard_img2(reFileName);
					}
				}catch(IllegalStateException ie) {
					ie.printStackTrace();
				}catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}

			b.setBoard_type("2");
			b.setBoard_startDay(start_date);
			b.setBoard_endDay(end_date);

			boardService.eventInsert(b);
		}

		return new ModelAndView("redirect:/board_list");
	}


	@GetMapping("/board_edit")
	public ModelAndView board_edit(@RequestParam("no") int board_no, int page, BoardVO b, HttpSession session) {
		
		checkUser = checkAdmin(session);
		
		if(checkUser != null) {
			return checkUser;
		}

		b = boardService.getboardDetail(board_no);

		this.board_no = board_no;

		ModelAndView em = new ModelAndView();
		em.addObject("blist", b);
		em.addObject("no", board_no);
		em.addObject("page", page);
		em.setViewName("/admin/board_edit");

		return em;
	}

	@PostMapping("/board_edit_ok")
	public ModelAndView board_edit(BoardVO b, HttpServletRequest request, HttpSession session,
			@RequestParam(value="imgfile1", required=false) MultipartFile imgfile1,
			@RequestParam(value="imgfile2", required=false) MultipartFile imgfile2,
			int page, int no) {
		
		checkUser = checkAdmin(session);
		
		if(checkUser != null) {
			return checkUser;
		}
		
		b.setBoard_no(board_no);
		String content = b.getBoard_content();
		b.setBoard_content(content.replace("\n", "<br>"));
		
		if(b.getBoard_type().equals("notice")) {
			b.setBoard_type("1");
			boardService.noticeUpdate(b);
		}else {
			String start_date = request.getParameter("start_date");
			String end_date= request.getParameter("end_date");

			String root = uploadPath + "event";

			BoardVO blist = boardService.getboardDetail(board_no);
			
			if(imgfile1.getSize() <= 0) {
				b.setBoard_img1(blist.getBoard_img1());
			}else {
				b.setBoard_img1(imgUpload(1, imgfile1, blist, root).getBoard_img1());
			}
			if(imgfile2.getSize() <= 0) {
				b.setBoard_img2(blist.getBoard_img2());
			}else {
				b.setBoard_img2(imgUpload(2, imgfile2, blist, root).getBoard_img2());
			}

			b.setBoard_type("2");
			b.setBoard_startDay(start_date);
			b.setBoard_endDay(end_date);

			boardService.eventUpdate(b);
		}
		

		return new ModelAndView("redirect:/board_cont?no="+no+"&page="+page);
	}


	 @PostMapping("/board_del")
	 public ModelAndView board_del(@RequestParam List<Integer> check_num, String page, String board_sort, String search_option, String list_search, HttpSession session) {
      
      checkUser = checkAdmin(session);
      
      if(checkUser != null) {
         return checkUser;
      }
      
      if(check_num.size()!=0) {
         for(int i : check_num) {
            boardService.board_del(i);
         }
         ModelAndView m = new ModelAndView();
         m.addObject("msg", check_num.size()+"개의 게시물 삭제가 완료되었습니다!");
         m.addObject("url", "/board_list?page="+page+"&board_sort="+board_sort+"&search_option="+search_option+"&list_search="+list_search);
         m.setViewName("message");
         return m;
      }
      return null;
	 }
	
	@PostMapping("/event_end")
	public ModelAndView event_end(@RequestParam List<Integer> check_num, String page, String board_sort, String search_option, String list_search, HttpSession session) {
		
		checkUser = checkAdmin(session);

		if(checkUser != null) {
			return checkUser;
		}
		
		if(check_num.size()!=0) {
			for(int i: check_num) {
				boardService.board_eventEnd(i);
			}
			return new ModelAndView("redirect:/board_list?page="+page+"&board_sort="+board_sort+"&search_option="+search_option+"&list_search="+list_search);
		}
		return null;
	}

	public static BoardVO imgUpload(int i, MultipartFile file, BoardVO b, String root) {

		String regdate = b.getBoard_regDate().substring(0,10);
		String dir = root + "/" + regdate;

		List<MultipartFile> list = new ArrayList<>();
		list.add(file);

		String fullFileName = list.get(0).getOriginalFilename();
		int index = fullFileName.lastIndexOf(".");
		String ext = fullFileName.substring(index);
		String exFileName;
		String exFileExt;
		int exIndex;
		File exImg ;
		String reFileName;
		if(i == 1) {
			exFileName = b.getBoard_img1();
			exIndex = exFileName.lastIndexOf(".");
			exFileExt = b.getBoard_img1().substring(exIndex);
			if(ext.equals(exFileExt)) {
				reFileName = b.getBoard_img1();
			}else {
				reFileName = exFileName.substring(0,exIndex)+ext;
				exImg = new File(dir+"/"+b.getBoard_img1());
				exImg.delete();
			}
		}else {
			exFileName = b.getBoard_img2();
			exIndex = exFileName.lastIndexOf(".");
			exFileExt = b.getBoard_img2().substring(exIndex);
			if(ext.equals(exFileExt)) {
				reFileName = b.getBoard_img2();
			}else {
				reFileName = exFileName.substring(0, exIndex)+ext;
				exImg = new File(dir+"/"+b.getBoard_img2());
				exImg.delete();
			}
		}
		File saveFile = new File(dir, reFileName);
		try {
			list.get(0).transferTo(saveFile);
			if(i == 1) {
				b.setBoard_img1(reFileName);
			}if(i == 2){
				b.setBoard_img2(reFileName);
			}
		}catch(IllegalStateException ie) {
			ie.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return b;
	}//imgUpload()

}
