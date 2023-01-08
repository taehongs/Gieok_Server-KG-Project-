package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.FacVO;

public interface FacDAO {

	int insertFac(FacVO fac);

	String getProvinceId(int attr_code);

	int getCityCode(int attr_code);

	FacVO getFac(int fac_code);

	int updateFac(FacVO upFac);

	List<FacVO> getFacList(Map<String, Object> map);

	int countFac(Map<String, Object> map);

	List<FacVO> getDeleteFacList(List<Integer> fac_checkBox);

	int deleteFac(List<Integer> fac_checkBox);

}
