package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.MyWithDAO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.WithVO;

@Service
public class MyWithServiceImpl implements MyWithService {

	@Autowired
	private MyWithDAO myWithDao;
	
	// 나의 동행 count
	@Override
	public int countMyWith(Map<String, Object> map) {
		return myWithDao.countMyWith(map);
	}

	// 나의 동행 목록
	@Override
	public List<BoardVO> getMyWithList(Map<String, Object> map) {
		return myWithDao.getMyWithList(map);
	}

	@Override
	public int delMyWith(int board_no) {
		return myWithDao.delMyWith(board_no);
	}

	@Override
	public List<WithVO> getMyWithList2(int board_no) {
		return myWithDao.getMyWithList2(board_no);
	}

	@Override
	public int acceptMyWith(WithVO wt) {
		return myWithDao.acceptMyWith(wt);
	}

	@Override
	public int rejectMyWith(WithVO wt) {
		return myWithDao.rejectMyWith(wt);
	}

	@Override
	public int countWithAccept(int board_no) {
		return myWithDao.countWithAccept(board_no);
	}

	@Override
	public int changeWith(int board_no) {
		return myWithDao.changeWith(board_no);
	}

}
