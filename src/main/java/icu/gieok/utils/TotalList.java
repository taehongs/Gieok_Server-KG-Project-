package icu.gieok.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import icu.gieok.service.BoardService;
import icu.gieok.service.IndexService;
import icu.gieok.service.MapService;
import icu.gieok.vo.AttrVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.CityVO;

public class TotalList {

	Map<String, Object> m = new HashMap<>();
	
	int list_count;
	
	public List<BoardVO> board_list(int board_type, String search, Object service){
		
		List<BoardVO> list = new ArrayList<>();
		
		m.put("startRow", 1);
		m.put("endRow", 5);
		
		if(board_type == 3) {
			m.put("category", "board_location");
			m.put("keyword", search);
			list = ((IndexService)service).getWithList_index(m);
		}else if(board_type == 4) {
			m.put("category", "board_title");
			m.put("sortBy", "board_regDate");
			m.put("keyword", search);
			list = ((IndexService) service).getBoardList_index(m);
		}else {
			m.put("board_sort", "");
			m.put("list_search", search);
			list = ((BoardService) service).board_list(m);
		}
		return list;
	}

	public int board_count(int board_type, String search, Object service){
		
		if(board_type == 3) {
			m.put("category", "board_location");
			m.put("keyword", search);
			list_count = ((IndexService) service).countWith_index(m);
		}else if(board_type == 4) {
			m.put("category", "board_title");
			m.put("keyword", search);
			list_count = ((IndexService) service).boardListCount_index(m);
		}else {
			m.put("list_search", search);
			m.put("board_sort", "");
			list_count = ((BoardService) service).board_count(m);
		}
		return list_count;
	}
	
	public List<AttrVO> attr_list(String search, Object service){
		
		m.put("startRow", 1);
		m.put("endRow", 5);
		m.put("sortBy", "attr_code");
		m.put("keyword", search);
		List<AttrVO> list = ((IndexService) service).getAttrList_index(m);

		return list;
	}
	
	public int attr_count(String search, Object service) {
		
		m.put("category", "attr_name");
		m.put("keyword", search);
		list_count = ((IndexService) service).countAttr_index(m);
		
		return list_count;
	}
	
	public List<AttrVO> attr_list_like(Object service){
		
		List<AttrVO> list = ((IndexService) service).getAttrLike_index();

		return list;
	}

	public AttrVO attr_ranImg(Object service) {
		
		
		List<AttrVO> attrList = ((IndexService)service).getAllAttrList();
		int ran = (int)(Math.random()*attrList.size());
		
		
		AttrVO list = new AttrVO();
		
		if(attrList.size()>0) {
			list = attrList.get(ran); 
		}

		return list;
	}

	public List<BoardVO> photo_likeImg(Object service) {
		
		List<BoardVO> list = ((IndexService)service).getPhotoLike();
		
		return list;
	}

	
	
	
}
