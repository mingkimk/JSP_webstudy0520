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
</head>
<body>
	<div id="wrap" align="center">
		<h1>게시글 리스트</h1>
		<table class="list">
			<tr>
				<td colspan="2" style="border: white; text-align: left">접속자 : ${sessionScope.userid }</td>
				<td colspan="3" style="border: white; text-align: right"><a
					href="BoardServlet?command=board_write_form">게시글 등록</a></td>
			</tr>
			<tr>
				<th width="10%" align=center>번호</th>
				<th width="50%" align=center>제목</th>
				<th width="15%" align=center>작성자</th>
				<th width="15%" align=center>작성일</th>
				<th width="10%" align=center>조회</th>
			</tr>
			<c:forEach var="board" items="${boardList }" begin="${pagemaker.nowPageStart }" varStatus="bs">
				<tr class="record">
					<td align=center>${bs.count + ((pageMaker.page-1)*10) }</td>
					<td >
						<c:forEach begin="1" end="${board.lvl }">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						</c:forEach>
						<c:if test="${board.lvl > 0 }">
							<span>[답변]</span>
						</c:if>
						<a href="BoardServlet?command=board_view&num=${board.num}&page=${pageMaker.prev}">
							${board.title } </a></td>
					<td align=center>${board.name}</td>
					<td align=center><fmt:formatDate value="${board.writedate }" /></td>
					<td align=center>${board.readcount}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan=5 align=center>
					<hr>
					<ul class="pageUL">
					
						<c:if test="${pageMaker.prev > 0 }">
							<a href='BoardServlet?command=board_list&page=${pageMaker.prev}'> [ 이전 ] </a>
						</c:if>
						<c:forEach begin="${pageMaker.start }" end="${pageMaker.end}"
							var="idx">
							<!-- 			<li class='<c:out value="${idx == pageMaker.page?'current':''}"/>'>   -->
							<a href='BoardServlet?command=board_list&page=${idx}'>
								<c:choose>
									<c:when test="${pageMaker.page eq idx}">
									<b>[<font color=red size=3> ${idx} </font> ]</b>
									</c:when>
									<c:otherwise>[ ${idx} ] </c:otherwise>
								</c:choose>
							</a>
						</c:forEach>
						<c:if test="${pageMaker.next > 0 }">
							<a href='BoardServlet?command=board_list&page=${pageMaker.next}'> [ 다음 ] </a>
						</c:if>
						

					</ul>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>