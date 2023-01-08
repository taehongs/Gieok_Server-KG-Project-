package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import icu.gieok.vo.FacVO;


@Repository
public class FacDAOImpl implements FacDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int insertFac(FacVO fac) {
		return sqlSession.insert("insertFac", fac);
	}

	@Override
	public String getProvinceId(int attr_code) {
		return sqlSession.selectOne("getProvinceId2", attr_code);
	}

	@Override
	public int getCityCode(int attr_code) {
		return sqlSession.selectOne("getCityCode", attr_code);
	}

	@Override
	public List<FacVO> getFacList(Map<String, Object> map) {
		return sqlSession.selectList("getFacList", map);
	}

	@Override
	public FacVO getFac(int fac_code) {
		return sqlSession.selectOne("getFac", fac_code);
	}

	@Override
	public int updateFac(FacVO upFac) {
		return sqlSession.update("updateFac", upFac);
	}

	@Override
	public int countFac(Map<String, Object> map) {
		return sqlSession.selectOne("countFac", map);
	}

	@Override
	public List<FacVO> getDeleteFacList(List<Integer> fac_checkBox) {
		return sqlSession.selectList("getDeleteFacList", fac_checkBox);
	}

	@Override
	public int deleteFac(List<Integer> fac_checkBox) {
		return sqlSession.delete("deleteFac", fac_checkBox);
	}

}
