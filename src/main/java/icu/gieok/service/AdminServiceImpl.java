package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.AdminDAO;
import icu.gieok.vo.AttrReviewReportVO;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.UserVO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO adminDAO;

	
	// 흥식's AREA
	@Override
	public int userListCount(Map<String, Object> map) {
		return adminDAO.userListCount(map);
	}

	@Override
	public List<UserVO> getUserList(Map<String, Object> map) {
		return adminDAO.getUserList(map);
	}

	@Override
	public int adminListCount(Map<String, Object> map) {
		return adminDAO.adminListCount(map);
	}

	@Override
	public List<UserVO> getAdminList(Map<String, Object> map) {
		return adminDAO.getAdminList(map);
	}

	@Override
	public int addAdmin(String user_id) {
		return adminDAO.addAdmin(user_id);
	}

	@Override
	public int deleteAdmin(String user_id) {
		return adminDAO.deleteAdmin(user_id);
	}

	@Override
	public UserVO getAdmin(String user_id) {
		return adminDAO.getAdmin(user_id);
	}

	// 진혁's AREA
	@Override
	public List<AttrReviewReportVO> getReviewReportList(Map<String, Object> map) {
		return adminDAO.getReivewReportList(map);
	}
	
	@Override
	public List<BoardLikeReportVO> getBoardReportList(Map<String, Object> map) {
		return adminDAO.getBoardReportList(map);
	}
	
	@Override
	public String getReportUser(int user_code) {
		return adminDAO.getReportUser(user_code);
	}
	
	@Override
	public AttrReviewVO getBadReview(int rev_code) {
		return adminDAO.getBadReview(rev_code);
	}
	
	@Override
	public BoardVO getBadBoard(int board_no) {
		return adminDAO.getBadBoard(board_no);
	}
	
	@Override
	public int countReport(String type) {
		return adminDAO.countReport(type);
	}
	
	@Override
	public int reviewReportDelete(int rev_code) {
		return adminDAO.reviewReportDelete(rev_code);
	}
	
	
}
