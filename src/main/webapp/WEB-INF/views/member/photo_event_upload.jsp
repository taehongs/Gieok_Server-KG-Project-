<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!-- ***** header ***** -->
        <jsp:include page="../static/header.jsp" />
   		<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
			<jsp:include page="../static/sidebar.jsp" />
		</c:if>
		<c:if test="${(sessionScope.grade == 'm' || empty sessionScope.grade)  }">
			<jsp:include page="../static/board_sidebar.jsp"/>
			<!-- 회원용 리모컨 -->
		</c:if>
        
        <link rel="stylesheet" href="../resources/CSS/member/photo_event_upload.css">

        <!-- contents: board list box -->
        <div class="content">
          
            <!-- contents -->
            <form id="board_form" method="POST" name="photo_event_form" enctype="multipart/form-data">
                <!-- board title -->
                <div class="board_title">
                    <h1>포토 이벤트</h1>
                </div>

                <!-- post information -->
                <div class="board_line"></div>

                <div class="board_upload_content">
                    <!-- local select -->
                    <select id="province" name="board_location">
                        <option value="" disabled selected>도 선택</option>
                        <option value="Tseoul">서울특별시</option>
                        <option value="Tgyeonggi">경기도</option>
                        <option value="Tincheon">인천광역시</option>
                        <option value="Tgangwon">강원도</option>
                        <option value="Tchungbuk">충청북도</option>
                        <option value="Tchungnam">충청남도</option>
                        <option value="Tsejong">세종특별자치시</option>
                        <option value="Tdaejeon">대전광역시</option>
                        <option value="Tgyeongbuk">경상북도</option>
                        <option value="Tdaegu">대구광역시</option>
                        <option value="Tgyeongnam">경상남도</option>
                        <option value="Tulsan">울산광역시</option>
                        <option value="Tbusan">부산광역시</option>
                        <option value="Tjeonbuk">전라북도</option>
                        <option value="Tjeonnam">전라남도</option>
                        <option value="Tgwangju">광주광역시</option>
                        <option value="Tjeju">제주특별자치도</option>
                    </select>
                     <!-- post tltle -->
                    <input type="text" class="post_title" name="board_title" placeholder="제목을 입력하세요 :)">
                    <!-- file upload -->
                    <input type="file" name="img" id="post_photo" onchange="set_preview(event);">
                    <!-- file upload button -->
                    <span>가로 500px, 세로 330px, 크기 5M이하</span>
                    <label for="post_photo" class="post_photo">사진 등록</label>
                    <!-- file preview -->
                    <div class="post_photo_preview"></div>
                    <!-- photo title -->
                    <input type="text" class="post_photo_title" readonly>
                    <!-- post contents -->
                    <textarea name="board_content" id="post_contents" placeholder="내용을 입력하세요 :)"></textarea>
                    <!-- photo_event_form -->
                    <!-- board_img, board_location, board_title, board_content -->
                </div>
                <!-- line -->
                <div class="board_line"></div>

                <div class="board_post_button">
                    <input type="button" value="등록">
                    <input type="button" value="취소">
                </div>
            </form>
        </div>
        <!-- container section end //-->
        <script src="../resources/JS/member/photo_event_upload.js"></script>

        <!-- ***** footer ***** -->
        <jsp:include page="../static/footer.jsp" />