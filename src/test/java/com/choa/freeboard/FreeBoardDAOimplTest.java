package com.choa.freeboard;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;

import com.choa.ex4.MyAbstractTest;

public class FreeBoardDAOimplTest extends MyAbstractTest {

	
	@Inject
	private FreeBoardDAOimpl freedao;
	
	@Test
	public void test()throws Exception {
		FreeBoardDTO freeBoardDTO = new FreeBoardDTO();
		
		freeBoardDTO.setNum(30);
		freeBoardDTO.setWriter("choa");
		freeBoardDTO.setTitle("ttt");
		freeBoardDTO.setContents("contents");
		
		int result = freedao.boardWrite(freeBoardDTO);
		System.out.println(result);

		assertEquals(1, result);
	}

}
