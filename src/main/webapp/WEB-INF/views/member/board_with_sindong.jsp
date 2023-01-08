<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../static/header.jsp" />
<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
	<jsp:include page="../static/sidebar.jsp" />
</c:if>
<c:if test="${(sessionScope.grade == 'm' || empty sessionScope.grade)  }">
	<jsp:include page="../static/board_sidebar.jsp"/>
	<!-- 회원용 리모컨 -->
</c:if>
<link rel="stylesheet" href="../resources/CSS/static/header.css">
<link rel="stylesheet"
	href="../resources/CSS/member/board_with_sindong.css">


<!--// contents section start -->
<section class="content">
	<input type="hidden" value="${sessionScope.id }" id="user_id">
	<input type="hidden" value="${sessionScope.code }" id="user_code">
	<input type="hidden" value="${sessionScope.grade }" id="user_grade">
	<!-- contents: board list box -->
	<div class="board_with">
		<form name="board_with">
		
			<input type="hidden" value="${page}" id="page" name="page"/>
			<input type="hidden" value="${category}" id="category" name="category" />
			<input type="hidden" value="${keyword}" id="keyword" name="keyword" />
			
			<div class="board_top">
			
			<c:if test="${(sessionScope.grade == 'm' || sessionScope.grade == 'a' || sessionScope.grade == 's')}">
				<span class="topbtn">
					<a class="with_my" href="/board_with_list">동행 목록</a>
					<span class="with_slash"><b>/</b></span>
					<a class="with_my" href="/mywrite_with_list">나의 동행</a>
				</span>
			</c:if>
				
				<h1>
					<a class="with-main-point" href="/board_with_sindong">신청 동행</a>
				</h1>
				
			</div>

			<div class="board_with_list">
				<ul class="with_list">
				<c:if test="${empty with_li_list}">
					<div class="li_none">
						신청한 동행이 없습니다😭
					</div>
				</c:if>
				<c:if test="${!empty with_li_list}">
					<c:forEach var="with" items="${with_li_list}">
	 					<li style="position: relative;">
							<div class="li_box">
								<h2>${with.board_title} </h2>
								<div class="li_with_cont">
									<p>
										<span class="with_statustxt">[${with.with_accept }]</span>
										<b>작성자 : </b>&nbsp
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
								
								<input class="with_cancelBtn" name="with_cancelBtn" type="button" value="신청취소">
								
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
						<a href="/board_with_sindong?page=1&category=${category}&keyword=${keyword}">
							<span>처음</span>
						</a>
					</c:if>
					<c:if test="${page < 11}">
						<span>이전</span>
					</c:if>
					<c:if test="${page >= 11}">
						<a href="/board_with_sindong?page=${startPage - 10}&category=${category}&keyword=${keyword}">
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
								<a href="/board_with_sindong?page=${p}&category=${category}&keyword=${keyword}">
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
						<a href="/board_with_sindong?page=${startPage + 10}&category=${category}&keyword=${keyword}">
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
						<a href="/board_with_sindong?page=${totalPage}&category=${category}&keyword=${keyword}">
							<span>마지막</span>
						</a>
					</c:if>
				</div>
			</div>

			<!-- search, event -->
			<div class="board_bottom">
				<!-- search -->
				<form action="https://gieok.icu/board_with_sindong" class="board_src">
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

<script src="../resources/JS/member/board_with_sindong.js"></script>
<jsp:include page="../static/footer.jsp" />
