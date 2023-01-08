<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../static/header.jsp" />
<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
	<jsp:include page="../static/sidebar.jsp" />
</c:if>
<c:if test="${(sessionScope.grade == 'm' || empty sessionScope.grade)}">
	<!-- 회원용 리모컨 -->
	<jsp:include page="../static/board_sidebar.jsp"/>
</c:if>
<link rel="stylesheet" href="../resources/CSS/static/header.css">
<link rel="stylesheet"
	href="../resources/CSS/member/mywrite_with_list.css">


<!--// contents section start -->
<section class="content">
	<input type="hidden" value="${sessionScope.code}" id="user_code">
	<!-- contents: board list box -->
	<div class="board_with">
		<form>
			<input type="hidden" value="${page}" id="page" name="page"/>
			<input type="hidden" value="${category}" id="category" name="category" />
			<input type="hidden" value="${keyword}" id="keyword" name="keyword" />
			
			<div class="board_top">
				<span class="topbtn">
					<a class="with_my" href="/board_with_list">동행 목록</a>
					<span class="with_slash"><b>/</b></span>
					<a class="with_sin" href="/board_with_sindong">신청 동행</a>
				</span>
				
				<h1>
					<a class="with-main-point" href="/mywrite_with_list">나의 동행</a>
				</h1>
			</div>

			<div class="board_with_list">
				<ul class="with_list">
				<c:if test="${empty with_li_list}">
					<div class="li_none">
						등록된 동행이 없습니다😭
					</div>
				</c:if>
				<c:if test="${!empty with_li_list}">
					<c:forEach var="with" items="${with_li_list}">
	 					<li style="position: relative;">
							<div class="li_box">
								<button class="delBtn" id="delBtn">삭제</button>
								<h2>${with.board_title}</h2>
								<div class="li_with_cont">
									<p>
										<b>작성자 :</b>&nbsp
										<span class="li_box_list">
											${with.board_writer}
										</span>
										&nbsp&nbsp
										<b>장소 :</b>&nbsp
										<span class="li_box_list">${with.board_location}</span>
									</p>
									<p>
										&nbsp&nbsp
										<b>모집기간 :</b>&nbsp
										<span class="li_box_list">${with.board_startDay} <b>~</b> ${with.board_endDay}</span>
										&nbsp&nbsp
										<b>모집인원 :</b>&nbsp
										<span class="li_box_list">${with.board_memCount}</span>
									</p>
								</div>
	
								<div class="with_contbox">${with.board_content}</div>
								
								<input type="hidden" class="with_submit_board_no" value="${with.board_no}" name="board_no">
								<input type="hidden" class="with_submit_board_writer" value="${with.board_writer}" name="board_writer">
								<input type="hidden" class="with_submit_board_memCount" value="${with.board_memCount}" name="board_memCount">
	
								<input class="with_accept" id="with_accept" type="button" value="${with.with_accept}">
							</div>
						</li>			
					</c:forEach>
				</c:if>
				</ul>
			</div>
		</form>
		<!-- // bottom -->
		<div class="board_last">
		
			<!-- board list page number -->
			<div class="board_pagination">
				<div class="prev">
					<c:if test="${page == 1}">
						<span>처음</span>
					</c:if>
					<c:if test="${page > 1}">
						<a href="/mywrite_with_list?page=1&category=${category}&keyword=${keyword}">
							<span>처음</span>
						</a>
					</c:if>
					<c:if test="${page < 11}">
						<span>이전</span>
					</c:if>
					<c:if test="${page >= 11}">
						<a href="/mywrite_with_list?page=${startPage - 10}&category=${category}&keyword=${keyword}">
							<span>이전</span>
						</a>
					</c:if>
				</div>
				
				<ul class="board_page">
					<c:forEach var="p" begin="${startPage}" end="${endPage}" step="1">
						<c:if test="${p == page}">
							<li class="now_P">${p}</li>
						</c:if>
						<c:if test="${p != page}">
							<li>
								<a href="/mywrite_with_list?page=${p}&category=${category}&keyword=${keyword}">
									${p}
								</a>
							</li>
						</c:if>	
					</c:forEach>
				</ul>
				
				<div class="next">
					<c:if test="${(startPage + 10) > totalPage}">
						<span>다음</span>
					</c:if>
					<c:if test="${(startPage + 10) <= totalPage}">
						<a href="/mywrite_with_list?page=${startPage + 10}&category=${category}&keyword=${keyword}">
							<span>다음</span>
						</a>
					</c:if>
					<c:if test="${page == totalPage}">
						<span>마지막</span>
					</c:if>
					<c:if test="${totalPage == 0}">
							<span>마지막</span>
					</c:if>			
					<c:if test="${page < totalPage}">
						<a href="/mywrite_with_list?page=${totalPage}&category=${category}&keyword=${keyword}">
							<span>마지막</span>
						</a>
					</c:if>
				</div>
			</div>

			<!-- search, event -->
			<div class="board_bottom">
				<!-- search -->
				<form action="https://gieok.icu/mywrite_with_list" class="board_src">
					<select name="category" class="with_category">
						<option value="" selected>선택</option>
						<option value="board_writer" <c:if test="${category == 'board_writer'}"> selected</c:if>>작성자</option>
						<option value="board_title" <c:if test="${category == 'board_title'}"> selected</c:if>>제목</option>
						<option value="board_location" <c:if test="${category == 'board_location'}"> selected</c:if>>지역 / 명소</option>
						<option value="board_content" <c:if test="${category == 'board_content'}"> selected</c:if>>내용</option>
					</select>
					<input class="search_field" type="text" name="keyword" maxlength="10" placeholder="검색어">
					<input class="search_cont" type="submit" value="검색">
				</form>

			</div>
		</div>
	</div>
</section>
<!-- container section end //-->

<!-- 신청 목록 modal -->

<div class="modal_back" id="modal_back" onclick="popClose()"></div>
<div class="modal_container" id="modal_container">
	<div class="count_box">
		<div class="with_count wcount" id="with_count"></div>
		<div class="accept_count wcount" id="accept_count">수락한 동행 수 : xx명</div>
	</div>
	
	<!-- modal 닫기 버튼 -->
	<div class="modal_close">
		<svg width="25" height="25" class="modal_close_btn" id="modal_close_btn" viewBox="0 0 25 25" xmlns="http://www.w3.org/2000/svg" onClick="popClose()" role="button">
			<path fill-rule="evenodd" clip-rule="evenodd" d="M2.29297 3.70706L10.5859 12L2.29297 20.2928L3.70718 21.7071L12.0001 13.4142L20.293 21.7071L21.7072 20.2928L13.4143 12L21.7072 3.70706L20.293 2.29285L12.0001 10.5857L3.70718 2.29285L2.29297 3.70706Z" fill="#fff"></path>
		</svg>
	</div>
	
	<!-- modal 내용 -->
	<div class="modal_con">
		<div class="modal_header">
			<span>동행 신청 목록</span>
		</div>
		<div class="modal_content">
			<div class="with_user_con">
			</div>
		</div>
	</div>
</div>

<div class="message_block"></div>
<!-- 메세지 창 -->
<div class="message_container">
</div>



<script src="../resources/JS/member/mywrite_with_list.js"></script>
<jsp:include page="../static/footer.jsp" />
