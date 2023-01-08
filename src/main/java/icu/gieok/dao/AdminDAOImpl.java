package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import icu.gieok.vo.AttrReviewReportVO;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.UserVO;

@Repository
public class AdminDAOImpl implements AdminDAO {
	
	@Autowired
	private SqlSession sqlSession;

	// 흥식's AREA
	@Override
	public int userListCount(Map<String, Object> map) {
		return sqlSession.selectOne("userListCount", map);
	}

	@Override
	public List<UserVO> getUserList(Map<String, Object> map) {
		return sqlSession.selectList("getUserList", map);
	}

	@Override
	public int adminListCount(Map<String, Object> map) {
		return sqlSession.selectOne("adminListCount", map);
	}

	@Override
	public List<UserVO> getAdminList(Map<String, Object> map) {
		return sqlSession.selectList("getAdminList", map);
	}

	@Override
	public int addAdmin(String user_id) {
		return sqlSession.update("addAdmin", user_id);
	}

	@Override
	public int deleteAdmin(String user_id) {
		return sqlSession.update("deleteAdmin", user_id);
	}

	@Override
	public UserVO getAdmin(String user_id) {
		return sqlSession.selectOne("getAdmin", user_id);
	}
	
	// 진혁's AREA
	@Override
	public List<AttrReviewReportVO> getReivewReportList(Map<String, Object> map) {
		return sqlSession.selectList("getReviewReportList", map);
	}
	
	@Override
	public List<BoardLikeReportVO> getBoardReportList(Map<String, Object> map) {
		return sqlSession.selectList("getBoardReportList", map);
	}
	
	@Override
	public String getBadUser(int bad_member) {
		return sqlSession.selectOne("getBadUser", bad_member);
	}
	
	@Override
	public String getReportUser(int user_code) {
		return sqlSession.selectOne("getReportUser", user_code);
	}
	
	@Override
	public String getReviewContent(int rev_code) {
		return sqlSession.selectOne("getReportUser", rev_code);
	}
	
	@Override
	public AttrReviewVO getBadReview(int rev_code) {
		return sqlSession.selectOne("getBadReview", rev_code);
	}
	
	@Override
	public BoardVO getBadBoard(int board_no) {
		return sqlSession.selectOne("getBadBoard", board_no);
	}
	
	@Override
	public int countReport(String type) {
		
		if(type.equals("review")) {
			return sqlSession.selectOne("countReviewReport");
		}else {
			return sqlSession.selectOne("countBoardReport");
		}
	}
	
	@Transactional
	@Override
	public int reviewReportDelete(int rev_code) {
		
		int result = sqlSession.update("reviewReportDelete", rev_code);
		
		if(result==1) {
			int res = sqlSession.update("updateReviewReport", rev_code);
			if(res<1) {
				return -1;
			}
		}
		
		return result;
	}
	
	
	
}
