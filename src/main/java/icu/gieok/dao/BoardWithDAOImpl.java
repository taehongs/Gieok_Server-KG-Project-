package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import icu.gieok.vo.AttrVO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.CityVO;
import icu.gieok.vo.ProvinceVO;
import icu.gieok.vo.WithVO;

@Repository
public class BoardWithDAOImpl implements BoardWithDAO {

	@Autowired
	private SqlSession sqlSession;

	// 도 목록
	@Override
	public List<ProvinceVO> getProvinceList() {
		
		return sqlSession.selectList("with_list_Province");
		
	}
	
	// 시 목록
	@Override
	public List<CityVO> getCityList(String province_id) {
		
		return sqlSession.selectList("with_list_City", province_id);
		
	}

	// 명소 목록
	@Override
	public List<AttrVO> getAttrList(String city_id) {
		
		return sqlSession.selectList("with_list_Attr", city_id);
		
	}

	// 동행 등록
	@Override
	public int insertWith(BoardVO bw) {
		
		return sqlSession.insert("insertWith", bw);
		
	}

	// Pagination
	@Override
	public int countWith(Map<String, Object> map) {
		
		return sqlSession.selectOne("countWith", map);
	}

	// 게시물 목록
	@Override
	public List<BoardVO> getWithList(Map<String, Object> map) {
		
		return sqlSession.selectList("getWithList", map);
	}

	// 신청하기 중복 확인	
	@Override
	public WithVO selectWith(Map<String, Object> map2) {
		
		return sqlSession.selectOne("selectWith", map2);
	}

	// 신청하기
	@Override
	public int insert_WT(WithVO wt) {
		
		return sqlSession.insert("insert_WT", wt);
	}

	// 동행 목록 관리자 삭제
	@Override
	public int delete_WT(int board_no) {
		sqlSession.update("changeWithReportState", board_no);
		return sqlSession.update("delete_WT", board_no);
	}

	// 신청 동행 토탈페이징
	@Override
	public int SinCountWith(Map<String, Object> map) {
		
		return sqlSession.selectOne("SinCountWith", map);
	}

	// 신청 동행 목록
	@Override
	public List<BoardVO> getWithSinDong(Map<String, Object> map) {

		return sqlSession.selectList("getWithSinDong", map);
	}

	// 신청 취소하기
	@Override
	public int sinCancel(WithVO wt) {
		
		return sqlSession.delete("sinCancel", wt);
		
	}


	// 동행 상태
	@Override
	public WithVO getWithAccept(Map<String, Object> withMap) {
		return sqlSession.selectOne("getWithAccept", withMap);
	}
	
	//신고하기
	@Override
	public int insertReportBoardWith(BoardLikeReportVO report) {
		return sqlSession.insert("insertReportBoardWith", report);
	}
	
	@Override
	public BoardLikeReportVO getReportBoardWith(Map<String, Integer> code) {
		return sqlSession.selectOne("getReportBoardWith", code);
	}

	

}
