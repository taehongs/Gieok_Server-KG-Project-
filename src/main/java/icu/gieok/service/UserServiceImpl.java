package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.UserDAO;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.MessageVO;
import icu.gieok.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public UserVO idDupCheck(String id_input) {
		return userDAO.idDupCheck(id_input);
	}
	
	@Override
	public void userInsert(UserVO user) {
		userDAO.userInsert(user);
	}
	
	@Override
	public void updateAuthKey(Map<String, String> map) {
		userDAO.updateAuthKey(map);
	}
	
	@Override
	public void updateAuth(Map<String, String> map) {
		userDAO.updateAuth(map);
	}
	
	@Override
	public UserVO checkAuth(Map<String, String> map) {
		return userDAO.checkAuth(map);
	}

	@Override
	public UserVO userSelect(String user_id) {
		return userDAO.userSelect(user_id);
	}
	
	@Override
	public int updateUserInform(UserVO user) {
		return userDAO.updateUserInform(user);
	}

	@Override
	public int userDelete(String user_id) {
		return userDAO.userDelete(user_id);
	}

	@Override
	public int updateUserPw(Map<String, String> map) {
		return userDAO.updateUserPw(map);
	}

	@Override
	public UserVO userIdPwFind(Map<String, String> map) {
		return userDAO.userIdPwFind(map);
	}

	@Override
	public int emailDupCheck(Map<String, String> map) {
		return userDAO.emailDupCheck(map);
	}
	
	@Override
	public int changeStateLeave(int user_code) {
		return userDAO.changeStateLeave(user_code);
	}
	
	@Override
	public List<AttrReviewVO> getMyReviewList(Map<String, Object> map) {
		return userDAO.getMyReviewList(map);
	}
	
	@Override
	public int countMyReview(Map<String, Object> map) {
		return userDAO.getMyReviewCount(map);
	}
	
	@Override
	public AttrReviewVO reviewDetail(Map<String, Integer> map) {
		return userDAO.reviewDetail(map);
	}
	
	@Override
	public int reviewDelete(Map<String, Integer> map) {
		return userDAO.reviewDelete(map);
	}
	
	@Override
	public int reviewUpdate(AttrReviewVO review) {
		return userDAO.reviewUpdate(review);
	}
	
	@Override
	public int sendMessage(MessageVO message) {
		return userDAO.sendMessage(message);
	}
	
	@Override
	public List<MessageVO> getMyMessage(Map<String, String> map) {
		return userDAO.getMyMessage(map);
	}
	
	@Override
	public int deleteMessage(Integer[] checked) {
		return userDAO.deleteMessage(checked);
	}
	
	@Override
	public int updateMessageRead(Map<String, Object> map) {
		return userDAO.updateMessageRead(map);
	}
	
	@Override
	public int countUnreadMessage(Map<String, Object> map) {
		return userDAO.countUnreadMessage(map);
	}
	
	@Override
	public UserVO userCheck_sec(String user_id) {
		return userDAO.userCheck_sec(user_id);
	}
	
}
