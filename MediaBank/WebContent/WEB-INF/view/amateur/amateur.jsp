<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../css/header.css" rel="stylesheet">
<link href="../css/amateur/amateurList.css" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<!-- header start -->
<c:import url="../temp/header.jsp"></c:import>
<!-- header finish -->

<!-- contents start -->
<div class="body">
		<form action="" method="post">
			<div class="allbody">
				<div class="amatitle">
					<h1>무명 작가 작품</h1>&nbsp;&nbsp;<h4>List</h4>
				</div>
				<div class="amasearch">
					<select class="select">
						<option>작품명</option>
						<option>작가명</option>
						<option>태그</option>
					</select>
					<input type="text" placeholder="검색어" id="image1" class="image1" value="">
					<button class="btn btn-default">검색</button>
				</div>
				<div class="amacontainer">
					<div class="container">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#image">이미지</a></li>
							<li><a data-toggle="tab" href="#video">동영상</a></li>
						</ul>
						<div class="tab-content">
							<div id="image" class="tab-pane fade in active">
								<table id="table2">
									<tr>
										<td><div class="piece" id="piece1"></div></td>
										<td><div class="piece"></div></td>
										<td><div class="piece"></div></td>
										<td><div class="piece"></div></td>
									</tr>
									<tr class="trtable1">
										<td class="tdtable1" id="tdtable1">작품명:<input type="text" id="work" name="work" value=""></td>
										<td class="tdtable1">작품명:<input type="text" id="work" name="work" value=""></td>
										<td class="tdtable1">작품명:<input type="text" id="work" name="work" value=""></td>
										<td class="tdtable1">작품명:<input type="text" id="work" name="work" value=""></td>
									</tr>
									<tr class="trtable1">
										<td class="tdtable1" id="tdtable1">작가명:<input type="text" id="nickname" name="nickname" value=""></td>
										<td class="tdtable1">작가명:<input type="text" id="nickname" name="nickname" value=""></td>
										<td class="tdtable1">작가명:<input type="text" id="nickname" name="nickname" value=""></td>
										<td class="tdtable1">작가명:<input type="text" id="nickname" name="nickname" value=""></td>
									</tr>
								</table>
							</div>
							<div id="video" class="tab-pane fade">
      							<table class="table1" id="table3">
									<tr>
										<td><div class="piece" id="piece1"></div></td>
										<td><div class="piece"></div></td>
										<td><div class="piece"></div></td>
										<td><div class="piece"></div></td>
									</tr>
									<tr class="trtable1">
										<td class="tdtable1" id="tdtable1">작품명:<input type="text" id="work" name="work" value=""></td>
										<td class="tdtable1">작품명:<input type="text" id="work" name="work" value=""></td>
										<td class="tdtable1">작품명:<input type="text" id="work" name="work" value=""></td>
										<td class="tdtable1">작품명:<input type="text" id="work" name="work" value=""></td>
									</tr>
									<tr class="trtable1">
										<td class="tdtable1" id="tdtable1">작가명:<input type="text" id="nickname" name="nickname" value=""></td>
										<td class="tdtable1">작가명:<input type="text" id="nickname" name="nickname" value=""></td>
										<td class="tdtable1">작가명:<input type="text" id="nickname" name="nickname" value=""></td>
										<td class="tdtable1">작가명:<input type="text" id="nickname" name="nickname" value=""></td>
									</tr>
								</table>
    						</div>
						</div>
					</div>	
				</div>
			</div>
		</form>
	<div class="push"></div>
</div>
<!-- contents finish -->

<!-- footer start -->
<c:import url="../temp/footer.jsp"></c:import>
<!-- footer finish -->
</body>
</html>