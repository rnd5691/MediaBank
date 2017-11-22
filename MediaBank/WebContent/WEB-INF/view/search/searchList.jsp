<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- css -->
<link href="../css/index.css" rel="stylesheet">
<link href="../css/header.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- flex Imagae plugin -->
<link href="${pageContext.request.contextPath}/plugin/jQuery-flexImages-master/jquery.flex-images.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/plugin/jQuery-flexImages-master/jquery.flex-images.min.js"></script>

<title>Insert title here</title>
<style type="text/css">
</style>
<script>
	$(function() {
		var API_KEY = '5591688-41cb145c7a8c9f609261640ef';
		var count = 1;
		var paging = 1; 
		var perPage = 1;
		try {
			perPage = ${requestScope.perPage};
		} catch (e) {
			perPage = 1;
		}
		
		$("#search").keydown(function(key){
			if(key.keyCode==13){
				if ($("#select").val() == "video") {
					$("#kind").prop("value", false);
				} else {
					$("#kind").prop("value", true);
				}
				document.frm.submit();
			}
		});
		
		$("#btn").click(function() {
			if ($("#select").val() == "video") {
				$("#kind").prop("value", false);
			} else {
				$("#kind").prop("value", true);
			}
			document.frm.submit();
		});
		
		var URL = "https://pixabay.com/api/?key=" + API_KEY + "&q="
				+ encodeURIComponent('${requestScope.search}')+"&pretty=true"+"&page="+perPage;
		
		if("${requestScope.select}"=="video"){
			alert("비디오");
			URL = "https://pixabay.com/api/videos/?key="+ API_KEY + "&q="
			+ encodeURIComponent('${requestScope.search}')+"&pretty=true"+"&page="+perPage;
		} else {
			alert("사진")
			URL = "https://pixabay.com/api/?key=" + API_KEY +"&q="
			+ encodeURIComponent('${requestScope.search}')+"&pretty=true"+"&page="+perPage;
		}

		var check = true;
		try {
			check = ${requestScope.check};
		} catch (Exception) {
			check = true;
		}
		
		if(check==true){
			$('#tab1').prop('class', "active");
			$('#home').prop('class', "tab-pane fade active in");
			$('#menu1').prop('class',"tab-pane fade");
			$('#tab2').prop('class', "");
			check=true;
		} else if(check==false) {
			$('#tab2').prop('class', "active");
			$('#menu1').prop('class',"tab-pane fade active in");
			$('#home').prop('class', "tab-pane fade");
			$('#tab1').prop('class', "");
			check==false;
		}
		
		
		$.getJSON(URL, function(data) {
			if (parseInt(data.totalHits) > 0) {
				for(var i=0; i<data.totalHits/20; i++){
					$('.paging').append('<a href=searchList.search?search='+'${requestScope.search}'+'&kind=${requestScope.kind}&select=${requestScope.select}&check=false&perPage='+paging+'>'+paging+'</a>');
					paging++;
				}
				$.each(data.hits, function(i, hit) {
					if("${requestScope.select}"=="video"){
						$(".flex-image").append("<div class='item item"+count+"'><video id=video"+count+" controls><div>");
						$("#video" + count).prop("src", hit.videos.tiny.url);
						$("#select-video").prop("selected", true);
						count++;
					} else {
						$(".flex-image").append("<div class='item item"+count+"'><img id=img"+count+"><div>");
						var re = hit.webformatURL.indexOf('_');
						var lstr = hit.webformatURL.lastIndexOf('.');
						var lstr2 = hit.webformatURL.lastIndexOf('_');//_640.jpg
						var str = hit.webformatURL.substring(re); //_640
						var str2 = hit.webformatURL.substring(lstr); //.jpg
						var str3 = hit.webformatURL.substring(0, lstr2); //주소
						str = '_180';
						var URL = str3 + str + str2;
						$("#img" + count).prop("src", URL);
						$("#select-img").prop("selected", true);
						count++;
					}
				});	
			} else {
				console.log('No hits');
			}
		});
		
	});
