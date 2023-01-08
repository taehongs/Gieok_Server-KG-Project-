<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../static/header.jsp" />
<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
	<jsp:include page="../static/sidebar.jsp" />
</c:if>
<c:if test="${(sessionScope.grade == 'm' || empty sessionScope.grade)  }">
	<!-- 회원용 리모컨 -->
	<jsp:include page="../static/board_sidebar.jsp"/>
</c:if>

    <link rel="stylesheet" href="../resources/CSS/static/header.css">
    <link rel="stylesheet" href="../resources/CSS/admin/board_write.css">
	<div class="content">
        <!-- contents: board list box -->
        <div id="board_notice" class="board_notice">
            <div class="board_container">
                <h1>공지사항</h1>
            </div>
            <div class="board_title">
                <select name="board_type" id="board_type">
                    <c:if test="${blist.board_type==1}">
                    <option value="notice" selected>공지사항</option>
                    </c:if>
                    <c:if test="${blist.board_type==2}">
                    <option value="event" selected>이벤트</option>
                    </c:if>
                </select>
                <input type="text" name="board_title" id="board_title" value="${blist.board_title}" readonly>
                <input type="text" name="board_date" id="board_date" value="${fn:substring(blist.board_startDay,0,10)} ~ ${fn:substring(blist.board_endDay,0,10)}" readonly>
            </div>
            <div class="board_middle">
                <div contentEditable="false" name="board_content" class="board_content" id="board_content" >${blist.board_content}
                <c:if test="${blist.board_type==2}">
                	<br><img alt="" src="../resources/upload/event/${fn:substring(blist.board_regDate,0,10)}/${blist.board_img1}" width="100%">
               		<br><img alt="" src="../resources/upload/event/${fn:substring(blist.board_regDate,0,10)}/${blist.board_img2}" width="100%">
                </c:if>
                </div>
            </div>
            <div class="board_button">
            	<input type="hidden" value="${sessionScope.grade }" id="grade">
            	<input type="hidden" value="${page }" id="page">
            	<c:if test="${sessionScope.grade =='a' || sessionScope.grade =='s'}">
            	<input type="hidden" value="${blist.board_no }" id="board_no">
                <input type="button" id="edit_btn" value="수정" style="margin-right:10px" >
                </c:if>
                <input type="submit" id="list_btn" value="목록" >
                <input type="button" value="취소" onclick="history.back();">
            </div>
            
        </div>
    </div>
    <!-- container section end //-->
<script src="../resources/JS/member/board_cont.js"></script>
<jsp:include page="../static/footer.jsp"/>   