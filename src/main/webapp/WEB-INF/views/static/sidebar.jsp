<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <link rel="stylesheet" href="/resources/CSS/static/header.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!--// contents section start -->
    <section class="controller">
        <!-- admin menu -->
        <div class="admin_menu">
            <span>관리자</span>
            <div class="line_s"></div>
            <!-- member -->
            <span>회원 관리</span>
            <ul>
            	<c:if test="${sessionScope.grade == 's' }">
	                <li>
	                    <input type="radio" id="admin_list" name="admin_menu">
	                    <label for="admin_list" id="admin_list_btn">관리자 목록</label>
	                </li>
				</c:if>
                <li>
                    <input type="radio" id="member_list" name="admin_menu">
                    <label for="member_list" id="member_list_btn">회원 목록</label>
                </li>
	             <li>
	                 <input type="radio" id="review_report_list" name="admin_menu">
	                 <label for="review_report_list" id="review_report_list_btn">리뷰 신고 목록</label>
	             </li>
	             <li>
	                 <input type="radio" id="board_report_list" name="admin_menu">
	                 <label for="board_report_list" id="board_report_list_btn">게시물 신고 목록</label>
	             </li>
            </ul>
            <!-- local -->
            <span>명소 게시판</span>
            <ul>
                <li>
                    <input type="radio" id="attr_list" name="admin_menu">
                    <label for="attr_list" id="attr_list_btn">명소 목록</label>
                </li>
            </ul>
            <!-- news, event board -->
            <span>News & Event</span>
            <ul>
                <li>
                    <input type="radio" id="board_list" name="admin_menu">
                    <label for="board_list" id="board_list_btn">공지 목록</label>
                </li>
            </ul>
            <!-- accompany board -->
            <span>동행 게시판</span>
            <ul>
                <li>
                    <input type="radio" id="with_list" name="admin_menu">
                    <label for="with_list" id="with_list_btn">동행 목록</label>
                </li>
            </ul>
            <!-- photo event board -->
            <span>포토 이벤트 게시판</span>
            <ul>
                <li>
                    <input type="radio" id="photo_event_list" name="admin_menu">
                    <label for="photo_event_list" id="photo_event_list_btn">포토 이벤트 목록</label>
                </li>
            </ul>
        </div>
    </section>
    <script src="../resources/JS/static/sidebar.js"></script>