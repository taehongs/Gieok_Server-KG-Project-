package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.BoardWithDAO;
import icu.gieok.vo.AttrVO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.CityVO;
import icu.gieok.vo.ProvinceVO;
import icu.gieok.vo.WithVO;

@Service
public class BoardWithServiceImpl implements BoardWithService {

	@Autowired
	private BoardWithDAO boardWithDao;
	
	// 도 목록
	@Override
	public List<ProvinceVO> getProvinceList() {
		
		return boardWithDao.getProvinceList();
		
	}
	
	// 시 목록
	@Override
	public List<CityVO> getCityList(String province_id) {
		
		return boardWithDao.getCityList(province_id);
		
	}

	// 명소 목록
	@Override
	public List<AttrVO> getAttrList(String city_id) {
		
		return boardWithDao.getAttrList(city_id);
		
	}

	// 동행 등록
	@Override
	public int insertWith(BoardVO bw) {
		
		return boardWithDao.insertWith(bw);
		
	}

	// Pagination
	@Override
	public int countWith(Map<String, Object> map) {
		
		return boardWithDao.countWith(map);
		
	}
	
	// 게시물 목록
	@Override
	public List<BoardVO> getWithList(Map<String, Object> map) {
		
		return boardWithDao.getWithList(map);
		
	}

	// 신청하기 중복 확인
	@Override
	public WithVO selectWith(Map<String, Object> map2) {
		
		return boardWithDao.selectWith(map2);
		
	}

	// 신청하기
	@Override
	public int insert_WT(WithVO wt) {
		
		return boardWithDao.insert_WT(wt);
		
	}

	// 동행 목록 관리자 삭제
	@Override
	public int delete_WT(int board_no) {
		
		return boardWithDao.delete_WT(board_no);
	}

	// 신청 동행 토탈페이징
	@Override
	public int SinCountWith(Map<String, Object> map) {
		
		return boardWithDao.SinCountWith(map);
	}

	// 신청 동행 목록
	@Override
	public List<BoardVO> getWithSinDong(Map<String, Object> map) {

		return boardWithDao.getWithSinDong(map);
	}

	// 신청 취소하기
	@Override
	public int sinCancel(WithVO wt) {

		return boardWithDao.sinCancel(wt);
	}
	
	// 동행 상태
	@Override
	public WithVO getwithAccept(Map<String, Object> withMap) {
		return boardWithDao.getWithAccept(withMap);
	}
	
	// 신고하기
	@Override
	public int insertReportBoardWith(BoardLikeReportVO report) {
		return boardWithDao.insertReportBoardWith(report);
	}
	
	@Override
	public BoardLikeReportVO getReportBoardWith(Map<String, Integer> code) {
		return boardWithDao.getReportBoardWith(code);
	}


	
}
