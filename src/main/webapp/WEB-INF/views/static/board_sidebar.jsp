<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <link rel="stylesheet" href="/resources/CSS/static/header.css">
    <!--// contents section start -->
    <section class="controller">
        <!-- admin menu -->
        <div class="admin_menu">
            <span>게시판</span>
            <div class="line_s"></div>
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
    
    <script src="../resources/JS/static/board_sidebar.js"></script>