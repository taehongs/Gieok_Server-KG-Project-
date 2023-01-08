<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />
<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
	<jsp:include page="../static/sidebar.jsp" />
</c:if>
<c:if test="${sessionScope.grade == 'm'  }">
	<!-- 회원용 리모컨 -->
</c:if>

<link type="text/css" rel="stylesheet" href="../resources/CSS/admin/report_list.css">

		 <!-- contents: board list box -->
    <div class="content">
        <div class="report_list_box">
            <h1 class="report_title">게시물 신고 목록</h1>

            <div class="report_col">
                <span>번호</span>
                <span>신고 계정</span>
                <span>신고 유형</span>
                <span>게시글/리뷰</span>
                <span>피신고 계정</span>
            </div>

            <div class="line_s2"></div>
            <input type="hidden" value="${page }" id="page">
            <ul class="report_list">
            	<c:if test="${empty boardReport }">
            		<span>신고된 리뷰가 없습니다! :)</span>
            	</c:if>
            	<c:if test="${!empty boardReport }">
	            	<c:forEach var="review" items="${boardReport }">
		                <li class="board_report_list_btn">
		                	<input type="hidden" value="${review.bad_board.board_no }" class="bad_board_no">
		                	<input type="hidden" value="${review.bad_board.board_type }" class="bad_board_type">
		                	<input type="hidden" value="${review.bad_board.board_title }" class="bad_board_title">
	                        <span>${review.report_no }</span>
	                        <span>${review.user_id }</span>
	                        <span>${review.report_type }</span>
	                        <c:if test="${review.bad_board.board_type == '3' }">
		                        <span>동행) ${review.bad_board.board_title }</span>
	                        </c:if>
	                        <c:if test="${review.bad_board.board_type == '4' }">
		                        <span>포토이벤트) ${review.bad_board.board_title }</span>
	                        </c:if>
	                        <span>${review.bad_board.board_writer }</span>
		                </li>
	            	</c:forEach>
            	</c:if> 
            </ul>

            <div class="line_s2"></div>

            <div class="report_page">
                 <c:if test="${page==1 }">
	               <span class="prev_next">시작</span>
	            </c:if>
	            <c:if test="${page>1 }">
	               <a
	                  href="/admin/board_report_list?page=1">
	                 	시작
	               </a>
	            </c:if>
	            <c:if test="${page<11 }">
	               <span class="prev_next">이전</span>
	            </c:if>
	            <c:if test="${page>=11 }">
	               <a
	                  href="/admin/board_report_list?page=${startPage-10 }">
	                  	이전
	               </a>
	            </c:if>

                <ul class="page">
                    <c:forEach var="p" begin="${startPage }" end="${endPage }" step="1">
               			<c:if test="${p==page }">
		                  <li class="select_page">${p }</li>
		               </c:if>
		               <c:if test="${p!=page }">
		                  <li><a
		                     href="/admin/board_report_list?page=${p }">${p }</a></li>
		               </c:if>
		            </c:forEach>
                </ul>

                <c:if test="${(startPage+10)>totalPage }">
	                <span class="prev_next">다음</span>
	           	</c:if>
	            <c:if test="${(startPage+10)<=totalPage }">
	               <a
	                  href="/admin/board_report_list?page=${startPage+10 }">
	                  	다음
	               </a>
	            </c:if>
	            <c:if test="${page==totalPage || totalPage==0 }">
	               <span class="prev_next">마지막</span>
	            </c:if>
	            <c:if test="${page<totalPage }">
	               <a
	                  href="/admin/board_report_list?page=${totalPage }">
	                  	마지막
	               </a>
	            </c:if>
            </div>

        </div>

    </div>
    <!-- container section end //-->

<script src="../resources/JS/admin/board_report_list.js"></script>
<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" />