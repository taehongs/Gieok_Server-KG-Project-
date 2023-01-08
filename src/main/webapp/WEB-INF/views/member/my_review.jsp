<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
 <!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />
<link rel="stylesheet" href="../resources/CSS/member/my_review.css">

<!-- contents: board list box -->
    <div class="content">
    		
			<form class="attr_review_form" name="attr_review_form" enctype="multipart/form-data">
	            
	        </form>

        <div class="my_review_box">
            <h1 class="review_title">내가 쓴 리뷰</h1>

            <div class="review_col">
                <span>번호</span>
                <span>명소</span>
                <span>내용</span>
                <span>별점</span>
                <span>작성일</span>
            </div>

            <div class="line_s"></div>
			
			<input type="hidden" value="${!empty review_list }" id="isNotEmpty">
			<input type="hidden" value="${page }" id="page">
            <ul class="review_list">
            	<c:if test="${empty review_list }">
            		<span>작성한 리뷰가 없습니다 :(</span>
            	</c:if>
            	<c:if test="${!empty review_list }">
	            	<c:forEach var="review" items="${review_list }">
		                <li class="review_detail_btn">
	                        <input type="hidden" value="${review.rev_code }" class="rev_code_list">
	                        <span>${review.rev_code }</span>
	                        <span>${review.attr_name }</span>
	                        <span>${review.rev_content}</span>
	                        <span>${review.rev_rate}</span>
	                        <span>${review.rev_date }</span>
		                </li>
	            	</c:forEach>
            	</c:if>
            </ul>

            <div class="line_s"></div>

            <div class="review_page">
	            <c:if test="${page==1 }">
	               <span class="prev_next">시작</span>
	            </c:if>
	            <c:if test="${page>1 }">
	               <a
	                  href="/member/my_review?page=1">
	                 	시작
	               </a>
	            </c:if>
	            <c:if test="${page<11 }">
	               <span class="prev_next">이전</span>
	            </c:if>
	            <c:if test="${page>=11 }">
	               <a
	                  href="/member/my_review?page=${startPage-10 }">
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
		                     href="/member/my_review?page=${p }">${p }</a></li>
		               </c:if>
		            </c:forEach>
                </ul>

                <c:if test="${(startPage+10)>totalPage }">
	                <span class="prev_next">다음</span>
	           	</c:if>
	            <c:if test="${(startPage+10)<=totalPage }">
	               <a
	                  href="/member/my_review?page=${startPage+10 }">
	                  	다음
	               </a>
	            </c:if>
	            <c:if test="${page==totalPage || totalPage==0 }">
	               <span class="prev_next">마지막</span>
	            </c:if>
	            <c:if test="${page<totalPage }">
	               <a
	                  href="/member/my_review?page=${totalPage }">
	                  	마지막
	               </a>
	            </c:if>
            </div>

        </div>

    </div>
    
<script src="../resources/JS/member/my_review.js"></script>
<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" />