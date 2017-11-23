<!-- 회원 가입시, 계정 선택 부분 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 가입</title>
<link href="../css/header.css" rel="stylesheet">
<link href="../css/join/joinForm_info.css" rel="stylesheet">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>   
<script type="text/javascript">
	$(function(){
		$("#add").load('memberTable.member?kind='+'${sessionScope.naver.kind}');
	});
</script>
<body oncontextmenu="return false" ondragstart="return false" onselectstart="return false">
<c:import url="../temp/header.jsp"></c:import>
<!-- Contents -->
<section class="body">
	<article class="contents">
		<article class="title">
			<h1>회원 가입</h1> <h5>개인 회원 가입</h5>
		</article>
		<article class="join_info">
			<form name="frm" method="post" action="memberInsert.member">
				<input type="hidden" name="kind" value="person">
				<table class="table table-striped">
					<tr>
						<td><span>*</span>ID</td>
						<td>
							<input id="id"type="text" name="id" readonly="readonly" value="${sessionScope.naver.id}">
						</td>
					</tr>
					<tr>
						<td>연락처</td>
						<td><input type="text" name="phone"></td>
					</tr>
					<tr>
						<td><span>*</span>이메일</td>
						<td><input type="email" name="email" readonly="readonly" value="${sessionScope.naver.email}"></td>
					</tr>
					<tbody id="add"></tbody>
				</table>
				<div>
					<a href="memberAgreement.member" class="btn btn-default">취소</a>
					<button id="btn" class="btn btn-default">확인</button>
				</div>
			</form>
		</article>
	</article>
	<div class="push"></div>
</section>
<!-- Contents 끝 -->
<c:import url="../temp/footer.jsp"></c:import>
</body>
</html>