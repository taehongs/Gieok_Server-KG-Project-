package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.MessageVO;
import icu.gieok.vo.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public UserVO idDupCheck(String id_input) {
		return sqlSession.selectOne("idDupCheck", id_input);
	}
	
	@Override
	public void userInsert(UserVO user) {
		sqlSession.insert("userInsert", user);
	}
	
	@Override
	public void updateAuthKey(Map<String, String> map) {
		sqlSession.update("updateAuthKey", map);
	}
	
	@Override
	public void updateAuth(Map<String, String> map) {
		sqlSession.update("updateAuth", map);
	}
	
	@Override
	public UserVO checkAuth(Map<String, String> map) {
		return sqlSession.selectOne("checkAuth", map);
	}

	@Override
	public UserVO userSelect(String user_id) {
		return sqlSession.selectOne("userSelect", user_id);
	}
	
	@Override
	public int updateUserInform(UserVO user) {
		return sqlSession.update("updateUserInform", user);
	}

	@Override
	public int userDelete(String user_id) {
		return sqlSession.update("userDelete", user_id);
	}

	@Override
	public int updateUserPw(Map<String, String> map) {
		return sqlSession.update("updateUserPw", map);
	}

	@Override
	public UserVO userIdPwFind(Map<String, String> map) {
		return sqlSession.selectOne("userIdPwFind", map);
	}

	@Override
	public int emailDupCheck(Map<String, String> map) {
		return sqlSession.selectOne("emailDupCheck", map);
	}
	
	@Override
	public int changeStateLeave(int user_code) {
		int res = -1;
		res = sqlSession.update("changeStateLeave1", user_code);
		res += sqlSession.update("changeStateLeave2", user_code);
		return res;
	}
	
	
	@Override
	public List<AttrReviewVO> getMyReviewList(Map<String, Object> map) {
		return sqlSession.selectList("getMyReviewList", map);
	}
	
	@Override
	public int getMyReviewCount(Map<String, Object> map) {
		return sqlSession.selectOne("getMyReviewCount", map);
	}
	
	@Override
	public AttrReviewVO reviewDetail(Map<String, Integer> map) {
		return sqlSession.selectOne("reviewDetail", map);
	}
	
	@Override
	public int reviewDelete(Map<String, Integer> map) {
		return sqlSession.update("reviewDelete", map);
	}
	
	@Override
	public int reviewUpdate(AttrReviewVO review) {
		return sqlSession.update("reviewUpdate", review);
	}
	
	@Override
	public int sendMessage(MessageVO message) {
		return sqlSession.insert("sendMessage", message);
	}
	
	@Override
	public List<MessageVO> getMyMessage(Map<String, String> map) {
		return sqlSession.selectList("getMyMessage", map);
	}
	
	@Override
	public int deleteMessage(Integer[] checked) {
		return sqlSession.update("deleteMessage", checked);
	}
	
	@Override
	public int updateMessageRead(Map<String, Object> map) {
		return sqlSession.update("updateMessageRead", map);
	}
	
	@Override
	public int countUnreadMessage(Map<String, Object> map) {
		return sqlSession.selectOne("countUnreadMessage", map);
	}
	
	@Override
	public UserVO userCheck_sec(String user_id) {
		return sqlSession.selectOne("userCheck_sec", user_id);
	}
	
}
