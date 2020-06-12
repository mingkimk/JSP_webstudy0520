package com.saeyan.controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.saeyan.dao.BoardDAO;
import com.saeyan.dto.BoardVO;

public class BoardWriteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO bDao = BoardDAO.getInstance();
		int indexNum = bDao.nowNum();

		//
		// SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd
		// HH:mm:ss", Locale.KOREA );
		// Date currentTime = new Date ();
		// String mTime = mSimpleDateFormat.format ( currentTime );

		// 파일 업로드
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 여기를 바꿔주면 다운받는 경로가 바뀜
		 String savePath = "./board/upload";
		// 최대 업로드 파일 크기 5MB로 제한
		int uploadFileSizeLimit = 5 * 1024 * 1024;
		//용량 단위 비트/바이트/키토바이트/메가바이트/기가 바이트/테라 바이트 ex>5MB
		String encType = "UTF-8";
		// ServletContext context = getServletContext();
		// String uploadFilePath = context.getRealPath(savePath);
		String uploadFilePath = (String)request.getAttribute("uploadFilePath");		                                              
		System.out.println("서버상의 실제 디렉토리 :" + uploadFilePath);
		try {
			MultipartRequest multi = new MultipartRequest(request, // request 객체
					uploadFilePath, // 서버상의 실제 디렉토리
					uploadFileSizeLimit, // 최대 업로드 파일 크기
					encType, // 인코딩 방법
					// 동일한 이름이 존재하면 새로운 이름이 부여됨
					new DefaultFileRenamePolicy());
			// 업로드된 파일의 이름 얻기
			String fileName = multi.getFilesystemName("uploadFile");
			if (fileName == null) { // 파일이 업로드 되지 않았을때
				System.out.print("파일 업로드 되지 않았음");
			} else { // 파일이 업로드 되었을때
				System.out.println("업로드 성공!!"+multi.getParameter("name"));
				//request.setAttribute("fname", fileName);
			} // else
			if (indexNum != -1) {
				BoardVO bVo = new BoardVO();
				bVo.setNum(indexNum);
				bVo.setName(multi.getParameter("name"));
				bVo.setPass(multi.getParameter("pass"));
				bVo.setEmail(multi.getParameter("email"));
				bVo.setTitle(multi.getParameter("title"));
				bVo.setContent(multi.getParameter("content"));
				bVo.setFname(fileName);
				bDao.insertBoard(bVo);
			}
		} catch (Exception e) {
			System.out.print("예외 발생 : " + e);
		} // catch

		//

		new BoardListAction().execute(request, response);
	}

}
