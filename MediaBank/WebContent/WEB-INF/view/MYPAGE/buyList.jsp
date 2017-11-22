<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>구매 목록</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="../css/header.css" rel="stylesheet">
<link href="../css/mypage/mypage.css" rel="stylesheet">
<link href="../css/mypage/List.css" rel="stylesheet">
<script type="text/javascript">
	$(function(){
		$("#buyList").css('color', 'white');
		$("#buyList").css('background-color', '#83b14e');
	});
</script>
</head>
<body>
<!-- header start -->
<c:import url="../temp/header.jsp"></c:import>
<!-- header finish -->

<!-- contents start -->
<!-- menu는 mypage나 구매목록이 나오는 탭 부분 -->

<div class="body">
	<c:import url="./menu.jsp"></c:import>
	<div class="totalbody">
		<div class="title">
			<h1>My Page</h1>&nbsp;&nbsp;<h5>구매 목록</h5>
		</div>
		<input type="hidden" id="user_num" name="user_num" value="${sessionScope.member.user_num}">
		
		<div>
			<table class="table table-hover">
					<tr class="table-title">
						<td>번호</td>
						<td class="title_contents">작품명</td>
						<td>작가명</td>
						<td>구매금액</td>
						<td>구매일자</td>
					</tr>
					<c:forEach items="${requestScope.list}" var="dto">
						<tr>
							<td>${dto.buy_seq}</td>
							<td>${dto.work}</td>
							<td>${dto.nickname}</td>
							<td>${dto.price}</td>
							<td>${dto.buy_date}</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${makePage.totalPage > 0}">
					<div class="paging">
						<ul class="pagination">
							<c:if test="${makePage.curBlock>1}">
								<li><a href="./mypageBuyList.mypage?curPage=1&user_num=${member.user_num}">&lt;&lt;</a></li>
								<li><a href="./mypageBuyList.mypage?curPage=${makePage.startNum-1}&user_num=${member.user_num}">[이전]</a></li>
							</c:if>
							<c:forEach begin="${makePage.startNum}" end="${makePage.lastNum}" var="i">
								<li><a href="./mypageBuyList.mypage?curPage=${i}&user_num=${member.user_num}">${i}</a></li>
							</c:forEach>
							<c:if test="${makePage.curBlock < makePage.totalBlock}">
								<li><a href="./mypageBuyList.mypage?curPage=${makePage.lastNum+1}&user_num=${member.user_num}">[다음]</a></li>
								<li><a href="./mypageBuyList.mypage?curPage=${makePage.totalPage}&user_num=${member.user_num}">&gt;&gt;</a></li>
							</c:if>
						</ul>
					</div>
				</c:if>
		</div>
	</div>
	<div class="push"></div>
</div>

<!-- contents finish -->

<!-- footer start -->
<c:import url="../temp/footer.jsp"></c:import>
<!-- footer finish -->
</body>
</html>