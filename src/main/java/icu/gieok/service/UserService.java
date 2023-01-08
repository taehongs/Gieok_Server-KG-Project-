package icu.gieok.service;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.MessageVO;
import icu.gieok.vo.UserVO;

public interface UserService {

	UserVO idDupCheck(String id_input);

	void userInsert(UserVO user);

	void updateAuthKey(Map<String, String> map);

	void updateAuth(Map<String, String> map);

	UserVO checkAuth(Map<String, String> map);

	UserVO userSelect(String user_id);
	
	int updateUserInform(UserVO user);

	int userDelete(String user_id);

	int updateUserPw(Map<String, String> map);

	UserVO userIdPwFind(Map<String, String> map);

	int emailDupCheck(Map<String, String> map);

	int changeStateLeave(int user_code);

	List<AttrReviewVO> getMyReviewList(Map<String, Object> map);

	int countMyReview(Map<String, Object> map);

	AttrReviewVO reviewDetail(Map<String, Integer> map);

	int reviewDelete(Map<String, Integer> map);

	int reviewUpdate(AttrReviewVO review);

	int sendMessage(MessageVO message);

	List<MessageVO> getMyMessage(Map<String, String> map);

	int deleteMessage(Integer[] checked);

	int updateMessageRead(Map<String, Object> map);

	int countUnreadMessage(Map<String, Object> map);

	UserVO userCheck_sec(String user_id);



}