</script>
<style type="text/css">
	.search_con {
		width: 100%;
		height: 200px;
	}
	.item {
		float: left;
	}
	.item img {
		opacity: 0.9;
    	/* margin: 1px; */
    	height: 180px;
    	/* width: 100%; */
    	padding: 2px;
   		width: 280px;
    	margin: 0 auto;
	}
	.item video {
	    display: inline-block;
    	vertical-align: baseline;
    	margin: 2px;
    	height: 360px;
    	width: 566px;
	}
	.item img:hover {
		opacity: 1;
	}
	.paging {
		margin: 0 auto;
    	text-align: center;
    	padding: 50px;
	}
	.paging a {
		margin: 10px;
	}
	.flex-image {
	    width: 1220px;
    	margin: 0 auto;
    	height: 900px;
    	display: block;
    	max-width: 100%;
	}
	#search_warp {
    	width: 700px;
    	padding: 50px;
    	left: auto;
    	margin: 10px auto;
    	border-radius: 5px;
    	background-color: white;
    	position: relative;
    	padding-top: 270px;
    	/* top: -200px; */
	}
	.form-control {
    	display: inline-block;
    	width:442px;
    	height: 34px;
    	padding: 6px 12px;
    	font-size: 14px;
    	line-height: 1.42857143;
    	color: #555;
    	background-color: #fff;
    	background-image: none;
    	border: 0;
    	border-radius: 4px;
    	-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
    	box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
    	-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
    	-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    	transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	}
	#f_position {
    	left: 30%;
    	top: 500px;
    	width: 100%;
    	border: 1px solid;
	}
	.body {
		width: 1134px;
	}
</style>
</head>
<body>
	<c:import url="${pagecontext.request.contextpath}/WEB-INF/view/temp/header.jsp"></c:import>
	<div class="body">
		<div class="search_con">
			<div id="search_warp">
				<form action="searchList.search" name="frm" id="f_position">
					<input type="text" id="search" name="search" class="form-control" placeholder="Search images, videos"> 
					<input type="hidden" id="kind" name="kind">
					<input type="hidden" name="perPage" value="1">
					<input type="hidden" id="check" name="check" value="${requestScope.check }">
					<select id="select" name="select" class="form-control">
						<option id="select-img" value="image">사진</option>
						<option id="select-video" value="video">비디오</option>
					</select> 
					<input type="button" class="btn btn-default" id="btn">
				</form>
			</div>
		</div>
		
		<div class="container">
  <h2>Dynamic Tabs</h2>
  <p>To make the tabs toggleable, add the data-toggle="tab" attribute to each link. Then add a .tab-pane class with a unique ID for every tab and wrap them inside a div element with class .tab-content.</p>

  <ul class="nav nav-tabs">
    <li id="tab1" class="active"><a id="tab1-menu" data-toggle="tab" href="#home">아마추어 작가</a></li>
    <li id="tab2"><a id="tab2-menu" data-toggle="tab" href="#menu1">기타 작품</a></li>
  </ul>

  <div class="tab-content">
   <div id="home" class="tab-pane fade in active">
      <h3>아마추어 작가 작품</h3>
      <div id="flex-image2" class="flex-image2">
      	<c:forEach items="${requestScope.author}" var="author">
      		<div class="item item${author.file_num}">
      			<c:if test="${author.file_kind eq 'image' }">
      				<a href="searchView.search?work_seq=${author.work_seq}"><img src="${pageContext.request.contextPath}/upload/${author.file_name}"></a>
      			</c:if>
      			<c:if test="${author.file_kind eq 'video' }">		
 -      			<video src="${pageContext.request.contextPath}/upload/${author.file_name}" width="280" height="180" controls="controls"></video>		
 -      		</c:if>
      		</div>
      	</c:forEach>
	   </div>
	   <div class="paging2"></div>
	</div>
    <div id="menu1" class="tab-pane fade">
      <h3>기타 작품</h3>
      <div id="flex-image" class="flex-image">
	  </div>
	  <div class="paging"></div>
	</div>	
    </div>
	</div>
	
	</div>
	<div class="push"></div>
	<!-- footer -->
	<c:import
		url="${pagecontext.request.contextpath}/WEB-INF/view/temp/footer.jsp"></c:import>
	<!-- footer finish -->
</body>
</html>