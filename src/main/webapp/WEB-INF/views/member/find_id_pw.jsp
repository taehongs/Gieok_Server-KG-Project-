<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
 <!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />
<link rel="stylesheet" href="../resources/CSS/member/find_id_pw.css">
	
    <!-- contents: board list box -->
    <div class="content">
        <div class="find_box">
            <input type="radio" name="find_select" id="show_find_id" checked>
            <input type="radio" name="find_select" id="show_find_pw">
            <div class="find_title">
                <h1>아이디/비밀번호 찾기</h1>
                <label for="show_find_id">아이디 찾기</label>
                <label for="show_find_pw">비밀번호 찾기</label>
            </div>

            <!-- find id -->
            <form action="" id="find_id" name="find_id_form">
                <div class="user_phone_box">
                    <h2>연락처</h2>
                    <select id="user_mobcarr" name="user_mobcarr">
                        <option value="none">통신사</option>
                        <option value="SKT">SKT</option>
                        <option value="KT">KT</option>
                        <option value="LGU">LGU</option>
                        <option value="SAVE">알뜰</option>
                    </select>
                    <input type="text" maxlength="3" name="user_ph1" placeholder="010">
                    <input type="text" maxlength="4" name="user_ph2" placeholder="0000">
                    <input type="text" maxlength="4" name="user_ph3" placeholder="0000">
                </div>

                <!-- member join email -->
                <div class="user_email_box">
                    <h2>이메일</h2>
                    <input type="text" maxlength="12" name="user_email">
                    <span>@</span>
                    <input type="text" maxlength="12" name="user_domain">
                    <select name="email" id="user_domain_select">
                        <option value="">직접입력</option>
                        <option value="gmail.com">구글</option>
                        <option value="naver.com">네이버</option>
                        <option value="kakao.com">카카오</option>
                        <option value="nate.com">네이트</option>
                        <option value="hanmail.net">다음</option>
                    </select>
                </div>

                <div class="user_button_box">
                    <input type="button" value="찾기">
                    <input type="button" value="취소" onclick="window.history.back()">
                </div>
            </form>

            <!-- find pw -->
            <form action="" id="find_pw" name="find_pw_form">
                <div class="user_id_box">
                    <h2>아이디</h2>
                    <input type="text" name="user_id">
                </div>
                <div class="user_phone_box">
                    <h2>연락처</h2>
                    <select id="user_mobcarr" name="user_mobcarr">
                        <option value="none">통신사</option>
                        <option value="SKT">SKT</option>
                        <option value="KT">KT</option>
                        <option value="LGU">LGU</option>
                        <option value="SAVE">알뜰</option>
                    </select>
                    <input type="text" maxlength="3" name="user_ph1" placeholder="010">
                    <input type="text" maxlength="4" name="user_ph2" placeholder="0000">
                    <input type="text" maxlength="4" name="user_ph3" placeholder="0000">
                </div>

                <!-- member join email -->
                <div class="user_email_box">
                    <h2>이메일</h2>
                    <input type="text" maxlength="12" name="user_email">
                    <span>@</span>
                    <input type="text" maxlength="12" name="user_domain">
                    <select name="email" id="user_domain_select">
                        <option value="">직접입력</option>
                        <option value="gmail.com">구글</option>
                        <option value="naver.com">네이버</option>
                        <option value="kakao.com">카카오</option>
                        <option value="nate.com">네이트</option>
                        <option value="hanmail.net">다음</option>
                    </select>
                </div>

                <div class="user_button_box">
                    <input type="button" value="찾기">
                    <input type="button" value="취소" onclick="window.history.back()">
                </div>
            </form>
        </div>
    </div>
    <!-- container section end //-->
    <script src="../resources/JS/member/find_id_pw.js"></script>
    
<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" />