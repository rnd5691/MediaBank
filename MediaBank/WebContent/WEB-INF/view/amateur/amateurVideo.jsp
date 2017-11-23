<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Artist Gallery</title>
<link href="../css/header.css" rel="stylesheet">
<link href="../css/amateur/amateurList.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath }/css/amateur/amateurList.css"
	rel="stylesheet">
<script type="text/javascript">
	$(function() {
		$(".select").click(function() {
			$(this).prop("selected", true);
		});
	});
</script>
</head>
<body>
	<!-- header start -->
	<c:import url="../temp/header.jsp"></c:import>
	<!-- header finish -->

	<!-- contents start -->
	<div class="body">
		<!-- MAIN (Center website) -->
		<div class="main">

			<h1 class="header_font">Artist Gallery</h1>
			<hr>
			<!-- <hr> -->
			<div class="input-group">
				<form action="amateur.amateur" style="display: inherit;">
					<input type="text" class="form-control" name="search"
						placeholder="Search">
					<div class="input-group-btn">
						<select class="sel form-control" name="select" id="select"
							style="width: 100px">
							<option class="select" value="work">작품명</option>
							<option class="select" value="tag">태그</option>
							<option class="select" value="nickname">작가명</option>
						</select>
						<button id="btn" class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</form>
			</div>

			<!-- ssss -->
			<div class="container" style="padding: 0">
				<h4>Exhibition space of huge and excellent artists</h4>
				<ul class="nav nav-tabs">
					<li><a href="amateur.amateur?kind=image">사진</a></li>
					<li class="active"><a href="amateurVideo.amateur?kind=video">동영상</a></li>
				</ul>
				<br>
				<h3>동영상</h3>
				<p>무명 작가들의 다양하고 퀄리티 있는 동영상들을 만나보세요.</p>
				<!-- Portfolio Gallery Grid -->
				<div class="row">
					<c:forEach items="${requestScope.author }" var="author">
						<c:if test="${author.file_kind eq 'video'}">
							<div class="column nature">
								<div class="content">
									<a href="../search/searchView.search?work_seq=${author.work_seq}"><video src="${pageContext.request.contextPath}/upload/${author.file_name }" style="width: 100%"></video></a>
									<h4>작품명 : ${author.work }</h4>
									<p>작가명 : ${author.nickname }</p>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
				<c:if test="${requestScope.page.curBlock>1}">
					<a
						href="./amateur.amateur?curPage=1&select=${requestScope.select}&search=${requestScope.search}">&lt;&lt;</a>
					<a
						href="./amateur.amateur?curPage=${page.startNum-1}&select=${requestScope.select}&search=${requestScope.search}">[이전]</a>
				</c:if>
				<div class="number" style="text-align: center">
				<ul class="pagination">
				<c:forEach begin="${page.startNum }" end="${page.lastNum }"	var="page">
					<li><a href="./amateur.amateur?curPage=${page}&select=${requestScope.select}&search=${requestScope.search}">${page}</a></li>
				</c:forEach>
				</ul>
				</div>
				<c:if test="${requestScope.page.curBlock < requestScope.page.totalBlock}">
					<li><a
						href="./amateur.amateur?curPage=${requestScope.page.getLastNum()+1}&select=${requestScope.select}&search=${requestScope.search}">[다음]</a></li>
					<li><a
						href="./amateur.amateur?curPage=${requestScope.page.totalPage}&select=${requestScope.select}&search=${requestScope.search}">&gt;&gt;</a></li>
				</c:if>
			</div>
		</div>


	<!-- END GRID -->

	<!-- END MAIN -->
	<script>
		filterSelection("all")
		function filterSelection(c) {
			var x, i;
			x = document.getElementsByClassName("column");
			if (c == "all")
				c = "";
			for (i = 0; i < x.length; i++) {
				w3RemoveClass(x[i], "show");
				if (x[i].className.indexOf(c) > -1)
					w3AddClass(x[i], "show");
			}
		}

		function w3AddClass(element, name) {
			var i, arr1, arr2;
			arr1 = element.className.split(" ");
			arr2 = name.split(" ");
			for (i = 0; i < arr2.length; i++) {
				if (arr1.indexOf(arr2[i]) == -1) {
					element.className += " " + arr2[i];
				}
			}
		}

		function w3RemoveClass(element, name) {
			var i, arr1, arr2;
			arr1 = element.className.split(" ");
			arr2 = name.split(" ");
			for (i = 0; i < arr2.length; i++) {
				while (arr1.indexOf(arr2[i]) > -1) {
					arr1.splice(arr1.indexOf(arr2[i]), 1);
				}
			}
			element.className = arr1.join(" ");
		}
	</script>
	<div class="push"></div>
	</div>
	<!-- contents finish -->

	<!-- footer start -->
	<c:import url="../temp/footer.jsp"></c:import>
	<!-- footer finish -->
</body>
</html>