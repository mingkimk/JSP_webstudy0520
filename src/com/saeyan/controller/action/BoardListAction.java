package com.saeyan.controller.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saeyan.dao.BoardDAO;
import com.saeyan.dto.BoardVO;

import util.PageNumber;

public class BoardListAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		
		String url = "/board/boardList.jsp";
		BoardDAO bDao = BoardDAO.getInstance();
		
		int nowPage = 1;
		if(request.getParameter("page") != null) {
			nowPage = Integer.valueOf(request.getParameter("page"));
		}
		//페이징하기위한 보더링
		PageNumber pagemaker = new PageNumber();//page 객체  /넘버링 방식 생각해보자
		pagemaker.setPage(nowPage);
		pagemaker.setCount(bDao.allCount());		//전체게시글수

		List<BoardVO> boardList = bDao.selectAllBoards(pagemaker.getNowPageStart(), pagemaker.getNowPageEnd());
		
		if(boardList != null) {
			request.setAttribute("boardList", boardList);
			request.setAttribute("pageMaker", pagemaker);
			//System.out.println("글목록 : "+boardList.size());
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}










