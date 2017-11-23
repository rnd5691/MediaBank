<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function(){
		<c:forEach items="${requestScope.file}" var="dto">
		if(${dto.sell eq 'Y'}){
			$("#" + ${dto.file_num}).prop("checked", true);
		}else{
			$("#" + ${dto.file_num}).prop("checked", false);
		}
	</c:forEach>
	});
</script>
</head>
<body>
<table>
	   	<c:forEach items="${requestScope.file}" var="dto">
	    <c:if test="${dto.file_kind eq 'image'}">
				<tr class="trtable1">
					<td class="tdtable1">
						<input type="checkbox" class="img" name="view" id="${dto.file_num}" value="${dto.work_seq }">
						<div class="images1">
							<div>
							<img src="${pageContext.request.contextPath}/upload/${dto.file_name}">
							</div>
						</div>
					</td>
				</tr>
		</c:if>
		</c:forEach>
</table>
			
</body>
</html>