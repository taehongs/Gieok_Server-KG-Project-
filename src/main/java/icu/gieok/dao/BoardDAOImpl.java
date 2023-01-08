package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import icu.gieok.vo.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void noticeInsert(BoardVO b) {
		sqlSession.insert("notice_insert", b);
	}

	@Override
	public void eventInsert(BoardVO b) {
		sqlSession.insert("event_insert", b);
	}

	@Override
	public BoardVO board_cont(int board_no) {
		return sqlSession.selectOne("board_cont", board_no);
	}

	@Override
	public BoardVO getboardDetail(int board_no) {
		return sqlSession.selectOne("board_detail", board_no);
	}

	@Transactional
	@Override
	public BoardVO board_contM(int board_no) {
		sqlSession.update("board_hitup", board_no);
		return sqlSession.selectOne("board_cont", board_no);
	}

	@Override
	public void board_del(int i) {
		sqlSession.update("board_del", i);
	}

	@Override
	public void noticeUpdate(BoardVO b) {
		sqlSession.update("notice_up", b);
	}

	@Override
	public void eventUpdate(BoardVO b) {
		sqlSession.update("event_up", b);
	}

	@Override
	public List<BoardVO> boardSort(Map<String, Object> bs) {
		return sqlSession.selectList("board_sort", bs);
	}

	@Override
	public List<BoardVO> board_list(Map<String, Object> rows) {
		return sqlSession.selectList("board_list", rows);
	}

	@Override
	public int board_count(Map<String, Object> row_sort) {
		return sqlSession.selectOne("board_count", row_sort);
	}

	@Override
	public void board_eventEnd(int i) {
		sqlSession.update("board_eventEnd", i);
	}
}
