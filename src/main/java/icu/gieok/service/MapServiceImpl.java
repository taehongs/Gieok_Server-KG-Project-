package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.MapDAO;
import icu.gieok.vo.AttrVO;
import icu.gieok.vo.CityVO;

@Service
public class MapServiceImpl implements MapService {

	@Autowired
	private MapDAO mapDAO;
	
	@Override
	public int countAttr(Map<String, Object> map) {
		return mapDAO.countAttr(map);
	}
	
	@Override
	public List<AttrVO> getAttrList(Map<String, Object> map) {
		return mapDAO.getAttrList(map);
	}
	
	@Override
	public List<CityVO> getCityList(String province_id) {
		return mapDAO.getCityList(province_id);
	}
	
	@Override
	public String getCityName(int city_code) {
		return mapDAO.getCityName(city_code);
	}
	
	@Override
	public String getProvinceName(String province_id) {
		return mapDAO.getProvinceName(province_id);
	}
	
	@Override
	public int insertAttr(AttrVO attr) {
		return mapDAO.insertAttr(attr);
	}
	
	@Override
	public AttrVO getAttr(int attr_code) {
		return mapDAO.getAttr(attr_code);
	}
	
	@Override
	public String getProvinceId(int attr_code) {
		return mapDAO.getProvinceId(attr_code);
	}
	
	@Override
	public List<AttrVO> showAttrList(String city_name) {
		return mapDAO.showAttrList(city_name);
	}
	
	@Override
	public int updateAttr(AttrVO newAttr) {
		return mapDAO.updateAttr(newAttr);
	}
	
	@Override
	public String getAttrName(int attr_code) {
		return mapDAO.getAttrName(attr_code);
	}
	
	@Override
	public int deleteAttr(List<Integer> attr_checkBox) {
		return mapDAO.deleteAttr(attr_checkBox);
	}

	@Override
	public List<AttrVO> getDeleteAttrList(List<Integer> attr_checkBox) {
		return mapDAO.getDeleteAttrList(attr_checkBox);
	}
	
	@Override
	public CityVO getCity(int city_code) {
		return mapDAO.getCity(city_code);
	}
	
	
}
