<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!-- ***** header ***** -->
        <jsp:include page="../static/header.jsp" />
        
        <c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
			<jsp:include page="../static/sidebar.jsp" />
		</c:if>

        <link type="text/css" rel="stylesheet" href="../resources/CSS/admin/user_list.css">

        <!-- contents: board list box -->
        <form class="content" action="https://gieok.icu/admin/user_list" name="user_form">
        	<input type="hidden" value="${page}" id="page">
            <div class="user_list_box">
                <h1 class="user_title">회원 목록</h1>

                <div class="line_big"></div>
                <div class="user_column">
                    <span>번호</span>
                    <span>아이디</span>
                    <span>이름</span>
                    <span>연락처</span>
                    <span>이메일</span>
                    <span>가입 일자</span>
                </div>
                <div class="line_small"></div>
                <ul class="user_list">
                    <c:if test="${!empty user_list}">
                        <c:forEach var="item" items="${user_list}">
                            <li>
                            	<input type="hidden" value="${item.user_code}" class="user_codes">
                            	<input type="hidden" value="${item.user_id}" class="user_ids">
                                <span>${item.user_code}</span>
                                <span>${item.user_id}</span>
                                <span>${item.user_name}</span>
                                <span>${item.user_ph1}-${item.user_ph2}-${item.user_ph3}</span>
                                <span>${item.user_email}@${item.user_domain}</span>
                                <span>${item.user_regDate}</span>
                            </li>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty user_list}">
                        <li style="text-align: center; font-size: 14px">회원 목록이 없습니다</li>
                    </c:if>
                </ul>

                <div class="line_big"></div>

                <!-- page -->
                <div class="user_page">
                    <c:if test="${page == 1}">
                        <span>처음</span>
                    </c:if>
                    <c:if test="${page > 1}">
                        <span><a href="/admin/user_list?page=1&category=${category}&keyword=${keyword}">처음</a></span>
                    </c:if>
                    <c:if test="${page < 11}">
                        <span>이전</span>
                    </c:if>
                    <c:if test="${page >= 11}">
                        <span><a href="/admin/user_list?page=${startPage - 10}&category=${category}&keyword=${keyword}">이전</a></span>
                    </c:if>

                    <ul class="page">
                        <c:forEach var="p" begin="${startPage}" end="${endPage}" step="1">
                            <c:if test="${p == page}">
                                <li class="select_page">${p}</li>
                            </c:if>

                            <c:if test="${p != page}">
                                <li><a href="/admin/user_list?page=${p}&category=${category}&keyword=${keyword}">${p}</a></li>
                            </c:if>
                        </c:forEach>
                    </ul>

                    <c:if test="${startPage + 10 > totalPage}">
                        <span>다음</span>
                    </c:if>
                    <c:if test="${startPage + 10 <= totalPage}">
                        <span><a href="/admin/user_list?page=${startPage + 10}&category=${category}&keyword=${keyword}">다음</a></span>
                    </c:if>
                    <c:if test="${page >= totalPage}">
                        <span>마지막</span>
                    </c:if>
                    <c:if test="${page < totalPage}">
                        <span><a href="/admin/user_list?page=${totalPage}&category=${category}&keyword=${keyword}">마지막</a></span>
                    </c:if>
                </div>
                <!-- search -->
                <div class="user_search">
                    <select name="category" id="user_search">
                        <option value="user_name" <c:if test="${category == 'user_name'}">selected</c:if>>이름</option>
                        <option value="user_id" <c:if test="${category == 'user_id'}">selected</c:if>>아이디</option>
                    </select>
                    <input type="text" name="keyword" placeholder="검색어를 입력하세요" value="${keyword}">
                    <input type="submit" value="검색" id="search_btn">
                </div>
            </div>
        </form>
        <!-- container section end //-->
		
	
		<div class="user_detail_box">
			<h1>회원 정보</h1>
			<div class="user_profile"></div> 
			<span class="user_id"></span>
		
			<div class="user_phone">
				<span></span>
			</div>
		
			<div class="user_email">
				<span></span>
			</div>
			<form method="POST" class="user_state_button">
				<input type="button" value="탈퇴 처리" class="user_delete_button"> 
				<input type="button" value="취소">
			</form>
		</div>



        <script type="text/javascript" src="../resources/JS/admin/user_list.js"></script>

        <!-- ***** footer ***** -->
        <jsp:include page="../static/footer.jsp" />