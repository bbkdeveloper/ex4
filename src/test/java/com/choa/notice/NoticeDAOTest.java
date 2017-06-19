package com.choa.notice;


import static org.junit.Assert.assertNotEquals;


import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.choa.board.BoardDTO;
import com.choa.ex4.MyAbstractTest;
import com.choa.util.PageMaker;




public class NoticeDAOTest extends MyAbstractTest {

	@Inject
	private NoticeDAOimpl noticeDAOimpl;
	
	@Test
	public void connectionTest()throws Exception{
		NoticeDTO noticeDTO = new NoticeDTO();
	/*	noticeDTO.setWriter("choa");
		noticeDTO.setTitle("test");
		noticeDTO.setContents("ttt");*/

		PageMaker pageMaker = new PageMaker(1, 20);
		List<BoardDTO> ar =  noticeDAOimpl.boardList(pageMaker.getRowMaker("",""));
		
		//assertEquals(1, result);
		assertNotEquals(0, ar.size());
		
	}
	@Test
	public void countTest()throws Exception{
		int count = noticeDAOimpl.boardCount();
		assertNotEquals(0, count);
		
		
	}

}
