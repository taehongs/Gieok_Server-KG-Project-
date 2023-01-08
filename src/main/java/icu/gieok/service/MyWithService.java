package icu.gieok.service;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.BoardVO;
import icu.gieok.vo.WithVO;

public interface MyWithService {

	int countMyWith(Map<String, Object> map);

	List<BoardVO> getMyWithList(Map<String, Object> map);

	int delMyWith(int board_no);

	List<WithVO> getMyWithList2(int board_no);

	int acceptMyWith(WithVO wt);

	int rejectMyWith(WithVO wt);

	int countWithAccept(int board_no);

	int changeWith(int board_no);

}
