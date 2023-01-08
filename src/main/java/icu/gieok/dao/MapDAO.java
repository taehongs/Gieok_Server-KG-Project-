package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.AttrVO;
import icu.gieok.vo.CityVO;

public interface MapDAO {

	List<CityVO> getCityList(String province_id);

	String getCityName(int city_code);

	String getProvinceName(String province_id);

	int insertAttr(AttrVO attr);

	List<AttrVO> getAttrList(Map<String, Object> map);

	AttrVO getAttr(int attr_code);

	String getProvinceId(int city_code);

	List<AttrVO> showAttrList(String city_name);

	int updateAttr(AttrVO newAttr);

	int countAttr(Map<String, Object> map);

	String getAttrName(int attr_code);

	int deleteAttr(List<Integer> attr_checkBox);

	List<AttrVO> getDeleteAttrList(List<Integer> attr_checkBox);

	CityVO getCity(int city_code);

	
	
	
	

}
