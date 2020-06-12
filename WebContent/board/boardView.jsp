<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/shopping.css">
<script type="text/javascript" src="script/board.js"></script>
</head>
<body>
	<div id="wrap" align="center">
		<h1>게시글 상세보기</h1>
		<table width=1024 border=1>
			<tr>
				<th width=20%>작성자</th>
				<td width=30%>${board.name}</td>
				<th width=20%>이메일</th>
				<td width=30%>${board.email}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td><fmt:formatDate value="${board.writedate}" /></td>
				<th>조회수</th>
				<td>${board.readcount }</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">${board.title }</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3"><pre>${board.content }</pre></td>
			</tr>
				
			<tr>
				<th>파일</th>
				<td colspan="3">${board.fname} <br>
				<img src="./board/upload/${board.fname}" width=400>
				</td>
			</tr>
			
		</table>
		<br>
		<table>
			<tr>
				<td width=5% align=center>순번</td>
				<td width=10% align=center>작성자</td>
				<td width=75% align=center>내용</td>
				<td width=10% align=center>원글번호</td>
			</tr>
			<c:forEach var="board" items="${commentList }">
				<tr>
					<td  align=center>${board.cno }</td>
					<td  align=center>${board.id}</td>
					<td>${board.cMemo}</td>
					<td  align=center>${board.num}</td>
				</tr>
			</c:forEach>
			<tr>
				<form action="CommentServlet" method="post">
					<input type="hidden" name="command" value="comment_write">
					<td colspan=2 align=center>작성자 : ${sessionScope.userid }</td>
					<td align=center>내용 : <input type=text name="commentmemo" size=95></td>
					<input type=hidden name="commentno" value="${board.num}">
					<td><input type=submit value="save"></td>
				</form>
			</tr>
		</table>
		</table>
		<br> <br> <input type="button" value="게시글 수정"
			onclick="open_win('BoardServlet?command=board_check_pass_form&num=${board.num}', 'update')">
		<input type="button" value="게시글 삭제"
			onclick="open_win('BoardServlet?command=board_check_pass_form&num=${board.num}', 'delete')">
		<input type="button" value="게시글 리스트"
			onclick="location.href='BoardServlet?command=board_list&nowuser=${sessionScope.userid }'">
		<input type="button" value="답글"
			onclick="location.href='BoardServlet?command=board_write_reply_form&num=${board.num}'">
		<input type="button" value="게시글 등록"
			onclick="location.href='BoardServlet?command=board_write_form'">
	</div>
</body>
</html>