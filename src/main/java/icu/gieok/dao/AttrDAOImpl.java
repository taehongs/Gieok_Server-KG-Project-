package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import icu.gieok.vo.AttrLikeVO;
import icu.gieok.vo.AttrReviewReportVO;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.FacVO;

@Repository
public class AttrDAOImpl implements AttrDAO {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<FacVO> getRestaurantList(int attr_code) {
		return sqlSession.selectList("getRestaurantList", attr_code);
	}
	
	@Override
	public List<FacVO> getCafeList(int attr_code) {
		return sqlSession.selectList("getCafeList", attr_code);
	}
	
	@Override
	public List<AttrReviewVO> getReviewList(int attr_code) {
		return sqlSession.selectList("getReviewList", attr_code);
	}
	
	@Override
	public int insertReview(AttrReviewVO review) {
		return sqlSession.insert("insertReview", review);
	}
	
	@Override
	public String avgReviewRate(int attr_code) {
		return sqlSession.selectOne("avgReviewRate", attr_code);
	}

	@Override
	public AttrReviewReportVO getAttrReviewReport(Map<String, Object> codes) {
		return sqlSession.selectOne("getAttrReviewReport", codes);
	}
	
	@Override
	public int insertAttrReviewReport(Map<String, Object> codes) {
		return sqlSession.insert("insertAttrReviewReport", codes);
	}
	
	@Override
	public int updateAttrReviewReport(Map<String, Object> codes) {
		return sqlSession.update("updateAttrReviewReport", codes);
	}
	
	@Override
	public AttrLikeVO getAttrLike(Map<String, Integer> map) {
		return sqlSession.selectOne("getAttrLike", map);
	}
	
	@Override
	public int insertAttrLike(AttrLikeVO attrLike) {
		return sqlSession.insert("insertAttrLike", attrLike);
	}
	
	@Override
	public int updateAttrLike(AttrLikeVO attrLike) {
		return sqlSession.update("updateAttrLike", attrLike);
	}
	
	@Override
	public int getAttrReviewWriter(int rev_code) {
		return sqlSession.selectOne("getAttrReviewWriter", rev_code);
	}
	
	
	
	
	
}
