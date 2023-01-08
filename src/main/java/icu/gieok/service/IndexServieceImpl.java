package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.IndexDAO;
import icu.gieok.vo.AttrVO;
import icu.gieok.vo.BoardVO;

@Service
public class IndexServieceImpl implements IndexService {
	
	@Autowired
	private IndexDAO indexDao;

	@Override
	public List<AttrVO> getAttrList_index(Map<String, Object> m) {
		return indexDao.getAttrList_index(m);
	}

	@Override
	public int countAttr_index(Map<String, Object> m) {
		return indexDao.countAttr_index(m);
	}

	@Override
	public List<BoardVO> getWithList_index(Map<String, Object> m) {
		return indexDao.getWiteList_index(m);
	}

	@Override
	public int countWith_index(Map<String, Object> m) {
		return indexDao.countWith_index(m);
	}

	@Override
	public List<BoardVO> getBoardList_index(Map<String, Object> m) {
		return indexDao.getBoardList_index(m);
	}

	@Override
	public int boardListCount_index(Map<String, Object> m) {
		return indexDao.boardListCount_index(m);
	}

	@Override
	public List<AttrVO> getAttrLike_index() {
		return indexDao.getAttrLike_index();
	}

	@Override
	public int maxNumAttr_index() {
		return indexDao.maxNumAttr_index();
	}

	@Override
	public AttrVO getAttrOne_index(int ran) {
		return indexDao.getAttrOne_index(ran);
	}

	@Override
	public List<BoardVO> getPhotoLike() {
		return indexDao.getPhotoLike();
	}
	
	@Override
	public List<AttrVO> getAllAttrList() {
		return indexDao.getAllAttrList();
	}

}
