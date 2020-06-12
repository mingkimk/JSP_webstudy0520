package com.saeyan.controller.commentAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saeyan.controller.action.BoardListAction;
import com.saeyan.dao.BoardCommentDAO;
import com.saeyan.dto.BoardCommentVO;

public class CommentWriteAction implements CommentAction {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardCommentVO bVo = new BoardCommentVO();
		
		HttpSession session = request.getSession();
		//String iid = (String)session.getAttribute("userid");
		int viewNum = Integer.valueOf(request.getParameter("commentno"));
		bVo.setNum(viewNum);
		bVo.setcMemo(request.getParameter("commentmemo"));
		bVo.setId((String)session.getAttribute("userid"));
		BoardCommentDAO bDao = BoardCommentDAO.getInstance();
		bDao.insertCommentBoard(bVo);
		
		//new BoardListAction().execute(request, response);
		
		response.sendRedirect("http://localhost:8081/web-study-11/BoardServlet?command=board_view&num="+viewNum);

		
	}
}
