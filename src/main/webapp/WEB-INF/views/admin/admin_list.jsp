<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!-- ***** header ***** -->
        <jsp:include page="../static/header.jsp" />
        <c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
			<jsp:include page="../static/sidebar.jsp" />
		</c:if>

        <link type="text/css" rel="stylesheet" href="../resources/CSS/admin/admin_list.css">

        <!-- contents: board list box -->
        <form class="content" action="https://gieok.icu/admin/admin_list" name="admin_form">
            <div class="admin_list_box">
                <h1 class="admin_title">관리자 목록</h1>

                <div class="line_big"></div>
                <div class="admin_column">
                    <span>번호</span>
                    <span>아이디</span>
                    <span>이름</span>
                    <span>연락처</span>
                    <span>이메일</span>
                    <span>가입 일자</span>
                </div>
                <div class="line_small"></div>
                <ul class="admin_list">
                    <c:if test="${!empty admin_list}">
                        <c:forEach var="item" items="${admin_list}">
                            <li>
                                <span>${item.user_code}</span>
                                <span>${item.user_id}</span>
                                <span>${item.user_name}</span>
                                <span>${item.user_ph1}-${item.user_ph2}-${item.user_ph3}</span>
                                <span>${item.user_email}@${item.user_domain}</span>
                                <span>${item.user_regDate}</span>
                            </li>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty admin_list}">
                        <li style="text-align: center; font-size: 14px;">관리자가 없습니다</li>
                    </c:if>
                </ul>

                <div class="line_big"></div>

                <!-- page -->
                <div class="admin_page">
                    <c:if test="${page == 1}">
                        <span>처음</span>
                    </c:if>
                    <c:if test="${page > 1}">
                        <span><a href="/admin/admin_list?page=1&category=${category}&keyword=${keyword}">처음</a></span>
                    </c:if>
                    <c:if test="${page < 11}">
                        <span>이전</span>
                    </c:if>
                    <c:if test="${page >= 11}">
                        <span><a href="/admin/admin_list?page=${startPage - 10}&category=${category}&keyword=${keyword}">이전</a></span>
                    </c:if>

                    <ul class="page">
                        <c:forEach var="p" begin="${startPage}" end="${endPage}" step="1">
                            <c:if test="${p == page}">
                                <li class="select_page">${p}</li>
                            </c:if>

                            <c:if test="${p != page}">
                                <li><a href="/admin/admin_list?page=${p}&category=${category}&keyword=${keyword}">${p}</a></li>
                            </c:if>
                        </c:forEach>
                    </ul>

                    <c:if test="${startPage + 10 > totalPage}">
                        <span>다음</span>
                    </c:if>
                    <c:if test="${startPage + 10 <= totalPage}">
                        <span><a href="/admin/admin_list?page=${startPage + 10}&category=${category}&keyword=${keyword}">다음</a></span>
                    </c:if>
                    <c:if test="${page >= totalPage}">
                        <span>마지막</span>
                    </c:if>
                    <c:if test="${page < totalPage}">
                        <span><a href="/admin/admin_list?page=${totalPage}&category=${category}&keyword=${keyword}">마지막</a></span>
                    </c:if>
                </div>
                <!-- search -->
                <div class="admin_search">
                    <select name="category" id="admin_search">
                    	<option value="user_name" <c:if test="${category == 'user_name'}">selected</c:if>>이름</option>
                        <option value="user_id" <c:if test="${category == 'user_id'}">selected</c:if>>아이디</option>
                    </select>
                    <input type="text" name="keyword" placeholder="검색어를 입력하세요" value="${keyword}">
                    <input type="submit" value="검색" id="search_btn">
                </div>
                
                <span id="search_admin_user">관리자 권한 부여</span>
            </div>
        </form>
        <!-- container section end //-->
		
		<div class="user_detail_box">
            <h1>관리자 메뉴</h1>
            <span class="user_id"></span>
            <span class="user_name"></span>

            <div class="search_box">
                <input type="text" name="search_user" id="search_user" placeholder="아이디를 입력하세요">
                <input type="submit" value="검색" class="search_button">
            </div>

            <form class="admin_state_button">
                <input type="button" value="관리자 임명" class="add_admin_button">
                <input type="button" value="관리자 해임" class="delete_admin_button">
                <input type="button" value="취소">
            </form>
        </div>

        <script type="text/javascript" src="../resources/JS/admin/admin_list.js"></script>

        <!-- ***** footer ***** -->
        <jsp:include page="../static/footer.jsp" />