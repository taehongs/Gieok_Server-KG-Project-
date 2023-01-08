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
        <link rel="stylesheet" href="../resources/CSS/member/photo_event_detail.css">

        <!-- contents: board list box -->
        <div class="content">

            <!-- contents -->
            <form id="board_form" name="photo_event_form">
                <!-- board title -->
                <div class="board_title">
                    <h1>포토 이벤트</h1>
                </div>

                <!-- post information -->
                <div class="board_post_info">
                    <div class="board_line"></div>
                    <span name="board_title">${board.board_title}</span>
                    <span name="board_regDate">${board.board_regDate}</span>
                    <span name="board_hit">${board.board_hit}</span>
                    <span name="board_like">${board.board_like}</span>
                    <span name="board_no" id="board_no">${board.board_no}</span>
                    <div class="board_line"></div>
                </div>

                <div class="board_post_detail">
                    <div class="post_photo" style="background: url(/resources/upload/event/${board.board_img}) no-repeat; background-size: cover;"></div>
                    <div class="post_location">
                        <span>${board.board_location}</span>
                    </div>
                    <div class="post_contents">
                        <textarea readonly>${board.board_content}</textarea>
                    </div>
                </div>
                <!-- line -->
                <div class="board_line"></div>

                <div class="board_post_button">
                   <c:if test="${sessionScope.code == board.user_code}">
                      <input type="button" id="update_button" value="수정">
                   </c:if>
                    <input type="checkbox" name="like_checkbox" id="like_checkbox" <c:if test="${boardLikeReport.board_like == 'Y'}">checked</c:if>>
                    <label for="like_checkbox">
                        <svg width="17" height="15" viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <mask id="path-1-inside-1_731_234" fill="white">
                                <path fill-rule="evenodd" clip-rule="evenodd"
                                    d="M7.87452 14.2063C9.87727 14.1116 12.2273 12.4233 13.768 9.75479C15.7917 6.24966 15.6526 2.38072 13.4573 1.11327C11.8513 0.186086 9.58993 0.907934 7.69922 2.73453C5.80852 0.907937 3.5471 0.186092 1.94117 1.11328C-0.254108 2.38072 -0.39321 6.24966 1.63048 9.7548C3.17372 12.4278 5.52905 14.1173 7.53399 14.2068C7.58636 14.2092 7.63903 14.2104 7.69197 14.2105C7.75319 14.2106 7.81406 14.2092 7.87452 14.2063Z" />
                            </mask>
                            <path fill-rule="evenodd" clip-rule="evenodd"
                                d="M7.87452 14.2063C9.87727 14.1116 12.2273 12.4233 13.768 9.75479C15.7917 6.24966 15.6526 2.38072 13.4573 1.11327C11.8513 0.186086 9.58993 0.907934 7.69922 2.73453C5.80852 0.907937 3.5471 0.186092 1.94117 1.11328C-0.254108 2.38072 -0.39321 6.24966 1.63048 9.7548C3.17372 12.4278 5.52905 14.1173 7.53399 14.2068C7.58636 14.2092 7.63903 14.2104 7.69197 14.2105C7.75319 14.2106 7.81406 14.2092 7.87452 14.2063Z" />
                            <path
                                d="M13.768 9.75479L14.634 10.2548H14.634L13.768 9.75479ZM7.87452 14.2063L7.8273 13.2074L7.82581 13.2075L7.87452 14.2063ZM13.4573 1.11327L13.9573 0.247246V0.247246L13.4573 1.11327ZM7.69922 2.73453L7.00441 3.45372L7.69922 4.12497L8.39403 3.45372L7.69922 2.73453ZM1.94117 1.11328L1.44117 0.247251V0.247251L1.94117 1.11328ZM1.63048 9.7548L2.49651 9.2548L1.63048 9.7548ZM7.53399 14.2068L7.57983 13.2078L7.57858 13.2078L7.53399 14.2068ZM7.69197 14.2105L7.69429 13.2105L7.69363 13.2105L7.69197 14.2105ZM12.9019 9.25479C11.455 11.761 9.36112 13.1349 7.8273 13.2074L7.92174 15.2052C10.3934 15.0883 12.9996 13.0856 14.634 10.2548L12.9019 9.25479ZM12.9573 1.9793C13.6495 2.37892 14.1463 3.26614 14.1954 4.63083C14.2439 5.97921 13.8349 7.6389 12.9019 9.25479L14.634 10.2548C15.7248 8.36555 16.2581 6.3382 16.1941 4.55897C16.1307 2.79604 15.4604 1.11506 13.9573 0.247246L12.9573 1.9793ZM8.39403 3.45372C10.1783 1.72991 11.9459 1.39535 12.9573 1.9793L13.9573 0.247246C11.7568 -1.02318 9.0015 0.085959 7.00441 2.01533L8.39403 3.45372ZM2.44117 1.9793C3.45259 1.39536 5.2201 1.72991 7.00441 3.45372L8.39403 2.01533C6.39694 0.0859621 3.64162 -1.02318 1.44117 0.247251L2.44117 1.9793ZM2.49651 9.2548C1.56357 7.63891 1.15459 5.97922 1.20307 4.63083C1.25213 3.26615 1.749 2.37893 2.44117 1.9793L1.44117 0.247251C-0.0619341 1.11507 -0.732259 2.79605 -0.795642 4.55897C-0.859611 6.3382 -0.326302 8.36555 0.764455 10.2548L2.49651 9.2548ZM7.57858 13.2078C6.04439 13.1393 3.9461 11.7656 2.49651 9.2548L0.764455 10.2548C2.40134 13.09 5.01371 15.0953 7.4894 15.2058L7.57858 13.2078ZM7.69363 13.2105C7.65519 13.2104 7.61725 13.2095 7.57983 13.2078L7.48815 15.2057C7.55547 15.2088 7.62287 15.2104 7.69032 15.2105L7.69363 13.2105ZM7.82581 13.2075C7.78268 13.2096 7.73884 13.2106 7.69429 13.2105L7.68965 15.2105C7.76755 15.2107 7.84543 15.2089 7.92323 15.2051L7.82581 13.2075Z"
                                mask="url(#path-1.5-inside-1_731_234)" />
                        </svg>
                    </label>
                    <input type="button" value="뒤로" class="back_button">
                   <c:if test="${sessionScope.code == board.user_code || sessionScope.grade == 's' || sessionScope.grade == 'a'}">
                       <span id="delete_button">게시물 삭제</span>
                    </c:if>
                    <input type="checkbox" id="report_checkbox" name="report_checkbox">
                    <c:if test="${sessionScope.code != board.user_code}">
                       <label for="report_checkbox" class="report_button">게시물 신고</label>
                    </c:if>
                </div>
            </form>
        </div>
        <!-- container section end //-->
        <script src="../resources/JS/member/photo_event_detail.js"></script>

        <!-- ***** footer ***** -->
        <jsp:include page="../static/footer.jsp" />