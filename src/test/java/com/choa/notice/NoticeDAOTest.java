package com.choa.notice;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;


import com.choa.ex4.MyAbstractTest;
import com.choa.util.PageMaker;



public class NoticeDAOTest extends MyAbstractTest {

	@Inject
	private NoticeDAOimpl noticeDAO;
	//Junit으로 Test를 하려면 Test라는 Annotation을 줘야한다
	
	//@Test
	public void test()throws Exception{
	//NoiceDAO noticeDAO = new NoticeDAO();
		//무조건 null이 뜬다. 왜????????? Spring 컨테이너에서 만든 클래스만 가져올수있다.
		
		PageMaker pageMaker = new PageMaker(1, 10);
		List<NoticeDTO> ar =  noticeDAO.noticeList(pageMaker.getRowMaker("", ""));
		
		assertEquals(0, ar.size());
		
		/*NoticeDTO noticeDTO = noticeDAO.noticeView(33);
		System.out.println("noticeDTO.getTitle==="+noticeDTO.getTitle());
		Assert.assertNotNull(noticeDTO.getTitle());*/
	}
	
	@Test
	public void test2()throws Exception{
		int result =noticeDAO.noticeDelete(1);
		Assert.assertEquals(1, result);
	}

}
