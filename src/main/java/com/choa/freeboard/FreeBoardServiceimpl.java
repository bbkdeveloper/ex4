package com.choa.freeboard;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.choa.board.BoardDTO;
import com.choa.util.PageMaker;

@Service
public class FreeBoardServiceimpl {
	
	@Inject
	private FreeBoardDAOimpl freeBoardDAOimpl;
	
	
	public List<BoardDTO> boardList(int curPage)throws Exception{
		int result = freeBoardDAOimpl.boardCount();
		
		PageMaker pageMaker = new PageMaker(curPage);
		pageMaker.getMakePage(result);
		return freeBoardDAOimpl.boardList(pageMaker.getRowMaker("",""));

	}
	

}
