package icu.gieok.service;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.FacVO;

public interface FacService {

	int insertFac(FacVO fac);

	String getProvinceId(int attr_code);

	int getCityCode(int attr_code);

	FacVO getFac(int fac_code);

	int updateFac(FacVO upFac);

	int countFac(Map<String, Object> map);

	List<FacVO> getFacList(Map<String, Object> map);

	List<FacVO> getDeleteFacList(List<Integer> fac_checkBox);

	int deleteFac(List<Integer> fac_checkBox);

}
