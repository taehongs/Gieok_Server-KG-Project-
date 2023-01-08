<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
 <!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />

<link rel="stylesheet" href="../resources/CSS/member/my_message.css">
<!-- contents: board list box -->
    <div class="content">
    		
			<form class="message_form">
	            
	        </form>

        <div class="my_review_box">
            <h1 class="review_title">내 메세지</h1>

            <div class="review_col">
                <span>보낸 사람</span>
                <span>제목</span>
                <span>받은 날짜</span>
                <span>선택</span>
            </div>
            
			<span id="message_delete_btn">선택삭제</span>

            <div class="line_s"></div>
			
			<input type="hidden" value="${!empty myMessage }" id="isNotEmpty">
            <ul class="review_list">
            	<c:if test="${empty myMessage }">
            		<span>받은 메세지가 없습니다 :(</span>
            	</c:if>
            	<c:if test="${!empty myMessage }">
	            	<c:forEach var="msg" items="${myMessage }">
		                <li class="message_detail_btn">
	                        <span>${msg.message_sender }</span>
	                        <c:if test="${msg.message_read == 'N' }">
	                        	<span class="unread_msg">${msg.message_title}&nbsp;</span>
	                        </c:if>
	                        <c:if test="${msg.message_read == 'Y' }">
		                        <span>${msg.message_title}</span>
	                        </c:if>
	                        <input type="hidden" value="${msg.message_content }">
	                        <span>${msg.message_date }</span>
	                        <input type="hidden" value="${msg.message_no }">
	                        <input type="checkbox" class="message_check" value="${msg.message_no }">
		                </li>
	            	</c:forEach>
            	</c:if>
            </ul>

            <div class="line_s"></div>
            
            <form method="GET" action="https://gieok.icu/member/my_message" class="message_search_form" onsubmit="return searchValidate();">
            	<select name="category" id="message_category">
            		<option value="all" <c:if test="${category == 'all' }"> selected </c:if>>전체</option>
            		<option value="message_sender" <c:if test="${category == 'message_sender' }"> selected </c:if>>보낸 사람</option>
            		<option value="message_title" <c:if test="${category == 'message_title' }"> selected </c:if>>제목</option>
            		<option value="message_content" <c:if test="${category == 'message_content' }"> selected </c:if>>내용</option>
            	</select>
            	<c:if test="${keyword == '' }">
	            	<input type="text" name="keyword" placeholder="검색어를 입력하세요" id="message_keyword">        	
            	</c:if>
            	<c:if test="${!(keyword == '')}">
	            	<input type="text" name="keyword" value="${keyword }" id="message_keyword">        	
            	</c:if>
            	
            	<input type="submit" value="검색" id="message_search_btn">
            </form>

        </div>

    </div>
    
<script src="../resources/JS/member/my_message.js"></script>
<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" />