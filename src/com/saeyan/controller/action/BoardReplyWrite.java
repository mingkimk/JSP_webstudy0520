package com.saeyan.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saeyan.dao.BoardDAO;
import com.saeyan.dto.BoardVO;

public class BoardReplyWrite implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardDAO bDao = BoardDAO.getInstance();
		int nowNum = bDao.nowNum();

		int bNum = Integer.valueOf(request.getParameter("rnum"));  // 현재글
		
		request.getParameter("nowuser");
		BoardVO bVo = bDao.selectOneBoardByNum(String.valueOf(bNum));
		
		int seqNum = bVo.getSeq();
		int grpNum = bVo.getGrp();
		int lvlNum = bVo.getLvl();
		
		if(seqNum == 0) {
			seqNum = bDao.maxGrp(grpNum)+1;
			// select max(seq) from board where grp=grpNum;
		}else {
		//	int lvlDepth = bDao.depthLevelInt(grpNum, seqNum, lvlNum);
			int lvlDepth=0;
			int tempSeqNum = seqNum+1;
			for(;;) {
				if(bDao.depthLevelGrp(grpNum, tempSeqNum, lvlNum)){
					lvlDepth++;
					tempSeqNum++;
				}else {
					break;
				}
			}
					
			bDao.updateSeqCount(grpNum, seqNum+lvlDepth);
			seqNum = seqNum+1+lvlDepth;			
			
			
			//bDao.updateSeqCount(grpNum, seqNum);
			//seqNum = seqNum+1;
		}
		int lvlnum = bVo.getLvl()+1;
		
		// 원글의 그룹번호와, 순서, 레벨을 따로 가져와서 이를 결정해서 넘겨 준다. 
		if (nowNum != -1) {
			bVo = new BoardVO();
			bVo.setNum(nowNum);
			bVo.setName(request.getParameter("name"));
			bVo.setPass(request.getParameter("pass"));
			bVo.setEmail(request.getParameter("email"));
			bVo.setTitle(request.getParameter("title"));
			bVo.setContent(request.getParameter("content"));
			bVo.setGrp(grpNum);
			bVo.setSeq(seqNum);
			bVo.setLvl(lvlnum);
			
			bDao.insertReplyBoard(bVo);
			
		}
		
		//response.sendRedirect("http://localhost:8081/web-study-11/BoardServlet?command=board_list");

		new BoardListAction().execute(request, response);
	}
}
