<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <!-- ***** header ***** -->
    <jsp:include page="../static/header.jsp" />
    <link rel="stylesheet" href="../resources/CSS/member/pw_edit.css">

    <!-- contents: board list box -->
    <!-- contents: board list box -->
    <div class="content">
        <form action="https://gieok.icu/member/pw_editOK" method="POST" class="member_pedit_box" onsubmit="return pw0_check();">
            <h1>비밀번호 변경</h1>

            <!--member_pedit_p1-->
            <div class="member_pedit_p1">
                <h2>기존 비밀번호</h2>
                <input type="password" maxlength="18" name="pw">
            </div>

            <!--ㅡmember_pedit_p2-->
            <div class="member_pedit_p2">
                <h2>새로운 비밀번호</h2>
                <input type="password" maxlength="18" name="user_pw">
                <span>소문자
                    <svg width="10" height="10" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M1 3.5L4.5 8.5L8.5 1"" stroke-width=" 2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </span>
                <span>대문자
                    <svg width="10" height="10" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M1 3.5L4.5 8.5L8.5 1"" stroke-width=" 2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </span>
                <span>숫자
                    <svg width="10" height="10" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M1 3.5L4.5 8.5L8.5 1"" stroke-width=" 2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </span>
                <span>특수문자
                    <svg width="10" height="10" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M1 3.5L4.5 8.5L8.5 1"" stroke-width=" 2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </span>
                <span>8 ~ 18자리
                    <svg width="10" height="10" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M1 3.5L4.5 8.5L8.5 1"" stroke-width=" 2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </span>
            </div>


            <!--ㅡmember_pedit_p3-->
            <div class="member_pedit_p3">
                <h2>새로운 비밀번호 확인</h2>
                <input type="password" maxlength="18">
                <span>비밀번호 확인
                    <svg width="10" height="10" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M1 3.5L4.5 8.5L8.5 1"" stroke-width=" 2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </span>


            </div>

            <!--member_pedit_button-->
            <div class="member_pedit_button">
                <input type="submit" value="변경하기">
                <input type="button" value="취소" onclick="window.history.back()">

            </div>

        </form>

    </div>
    <!-- container section end //-->
    <script src="../resources/JS/member/pw_edit.js">

    </script>

    <!-- ***** footer ***** -->
    <jsp:include page="../static/footer.jsp" />