<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
<style type="text/css">
p {
    margin-top: 0;
    margin-bottom: 1rem;
    font-size: 33px;
    font-weight: bold;
}
#home {
	color :#83b14e;
}
#home:hover {
	color :#83b14e;
	text-decoration: none;
}
</style>
</head>
<body>
	<!-- contents start -->
	<div class="body">
	<div style="text-align:-webkit-center; "><img src="${pageContext.request.contextPath}/images/404error.png" width="400" style="margin: 30px 50px 30px 50px;"><h1  style="font-size: 6.5rem; color: #83b14e">500 ERROR PAGE</h1></div>
	<div style="text-align:-webkit-center">
		<p>Sorry dude vary bad news,</p>
		<p>we cant find your Page :(</p>
		<p><span style="font-size: 20px;">but, the good news
		that your still jump the</span><span style="color:#83b14e;"><a id="home" href="${pageContext.request.contextPath}/index.jsp"> HomePage</a></span></p>	
	</div>
	<div class="push"></div>
	</div>
	<!-- contents end -->
</body>
</html>