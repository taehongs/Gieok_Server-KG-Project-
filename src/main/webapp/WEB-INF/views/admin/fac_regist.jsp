<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
 <!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />
<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
	<jsp:include page="../static/sidebar.jsp" />
</c:if>
<c:if test="${sessionScope.grade == 'm'  }">
	<!-- 회원용 리모컨 -->
</c:if>

<link type="text/css" rel="stylesheet" href="../resources/CSS/admin/fac_regist.css">

	<!-- 관리자 > 주변 시설 명소 등록 -->
		<!-- 관리자 > 주변 시설 등록 container -->
		<div class="content">
			<!-- 주변 시설 명소 등록 -->
			<div class="facregist_container">
				<div class="facregist_default">
					<form action="https://gieok.icu/admin/fac_regist" method="POST" enctype="multipart/form-data" name="fac_regist" onsubmit="return formValidate();">
						<input type="hidden" value="${attr_code}" name="attr_code" id="attr_code">
						<!-- 명소이름 -->
						<div class="facreg1">
							<label class="facreg_title wf-gangwonTT">${attr_name} 주변 시설 등록</label>
						</div>
						<!-- 제목 -->
						<div class="facreg2 flex_row">
							<select id="fac_type" name="fac_type" class="fac_type">
								<option value="" disabled selected hidden>타입</option>
								<option value="1">카페</option>
								<option value="2">식당</option>
							</select>
							<input type="text" name="fac_name" class="facreg_inp_title" placeholder="주변 시설 이름" maxlength="15">
						</div>
						<!-- 내용 -->
						<div class="facreg3 flex_row">
							<!-- 내용 입력 -->
							<div class="inp_cont flex_column">
								<!-- 주소 검색 -->
								<div class="facreg_addr flex_row">
									<input type="text" id="facreg_inp_addr" name="fac_addr" class="facreg_inp_addr" placeholder="주소" maxlength="" readonly>
									<input type="button" id="facreg_inp_addrbtn" class="facreg_inp_addrbtn" value="검색" onclick="find_addr()">
								</div>
								<input type="text" name="fac_link" class="facreg_inp_link" maxlength="1000" placeholder="홈페이지 링크">
								<div class="facreg_time flex_row">
									<label for="optime">Open</label>
									<input type="time" name="fac_optime" id="optime" class=facreg_inp_time>
									<label for="cltime">Close</label>
									<input type="time" name="fac_cltime" id="cltime" class=facreg_inp_time>
								</div>
							</div>
							<!-- 이미지 업로드 -->
							<div class="facreg_inp_img">
								<!-- 명소 main 이미지 -->
								<div class="fac_inp_imgcon flex_column">
									<label class="preview_title">미리보기</label>	
									<label class="preview_size">500px * 300px</label>
									<img id="preview_img" class="preview_img" src="#" onerror="this.src='../resources/images/attraction/no_image.png'"/>
									<label class="preview_btn" for="pre_img">사진 등록</label>
									<input type="file" id="pre_img" accept="image/*" name="image" onchange="preview()">
								</div>
							</div>
						</div>
						<!-- 하단 버튼 -->
						<div class="facreg4 flex_row">
							<input type="submit" name="" class="facreg_reg_btn" value="등록">
							<input type="button" name="" class="facreg_cac_btn" value="취소">
						</div>
					</form>
				</div> <!-- class="facregist_default" -->
			</div> <!-- class="facregist_container" -->
		</div> <!-- class="content" -->
<!-- 주소 api -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- 이미지 업로드 -->
<script type="text/javascript" src="../resources/JS/admin/fac_regist.js"></script>

<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" />