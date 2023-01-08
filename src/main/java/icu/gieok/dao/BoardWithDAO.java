package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.AttrVO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.CityVO;
import icu.gieok.vo.ProvinceVO;
import icu.gieok.vo.WithVO;

public interface BoardWithDAO {

	List<ProvinceVO> getProvinceList();
	
	List<CityVO> getCityList(String province_id);

	List<AttrVO> getAttrList(String city_id);

	int insertWith(BoardVO bw);

	int countWith(Map<String, Object> map);

	List<BoardVO> getWithList(Map<String, Object> map);

	WithVO selectWith(Map<String, Object> map2);

	int insert_WT(WithVO wt);

	int delete_WT(int board_no);

	int SinCountWith(Map<String, Object> map);

	List<BoardVO> getWithSinDong(Map<String, Object> map);

	int sinCancel(WithVO wt);

	WithVO getWithAccept(Map<String, Object> withMap);

	int insertReportBoardWith(BoardLikeReportVO report);

	BoardLikeReportVO getReportBoardWith(Map<String, Integer> code);
	
	



}
