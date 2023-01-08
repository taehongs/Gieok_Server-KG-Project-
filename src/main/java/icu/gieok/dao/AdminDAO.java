package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.AttrReviewReportVO;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.UserVO;

public interface AdminDAO {

	// 흥식's AREA
	int userListCount(Map<String, Object> map);

	List<UserVO> getUserList(Map<String, Object> map);
	
	int adminListCount(Map<String, Object> map);
	
	List<UserVO> getAdminList(Map<String, Object> map);
	
	int addAdmin(String user_id);

	int deleteAdmin(String user_id);

	UserVO getAdmin(String user_id);
	
	// 진혁's AREA
	List<AttrReviewReportVO> getReivewReportList(Map<String, Object> map);

	List<BoardLikeReportVO> getBoardReportList(Map<String, Object> map);

	String getBadUser(int bad_member);

	String getReportUser(int user_code);

	String getReviewContent(int rev_code);

	AttrReviewVO getBadReview(int rev_code);

	BoardVO getBadBoard(int board_no);

	int countReport(String type);

	int reviewReportDelete(int rev_code);


}
