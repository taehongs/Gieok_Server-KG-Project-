<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <!-- ***** header ***** -->
    <jsp:include page="../static/header.jsp" />
    <link rel="stylesheet" href="../resources/CSS/member/leave.css">

    <!-- contents: leave box -->
    <div class="content">
        <form action="https://gieok.icu/member/leaveOK" method="POST" class="leave_box">
            <h1>회원 탈퇴</h1>

            <div class="pw_check_box">
                <span>비밀번호 확인</span>
                <input type="password" name="user_pw" maxlength="18" id="user_pw">
            </div>


            <div class="leave_button">
                <input type="submit" value="탈퇴">
                <input type="button" value="취소" onclick="window.history.back()">
            </div>
        </form>

    </div>
    <!-- container section end //-->
    <script src="../resources/JS/member/leave.js"></script>

    <!-- ***** footer ***** -->
    <jsp:include page="../static/footer.jsp" />