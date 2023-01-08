<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!-- ***** header ***** -->
        <jsp:include page="../static/header.jsp" />
        <link rel="stylesheet" href="../resources/CSS/member/inform_edit.css">

        <!-- contents: board list box -->
        <div class="content">
            <!---member edit form start--->
            <form class="member_edit_box" name="edit_form" enctype="multipart/form-data">
                <h1>회원 정보 수정</h1>

                <!--member edit id-->

                <div class="member_profile_img">
					<c:if test="${sessionScope.profile == 'default_profile.png' }">
	            		<img class="user_profile" src="/resources/images/${sessionScope.profile }">
		            </c:if>
		            <c:if test="${sessionScope.profile != 'default_profile.png' }">
	            		<img class="user_profile" src="/resources/upload/profile/${sessionScope.profile }">
		            </c:if>
                    <label for="profile">사진 추가</label>
                    <input type="file" name="profile" id="profile">
                </div>


                <div class="member_edit_id">
                    <h2>아이디</h2>
                    <input type="text" name="user_id" value="${user.user_id}" readonly>

                </div>

                <!--member edit name-->
                <div class="member_edit_name">
                    <h2>이름</h2>
                    <input type="text" name="user_name" value="${user.user_name}" readonly>

                </div>

                <!--member edit phone-->
                <div class="member_edit_phone">
                    <h2>연락처</h2>
                    <select name="user_mobcarr" id="member_edit_phone">
                        <option value="none">통신사</option>
                        <option value="SKT" <c:if test="${user.user_mobcarr == 'SKT'}">selected</c:if>>SKT</option>
                        <option value="KT" <c:if test="${user.user_mobcarr == 'KT'}">selected</c:if>>KT</option>
                        <option value="LGU" <c:if test="${user.user_mobcarr == 'LGU'}">selected</c:if>>LGU</option>
                        <option value="SAVE" <c:if test="${user.user_mobcarr == 'SAVE'}">selected</c:if>>알뜰</option>
                    </select>
                    <input type="text" maxlength="3" value="${user.user_ph1}" name="user_ph1">
                    <input type="text" maxlength="4" value="${user.user_ph2}" name="user_ph2">
                    <input type="text" maxlength="4" value="${user.user_ph3}" name="user_ph3">
                </div>

                <!--member edit email-->
                <div class="member_edit_email">
                    <h2>이메일</h2>
                    <input type="text" maxlength="12" value="${user.user_email}" name="user_email">
                    <span>@</span>
                    <input type="text" maxlength="12" value="${user.user_domain}" name="user_domain">
                    <select name="domain" id="member_edit_email">
                        <option value="">직접입력</option>
                        <option value="gmail.com" <c:if test="${user.user_domain == 'gmail.com'}">selected</c:if>>구글</option>
                        <option value="naver.com" <c:if test="${user.user_domain == 'naver.com'}">selected</c:if>>네이버</option>
                        <option value="kakao.com" <c:if test="${user.user_domain == 'kakao.com'}">selected</c:if>>카카오</option>
                        <option value="nate.com" <c:if test="${user.user_domain == 'nate.com'}">selected</c:if>>네이트</option>
                        <option value="hanmail.net" <c:if test="${user.user_domain == 'hanmail.com'}">selected</c:if>>다음</option>
                    </select>
                </div>


                <!--member edit button-->
                <div class="member_edit_button">
                    <input type="button" value="수정하기">
                    <input type="button" value="취소" onclick="window.history.back();">
                    <!--ㅡ비밀번호변경하기, 회원탈퇴-->
                    <span>
                        <a href="/member/pw_edit" title="비밀번호 변경하기로 이동">비밀번호 변경</a>
                        <a href="/member/leave" title="계정 탈퇴">계정 탈퇴</a>
                    </span>
                </div>

            </form>
            <!--member edit form end-->
        </div>
        <!-- container section end //-->
        <script src="../resources/JS/member/inform_edit.js"></script>

        <!-- ***** footer ***** -->
        <jsp:include page="../static/footer.jsp" />