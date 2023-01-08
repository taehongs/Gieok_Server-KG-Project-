package icu.gieok.dao;

import java.util.List;
import java.util.Map;

import icu.gieok.vo.AttrVO;
import icu.gieok.vo.BoardVO;

public interface IndexDAO {

	List<AttrVO> getAttrList_index(Map<String, Object> m);

	int countAttr_index(Map<String, Object> m);

	List<BoardVO> getWiteList_index(Map<String, Object> m);

	int countWith_index(Map<String, Object> m);

	List<BoardVO> getBoardList_index(Map<String, Object> m);

	int boardListCount_index(Map<String, Object> m);

	List<AttrVO> getAttrLike_index();

	int maxNumAttr_index();

	AttrVO getAttrOne_index(int ran);

	List<BoardVO> getPhotoLike();

	List<AttrVO> getAllAttrList();

}
