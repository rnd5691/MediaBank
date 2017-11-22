<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내 작품 판매 승인 현황</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="../css/header.css" rel="stylesheet">
<link href="../css/mypage/mypage.css" rel="stylesheet">
<link href="../css/mypage/salesRequestNow.css" rel="stylesheet">
<script type="text/javascript">

var file_kind = "${file_kind}";
var user_num = "${user_num}";
$(function(){
	if(file_kind == "image")	{
		$("#li_image").addClass("active");
		$("#li_video").removeClass("active");
		$("#image").addClass("in active");
		$("#video").removeClass("in active");
	}else if(file_kind == "video"){
		$("#li_video").addClass("active");
		$("#li_image").removeClass("active");
		$("#image").removeClass("in active");
		$("#video").addClass("in active");
	}
	$("#btn_image").click(function(){
		var sell = "";
		$("input:checkbox[name=jin]").each(function(){
			if((this).checked)	{
				if(sell==""){
					sell=this.id+"_Y";
				}else	{
					sell+=","+this.id+"_Y";
				}
			}else	{
				if(sell==""){
					sell=this.id+"_N";
				}else	{
					sell+=","+this.id+"_N";
				}
			}
			
		});
		 $.ajax({
             	type : "POST",
	            url : "mypageSalesRequestNowUpdate.mypage",
	            data : {"sell":sell, "user_num":user_num, "file_kind":file_kind},	
	            dataType : "text",
	            error : function(){
	                alert('저장이 실패하였습니다.');
	            },
	            success : function(data){
	                alert("저장이 완료되었습니다.") ;
	            }
	             
	    });
	});
	
	$("#btn_video").click(function(){
		var sell = "";
		$("input:checkbox[name=movie]").each(function(){
			if((this).checked)	{
				if(sell==""){
					sell=this.id+"_Y";
				}else	{
					sell+=","+this.id+"_Y";
				}
			}else	{
				if(sell==""){
					sell=this.id+"_N";
				}else	{
					sell+=","+this.id+"_N";
				}
			}
			
		});
		 $.ajax({
             	type : "POST",
	            url : "mypageSalesRequestNowUpdate.mypage",
	            data : {"sell":sell, "user_num":user_num, "file_kind":file_kind},	
	            dataType : "text",
	            error : function(){
	                alert('저장이 실패하였습니다.');
	            },
	            success : function(data){
	                alert("저장이 완료되었습니다.") ;
	            }
	             
	    });
	});
	
	<c:forEach items="${requestScope.file}" var="dto">
		if(${dto.sell eq 'Y'}){
			$("#" + ${dto.file_num}).prop("checked", true);
		}else	{
			$("#" + ${dto.file_num}).prop("checked", false);
		}
	</c:forEach>
	
	<c:forEach items="${requestScope.video}" var="dto">
		if(${dto.sell eq 'Y'}){
			$("#" + ${dto.file_num}).prop("checked", true);
		}else	{
			$("#" + ${dto.file_num}).prop("checked", false);
		}
	</c:forEach>
});

function fn_tabClick(tabId){
	if(tabId == 'sajin')	{
		$("#file_kind").val("image");
	}else if(tabId == 'youngsang')	{
		$("#file_kind").val("video");
	}
	document.frm.action="mypageSalesRequestNow.mypage";
	document.frm.method="POST";
	document.frm.submit();
}

$(function(){
	$("#salesNow").css('color', 'white');
	$("#salesNow").css('background-color', '#83b14e');
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
<form action="mypageSalesRequestNow.mypage" method="post" name="frm">
	

<div class="totalbody">
	<div class="title">
		<h1>My Page</h1>&nbsp;&nbsp;<h5>현재 판매 중인 내 작품</h5>
	</div>
	<div class="imagebody">
		<input type="hidden" id="user_num" name="user_num" value="${sessionScope.member.user_num}">
		<input type="hidden" id="file_kind" name="file_kind">
	<div class="container">
	  <ul class="nav nav-tabs">
    	<li id="li_image" class="active"><a data-toggle="tab" href="#image" onclick="fn_tabClick('sajin')">이미지</a></li>
    	<li id="li_video"><a data-toggle="tab" href="#video" onclick="fn_tabClick('youngsang')">동영상</a></li>
    </ul>
	<div class="tab-content">
	<div id="image" class="tab-pane fade in active">
    	<table>
   	<c:forEach items="${requestScope.file}" var="dto">
    <c:if test="${dto.file_kind eq 'image'}">
			<tr class="trtable1">
				<td class="tdtable1">
						<input type="checkbox" class="img" name="jin" id="${dto.file_num}">
					<div class="images1">
						<img class="imgvideo" style="width:200px; height:200px;" src="${pageContext.request.contextPath}/upload/${dto.file_name}">
					</div>
				</td>
			</tr>
		</c:if>
		</c:forEach>
		</table>
		<button type="button" class="btn btn-default" id="btn_image">저장</button>	
    </div>
    <div id="video" class="tab-pane fade">
    	<table>
    <c:forEach items="${requestScope.video}" var="dao">
    	<c:if test="${dao.file_kind eq 'video'}">
			<tr class="trtable1">
				<td class="tdtable1">
					<input type="checkbox" class="img" name="movie" id="${dao.file_num}">
					<div class="images1">
						<video style="width:200px; height:200px;" src="${pageContext.request.contextPath}/upload/${dao.file_name}"></video>
					</div>
				</td>
			</tr>
		</c:if>
    </c:forEach>
		</table>
    <button type="button" class="btn btn-default" id="btn_video">저장</button>	
    </div>
    
  </div>
</div>
			
	<c:if test="${makePage.totalPage>0 }">
		<div class="paging">
			<ul class="pagination">
				<c:if test="${makePage.curBlock>1}">
					<li><a href="./mypageSalesRequestNow.mypage?curPage=1&user_num=${member.user_num}&file_kind=${file_kind}">&lt;&lt;</a></li>
					<li><a href="./mypageSalesRequestNow.mypage?curPage=${makePage.startNum-1}&user_num=${member.user_num}&file_kind=${file_kind}">[이전]</a></li>
				</c:if>
				<c:forEach begin="${makePage.startNum}" end="${makePage.lastNum}" var="i">
					<li><a href="./mypageSalesRequestNow.mypage?curPage=${i}&user_num=${member.user_num}&file_kind=${file_kind}">${i}</a></li>
				</c:forEach>
				<c:if test="${makePage.curBlock<makePage.totalBlock}">
					<li><a href="./mypageSalesRequestNow.mypage?curPage=${makePage.lastNum+1}&user_num=${member.user_num}&file_kind=${file_kind}">[다음]</a></li>
					<li><a href="./mypageSalesRequestNow.mypage?curPage=${makePage.totalPage}&user_num=${member.user_num}&file_kind=${file_kind}">&gt;&gt;</a></li>
				</c:if>
			</ul>
		</div>
	</c:if>
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