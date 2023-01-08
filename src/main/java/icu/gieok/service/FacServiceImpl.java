package icu.gieok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import icu.gieok.dao.FacDAO;
import icu.gieok.vo.FacVO;

@Service
public class FacServiceImpl implements FacService {
	
	@Autowired
	private FacDAO facDAO;

	@Override
	public int insertFac(FacVO fac) {
		return facDAO.insertFac(fac);
	}

	@Override
	public String getProvinceId(int attr_code) {
		return facDAO.getProvinceId(attr_code);
	}

	@Override
	public int getCityCode(int attr_code) {
		return facDAO.getCityCode(attr_code);
	}

	@Override
	public List<FacVO> getFacList(Map<String, Object> map) {
		return facDAO.getFacList(map);
	}

	@Override
	public FacVO getFac(int fac_code) {
		return facDAO.getFac(fac_code);
	}

	@Override
	public int updateFac(FacVO upFac) {
		return facDAO.updateFac(upFac);
	}

	@Override
	public int countFac(Map<String, Object> map) {
		return facDAO.countFac(map);
	}

	@Override
	public List<FacVO> getDeleteFacList(List<Integer> fac_checkBox) {
		return facDAO.getDeleteFacList(fac_checkBox);
	}

	@Override
	public int deleteFac(List<Integer> fac_checkBox) {
		return facDAO.deleteFac(fac_checkBox);
	}

}
