package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import icu.gieok.vo.AttrVO;
import icu.gieok.vo.CityVO;

@Repository
public class MapDAOImpl implements MapDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int countAttr(Map<String, Object> map) {
		return sqlSession.selectOne("countAttr", map);
	}
	
	@Override
	public List<AttrVO> getAttrList(Map<String, Object> map) {
		return sqlSession.selectList("getAttrList", map);
	}
	
	@Override
	public List<CityVO> getCityList(String province_id) {
		return sqlSession.selectList("getCityList", province_id);
	}
	
	@Override
	public String getCityName(int city_code) {
		return sqlSession.selectOne("getCityName", city_code);
	}
	
	@Override
	public String getProvinceName(String province_id) {
		return sqlSession.selectOne("getProvinceName", province_id);
	}
	
	@Override
	public int insertAttr(AttrVO attr) {
		return sqlSession.insert("insertAttr", attr);
	}
	
	@Override
	public AttrVO getAttr(int attr_code) {
		return sqlSession.selectOne("getAttr", attr_code);
	}
	
	@Override
	public String getProvinceId(int city_code) {
		return sqlSession.selectOne("getProvinceId", city_code);
	}
	
	@Override
	public List<AttrVO> showAttrList(String city_name) {
		return sqlSession.selectList("showAttrList", city_name);
	}
	
	@Override
	public int updateAttr(AttrVO newAttr) {
		return sqlSession.update("updateAttr", newAttr);
	}
	
	@Override
	public String getAttrName(int attr_code) {
		return sqlSession.selectOne("getAttrName", attr_code);
	}
	
	@Override
	public int deleteAttr(List<Integer> attr_checkBox) {
		return sqlSession.delete("deleteAttr", attr_checkBox);
	}
	
	@Override
	public List<AttrVO> getDeleteAttrList(List<Integer> attr_checkBox) {
		return sqlSession.selectList("getDeleteAttrList", attr_checkBox);
	}
	
	@Override
	public CityVO getCity(int city_code) {
		return sqlSession.selectOne("getCity", city_code);
	}
	
}
