package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.PhotoDAO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	PhotoDAO photoDAO;
	
	@Override
	public int updateEventPhoto(BoardVO board) {
		return photoDAO.updateEventPhoto(board);
	}

	@Override
	public int eventPhotoInsert(BoardVO board) {
		return photoDAO.eventPhotoInsert(board);
	}

	@Override
	public int boardListCount(Map<String, Object> map) {
		return photoDAO.boardListCount(map);
	}

    @Override
    public List<BoardVO> getBoardList(Map<String, Object> map) {
        return photoDAO.getBoardList(map);
    }

    @Override
    public BoardVO getBoardDetail(int board_no) {
        return photoDAO.getBoardDetail(board_no);
    }

    @Override
    public int updateBoardHit(int board_no) {
        return photoDAO.updateBoardHit(board_no);
    }

    @Override
    public int updatePhotoEvent(BoardVO board) {
        return photoDAO.updatePhotoEvent(board);
    }

    @Override
    public int deletePhotoEvent(int board_no) {
        return photoDAO.deletePhotoEvent(board_no);
    }

    @Override
    public int updatePhotoEventLike(Map<String, Object> map) {
        return photoDAO.updatePhotoEventLike(map);
    }

    @Override
    public BoardLikeReportVO getBoardLikeReport(Map<String, Object> map) {
        return photoDAO.getBoardLikeReport(map);
    }

    @Override
    public int boardLikeReportInsert(Map<String, Object> map) {
        return photoDAO.boardLikeReportInsert(map);
    }

    @Override
    public int updatePhotoEventLikeCheck(Map<String, Object> map) {
        return photoDAO.updatePhotoEventLikeCheck(map);
    }

	@Override
	public int updatePhotoEventReportCheck(Map<String, Object> map) {
		return photoDAO.updatePhotoEventReportCheck(map);
	}

	@Override
	public int updatePhotoEventReport(Map<String, Object> map) {
		return photoDAO.updatePhotoEventReport(map);
	}

	@Override
	public List<BoardVO> selectPhotoEventList(List<Integer> photo_no) {
		return photoDAO.selectPhotoEventList(photo_no);
	}

	@Override
	public int deletePhotoEventList(List<Integer> photo_no) {
		return photoDAO.deletePhotoEventList(photo_no);
	}
	
	@Override
	public String getPhotoEventLikeCheck(Map<String, Integer> likeMap) {
		return photoDAO.getPhotoEventLikeCheck(likeMap);
	}

}
