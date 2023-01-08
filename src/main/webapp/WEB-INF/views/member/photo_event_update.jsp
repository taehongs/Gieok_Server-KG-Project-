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
            <input type=hidden value="${board.board_no}" name="board_no">
                <!-- board title -->
                <div class="board_title">
                    <h1>포토 이벤트</h1>
                </div>

                <!-- post information -->
                <div class="board_line"></div>

                <div class="board_upload_content">
                    <!-- local select -->
                    <select id="province" name="board_location">
                        <option value="Tseoul" <c:if test="${board.board_location == 'Tseoul'}">selected</c:if>>서울특별시</option>
                        <option value="Tgyeonggi" <c:if test="${board.board_location == 'Tgyeonggi'}">selected</c:if>>경기도</option>
                        <option value="Tincheon" <c:if test="${board.board_location == 'Tincheon'}">selected</c:if>>인천광역시</option>
                        <option value="Tgangwon" <c:if test="${board.board_location == 'Tgangwon'}">selected</c:if>>강원도</option>
                        <option value="Tchungbuk" <c:if test="${board.board_location == 'Tchungbuk'}">selected</c:if>>충청북도</option>
                        <option value="Tchungnam" <c:if test="${board.board_location == 'Tchungnam'}">selected</c:if>>충청남도</option>
                        <option value="Tsejong" <c:if test="${board.board_location == 'Tsejong'}">selected</c:if>>세종특별자치시</option>
                        <option value="Tdaejeon" <c:if test="${board.board_location == 'Tdaejeon'}">selected</c:if>>대전</option>
                        <option value="Tgyeongbuk" <c:if test="${board.board_location == 'Tgyeongbuk'}">selected</c:if>>경상북도</option>
                        <option value="Tdaegu" <c:if test="${board.board_location == 'Tdaegu'}">selected</c:if>>대구</option>
                        <option value="Tgyeongnam" <c:if test="${board.board_location == 'Tgyeongnam'}">selected</c:if>>경상남도</option>
                        <option value="Tulsan" <c:if test="${board.board_location == 'Tulsan'}">selected</c:if>>울산광역시</option>
                        <option value="Tbusan" <c:if test="${board.board_location == 'Tbusan'}">selected</c:if>>부산광역시</option>
                        <option value="Tjeonbuk" <c:if test="${board.board_location == 'Tjeonbuk'}">selected</c:if>>전라북도</option>
                        <option value="Tjeonnam" <c:if test="${board.board_location == 'Tjeonnam'}">selected</c:if>>전라남도</option>
                        <option value="Tgwangju" <c:if test="${board.board_location == 'Tgwangju'}">selected</c:if>>광주광역시</option>
                        <option value="Tjeju" <c:if test="${board.board_location == 'Tjeju'}">selected</c:if>>제주특별자치도</option>
                    </select>
                    <!-- post tltle -->
                    <input type="text" class="post_title" name="board_title" value="${board.board_title}">
                    <!-- file upload -->
                    <input type="file" name="img" id="post_photo" onchange="set_preview(event);">
                    <!-- file upload button -->
                    <span>가로 500px, 세로 330px, 크기 5M이하</span>
                    <label for="post_photo" class="post_photo">사진 등록</label>
                    <!-- file preview -->
                    <div class="post_photo_preview" style="background:url(/resources/upload/event/${board.board_img}) no-repeat; background-size: cover;"></div>
                    <!-- photo title -->
                    <input type="text" class="post_photo_title" value="${board.board_img}" readonly>
                    <!-- post contents -->
                    <textarea name="board_content" id="post_contents">${board.board_content}</textarea>
                    <!-- photo_event_form -->
                    <!-- board_img, board_location, board_title, board_content -->
                </div>
                <!-- line -->
                <div class="board_line"></div>

                <div class="board_post_button">
                    <input type="button" value="저장">
                    <input type="button" value="취소">
                </div>
            </form>
        </div>
        <!-- container section end //-->
        <script src="../resources/JS/member/photo_event_update.js"></script>

        <!-- ***** footer ***** -->
        <jsp:include page="../static/footer.jsp" />