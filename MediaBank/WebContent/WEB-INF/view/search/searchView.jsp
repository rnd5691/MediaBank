<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Media Bank</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="../css/header.css" rel="stylesheet">
<link href="../css/search/searchView.css" rel="stylesheet">
<script type="text/javascript">
	$(function(){
		$("#down").click(function(){
			$("#frm").prop("action", "../payment/paymentDown.payment");
			$("#frm").submit();
		});
	});
</script>
</head>
<body oncontextmenu="return false" ondragstart="return false" onselectstart="return false">
<!-- header start -->
<c:import url="../temp/header.jsp"></c:import>
<!-- header finish -->

<!-- contents start -->
<!-- menu는 mypage나 구매목록이 나오는 탭 부분 -->

<c:if test="${!empty requestScope.work}">
<div class="body">

<div class="totalbody">
	<div class="title">
		<h1>${requestScope.work.work}</h1>
	</div>
	<form action="payment.search" method="post" id="frm">
		<input type="hidden" name="work_seq" value="${requestScope.work.work_seq}">
		<table class="table">
			<tr>
				<c:if test="${requestScope.file.file_kind eq 'image'}">
            		<td rowspan="9" colspan="2">
						<img src="${pageContext.request.contextPath}/upload/${requestScope.file.file_name}">
					</td>
				</c:if>
				<c:if test="${requestScope.file.file_kind eq 'video'}">
					<td rowspan="8" colspan="2">
						<video src="${pageContext.request.contextPath}/upload/${requestScope.file.file_name} " width="310" height="310" controls="controls"></video>
					</td>
				</c:if>
			</tr>
			<tr>
				<td>작품명</td>
				<td><input name="work" type="text" readonly="readonly" value="${requestScope.work.work}"></td>
			</tr>
			<tr>
				<td>작가명</td>
				<td><input name="nickname" type="text" readonly="readonly" value="${requestScope.work.nickname}"></td>
			</tr>
			<tr>
				<td>등록 일자</td>
				<td><input name="work_date" type="date" readonly="readonly" value="${requestScope.work.work_date }"></td>
			</tr>
			<tr>
				<td>가격</td>
				<td><input name="price" type="text" readonly="readonly" value="${requestScope.work.price}"></td>
			</tr>
			<c:if test="${requestScope.file.file_kind eq 'image'}">
		         <tr>
		            <td>파일 사이즈</td>
		            <td><input class="size" name="width" type="text" readonly="readonly" value="${requestScope.file.width }"> X <input class="size" name="height" type="text"readonly="readonly" value="${requestScope.file.height}"></td>
		         </tr>
	     	</c:if>
			<tr>
				<td>상세 내용</td>
				<td><textarea name="contents" readonly="readonly">${requestScope.work.contents}</textarea></td>
			</tr>
			<tr>
				<td>태그</td>
				<td><textarea name="tag" readonly="readonly">${requestScope.work.tag}</textarea></td>
			</tr>
			<tr>
				<td class="download"colspan="4">
					<c:if test="${!empty sessionScope.member and empty requestScope.buyCheck}">
						<button class="btn btn-default">결제하기</button>
					</c:if>
					<c:if test="${!empty sessionScope.member and !empty requestScope.buyCheck}">
 						<input id="down" type="button" class="btn btn-default" value="다운로드">
 					</c:if>
					<c:if test="${empty sessionScope.member }">
						<textarea readonly="readonly">로그인 후에 다운로드 가능합니다.</textarea>
					</c:if>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="push"></div>
</div>
</c:if>
<c:if test="${empty requestScope.work}">
	<script type="text/javascript">
		alert('해당 하는 번호가 없습니다');
		location.href = "${pageContext.request.contextPath}/index.jsp";
	</script>
</c:if>
<!-- contents finish -->

<!-- footer start -->
<c:import url="../temp/footer.jsp"></c:import>
<!-- footer finish -->
</body>
</html>