package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import icu.gieok.vo.BoardVO;
import icu.gieok.vo.WithVO;

@Repository
public class MyWithDAOImpl implements MyWithDAO {

	@Autowired
	private SqlSession sqlSession;

	// 나의 동행 count
	@Override
	public int countMyWith(Map<String, Object> map) {
		return sqlSession.selectOne("countMyWith", map);
	}

	@Override
	public List<BoardVO> getMyWithList(Map<String, Object> map) {
		return sqlSession.selectList("getMyWithList", map);
	}

	@Override
	public int delMyWith(int board_no) {
		sqlSession.update("changeWithReportState", board_no);
		sqlSession.delete("delWithList", board_no);
		return sqlSession.update("delMyWith", board_no);
	}

	@Override
	public List<WithVO> getMyWithList2(int board_no) {
		return sqlSession.selectList("getMyWithList2", board_no);
	}

	@Override
	public int acceptMyWith(WithVO wt) {
		return sqlSession.update("acceptMyWith", wt);
	}

	@Override
	public int rejectMyWith(WithVO wt) {
		return sqlSession.update("rejectMyWith", wt);
	}

	@Override
	public int countWithAccept(int board_no) {
		return sqlSession.selectOne("countWithAccept", board_no);
	}

	@Override
	public int changeWith(int board_no) {
		// 여기부터~
		
		int countWithPending = sqlSession.selectOne("countWithPending", board_no);
		
		if(countWithPending==0) {
			return 1;
		}
		
		return sqlSession.update("changeWith", board_no);
	}

}
