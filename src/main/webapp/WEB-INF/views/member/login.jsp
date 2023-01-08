<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />
<link rel="stylesheet" href="../resources/CSS/member/login.css">

<!-- contents: board list box -->
    <div class="content">
        <!-- login form -->
        <form class="member_login_box" name="login_form" method="POST" action="https://gieok.icu/login">
        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <!-- logo -->
            <h1 class="member_login_logo">ㄱㅎㅈ</h1>
            <!-- id -->
            <div class="member_login_id">
                <input type="text" name="user_id" required="required" maxlength="12" id="user_id">
                <span>아이디</span>
                <i></i>
            </div>
            <!-- password -->
            <div class="member_login_pw">
                <input type="password" name="user_pw" required="required" maxlength="18">
                <span>비밀번호</span>
                <i></i>
            </div>

            <div class="member_login_btn">
                <input type="submit" value="로그인">
                <input type="button" value="취소" onclick="window.history.back()")>
            </div>
            <div class="member_login_etc">
                <a href="/member/find_id_pw">아이디, 비밀번호 찾기</a>
                <a href="/member/join">회원가입</a>
            </div>
        </form>
    </div>
    <!-- container section end //-->
    
	<script src="../resources/JS/member/login.js"></script>

<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" />