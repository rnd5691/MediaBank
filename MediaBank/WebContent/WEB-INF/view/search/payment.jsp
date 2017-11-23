<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 아임포트 라이브러리 추가 -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="http://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>결제 진행</title>
<script type="text/javascript">
	$(function(){
		var IMP = window.IMP; //생략가능
		IMP.init('imp95781276');
		//init는 서블릿 로딩 시 미리 가지고 있어야 하는 것
		IMP.request_pay({
		    pg : 'uplus', // version 1.1.0부터 지원.
		    pay_method : 'card',
		    merchant_uid : 'merchant_' + new Date().getTime(),
		    name : '${requestScope.work.work}',
		    amount : ${requestScope.work.price},
		    buyer_email : '${sessionScope.member.email}',//구매자 정보로 가져오기
		    buyer_name : '${requestScope.name}',//구매자 정보로 가져오기
		    buyer_tel : '010-1234-1234',//구매자 정보로 가져오기
		    buyer_addr : '비공개',//구매자 접
		    buyer_postcode : '비공개',
		    m_redirect_url : 'https://www.yourdomain.com/payments/complete'
		}, function(rsp) {
		    if ( rsp.success ) {
		        var msg = '결제가 완료되었습니다.';
		        msg += '고유ID : ' + rsp.imp_uid;
		        msg += '상점 거래ID : ' + rsp.merchant_uid;
		        msg += '결제 금액 : ' + rsp.paid_amount;
		        msg += '카드 승인번호 : ' + rsp.apply_num;
		    } else {
		        var msg = '결제에 실패하였습니다.';
		        msg += '에러내용 : ' + rsp.error_msg;
		        msg += '이름 : '+'${requestScope.name}';
		    }
		    alert(msg);
		    location.href = "../mypage/buyInsert.mypage?work_seq="+${requestScope.work.work_seq};
		});
	});
</script>
</head>
<body oncontextmenu="return false" ondragstart="return false" onselectstart="return false">

</body>
</html>