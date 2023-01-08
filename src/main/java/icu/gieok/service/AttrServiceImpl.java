package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.AttrDAO;
import icu.gieok.vo.AttrLikeVO;
import icu.gieok.vo.AttrReviewReportVO;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.FacVO;

@Service
public class AttrServiceImpl implements AttrService {
	
	@Autowired
	private AttrDAO attrDAO;
	
	@Override
	public List<FacVO> getRestaurantList(int attr_code) {
		return attrDAO.getRestaurantList(attr_code);
	}
	
	@Override
	public List<FacVO> getCafeList(int attr_code) {
		return attrDAO.getCafeList(attr_code);
	}
	
	@Override
	public List<AttrReviewVO> getReviewList(int attr_code) {
		return attrDAO.getReviewList(attr_code);
	}
	
	@Override
	public int insertReview(AttrReviewVO review) {
		return attrDAO.insertReview(review);
	}
	
	@Override
	public String avgReviewRate(int attr_code) {
		return attrDAO.avgReviewRate(attr_code);
	}

	@Override
	public AttrReviewReportVO getAttrReviewReport(Map<String, Object> codes) {
		return attrDAO.getAttrReviewReport(codes);
	}
	
	@Override
	public int insertAttrReviewReport(Map<String, Object> codes) {
		return attrDAO.insertAttrReviewReport(codes);
	}
	
	@Override
	public int updateAttrReviewReport(Map<String, Object> codes) {
		return attrDAO.updateAttrReviewReport(codes);
	}
	
	@Override
	public AttrLikeVO getAttrLike(Map<String, Integer> map) {
		return attrDAO.getAttrLike(map);
	}
	
	@Override
	public int insertAttrLike(AttrLikeVO attrLike) {
		return attrDAO.insertAttrLike(attrLike);
	}
	
	@Override
	public int updateAttrLike(AttrLikeVO attrLike) {
		return attrDAO.updateAttrLike(attrLike);
	}
	
	@Override
	public int getAttrRevieWriter(int rev_code) {
		return attrDAO.getAttrReviewWriter(rev_code);
	}
	

	
	

}
