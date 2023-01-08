package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;

@Repository
public class PhotoDAOImpl implements PhotoDAO {
    @Autowired
    SqlSession sqlSession;

    @Override
    public int updateEventPhoto(BoardVO board) {
        return sqlSession.update("updateEventPhoto", board);
    }

    @Override
    public int eventPhotoInsert(BoardVO board) {
        return sqlSession.insert("eventPhotoInsert", board);
    }

    @Override
    public int boardListCount(Map<String, Object> map) {
        return sqlSession.selectOne("boardListCount", map);
    }

    @Override
    public List<BoardVO> getBoardList(Map<String, Object> map) {
        return sqlSession.selectList("getBoardList", map);
    }

    @Override
    public BoardVO getBoardDetail(int board_no) {
        return sqlSession.selectOne("getBoardDetail", board_no);
    }

    @Override
    public int updateBoardHit(int board_no) {
        return sqlSession.update("updateBoardHit", board_no);
    }

    @Override
    public int updatePhotoEvent(BoardVO board) {
        return sqlSession.update("updatePhotoEvent", board);
    }

    @Override
    public int deletePhotoEvent(int board_no) {
    	sqlSession.update("changeWithReportState", board_no);
        return sqlSession.update("deletePhotoEvent", board_no);
    }

    @Override
    public int updatePhotoEventLike(Map<String, Object> map) {
        return sqlSession.update("updatePhotoEventLike", map);
    }

    @Override
    public BoardLikeReportVO getBoardLikeReport(Map<String, Object> map) {
        return sqlSession.selectOne("getBoardLikeReport", map);
    }

    @Override
    public int boardLikeReportInsert(Map<String, Object> map) {
    	if(map.get("bad_member")==null) {
    		return sqlSession.insert("boardLikeReportInsert2", map);
    	}
    	
        return sqlSession.insert("boardLikeReportInsert", map);
    }

    @Override
    public int updatePhotoEventLikeCheck(Map<String, Object> map) {
        return sqlSession.update("updatePhotoEventLikeCheck", map);
    }

	@Override
	public int updatePhotoEventReportCheck(Map<String, Object> map) {
		return sqlSession.update("updatePhotoEventReportCheck", map);
	}

	@Override
	public int updatePhotoEventReport(Map<String, Object> map) {
		return sqlSession.update("updatePhotoEventReport", map);
	}

	@Override
	public List<BoardVO> selectPhotoEventList(List<Integer> photo_no) {
		return sqlSession.selectList("selectPhotoEventList", photo_no);
	}

	@Override
	public int deletePhotoEventList(List<Integer> photo_no) {
		
		int result = sqlSession.delete("deletePhotoEventList", photo_no);
		
		if(result>0) {
			sqlSession.update("updateBoardLikeReport", photo_no);
		}
		
		return result;
	}
	
	@Override
	public String getPhotoEventLikeCheck(Map<String, Integer> likeMap) {
		return sqlSession.selectOne("getPhotoEventLikeCheck", likeMap);
	}

}
