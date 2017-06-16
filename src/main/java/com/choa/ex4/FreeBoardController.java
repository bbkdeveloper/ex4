package com.choa.ex4;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.choa.freeboard.FreeBoardServiceimpl;


@Controller
@RequestMapping(value="freeBoard/**")
public class FreeBoardController {
	
	@Inject
	private FreeBoardServiceimpl freeBoardServiceimpl;
	
	@RequestMapping(value="freeBoardList", method=RequestMethod.GET)
	public String boardList(Model model, @RequestParam(defaultValue="1")Integer curPage)throws Exception{
		
			model.addAttribute("board", "freeboard");
			model.addAttribute("list", freeBoardServiceimpl.boardList(curPage));
			return "board/boardList";
		}
		
	}
	
	

