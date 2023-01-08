package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.BoardVO;

public interface BoardDAO {

	void noticeInsert(BoardVO b);

	void eventInsert(BoardVO b);

	BoardVO board_cont(int board_no);

	BoardVO getboardDetail(int board_no);

	BoardVO board_contM(int board_no);

	void board_del(int i);

	void noticeUpdate(BoardVO b);

	void eventUpdate(BoardVO b);

	List<BoardVO> boardSort(Map<String, Object> bs);

	List<BoardVO> board_list(Map<String, Object> rows);

	int board_count(Map<String, Object> row_sort);

	void board_eventEnd(int i);

}
