package com.saeyan.controller.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saeyan.dao.BoardCommentDAO;
import com.saeyan.dao.BoardDAO;
import com.saeyan.dao.ReadCntDAO;
import com.saeyan.dto.BoardCommentVO;
import com.saeyan.dto.BoardVO;

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/board/boardView.jsp";

		String num = request.getParameter("num");
		BoardDAO bDao = BoardDAO.getInstance();
		
		HttpSession session = request.getSession();
		String iid = (String)session.getAttribute("userid");
		
		ReadCntDAO rDao = ReadCntDAO.getInstance();
		if(rDao.duplexReadID(num, iid)) {
			rDao.updateReadCount(iid,num);
			bDao.updateReadCount(num);
		}
		
		BoardCommentDAO bcDao = BoardCommentDAO.getInstance();
		List<BoardCommentVO> boardCommentList = bcDao.selectAllBoards(num);
		request.setAttribute("commentList", boardCommentList);		
		
		BoardVO bVo = bDao.selectOneBoardByNum(num);
		request.setAttribute("board", bVo);
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}
