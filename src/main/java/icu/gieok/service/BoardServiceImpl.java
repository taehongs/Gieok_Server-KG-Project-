package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.BoardDAO;
import icu.gieok.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDao;

	@Override
	public void noticeInsert(BoardVO b) {
		boardDao.noticeInsert(b);
	}

	@Override
	public void eventInsert(BoardVO b) {
		boardDao.eventInsert(b);
	}

	@Override
	public BoardVO board_cont(int board_no) {
		return boardDao.board_cont(board_no);
	}

	@Override
	public BoardVO getboardDetail(int board_no) {
		return boardDao.getboardDetail(board_no);
	}

	@Override
	public BoardVO board_contM(int board_no) {
		return boardDao.board_contM(board_no);
	}

	@Override
	public void board_del(int i) {
		boardDao.board_del(i);		
	}

	@Override
	public void noticeUpdate(BoardVO b) {
		boardDao.noticeUpdate(b);
	}

	@Override
	public void eventUpdate(BoardVO b) {
		boardDao.eventUpdate(b);
	}

	@Override
	public List<BoardVO> boardSort(Map<String, Object> bs) {
		return boardDao.boardSort(bs);
	}

	@Override
	public List<BoardVO> board_list(Map<String, Object> rows) {
		return boardDao.board_list(rows);
	}

	@Override
	public int board_count(Map<String, Object> row_sort) {
		return boardDao.board_count(row_sort);
	}

	@Override
	public void board_eventEnd(int i) {
		boardDao.board_eventEnd(i);
	}

}
