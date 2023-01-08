<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
 <!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />
<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
	<jsp:include page="../static/sidebar.jsp" />
</c:if>
<c:if test="${sessionScope.grade == 'm'  }">
	<!-- 회원용 리모컨 -->
</c:if>

<link type="text/css" rel="stylesheet" href="../resources/CSS/admin/fac_list.css">

		<!-- 관리자 > 주변 시설 목록 container -->
		<div class="content">
			<div class="faclist_container">
				<div class="faclist_default">
					<form action="https://gieok.icu/admin/fac_delete" method="POST" class="board_cont_form" onsubmit="return fac_delete_validate();">
						<input type="hidden" value="${attr_code}" id="attr_code" name="attr_code">
						<input type="hidden" value="${page}" id="page" name="page">
						<input type="hidden" value="${sort}" id="sort" name="sort">
						<input type="hidden" value="${category}" id="category" name="category">
						<input type="hidden" value="${keyword}" id="keyword" name="keyword">
						<!-- title -->
			            <div class="board_top">
			            	<a href="/admin/attr_detail?attr_code=${attr_code}&page=${page}&sort=${sort}&category=${category}&keyword=${keyword}">
			            		<img class="back_btn" src="../resources/images/attraction/back_btn.png"/>
			            	</a>
			            	<a href="/admin/fac_list?attr_code=${attr_code}">
			                	<h1 class="faclist_title">${attr_name} 주변 시설 목록</h1>
			                </a>
			                <button class="select_delete">선택 삭제</button> 
			                <input type="button" value="주변 시설 등록" class="add_contents">
			            </div> <!-- class="board_top" -->
			            <!-- 목록 -->
			            <div class="board_cont">
			                <div class="list_title">
			                    <span class="board_no">번호</span>
			                    <span class="board_title">주변 시설</span>
			                    <span class="board_addr">주소</span>
			                    <span class="board_stat">타입</span>
			                    <span class="board_date">등록 일자</span>
			                    <span class="board_select">전체 선택</span>
			                </div>
			                <!-- board rows -->
			                <ul class="list">
			                	<c:if test="${!empty fac_list}">
			                		<c:forEach var="fac" items="${fac_list}">
				                		<li>
					                        <span class="board_no">
					                        	${fac.fac_code}
					                        </span>
					                        
					                        <span class="board_title">
					                        	<a href="/admin/fac_detail?fac_code=${fac.fac_code}&page=${page}&sort=${sort}&category=${category}&keyword=${keyword}" class="fac_list_link">
					                        		${fac.fac_name}
					                        	</a>
					                        </span>
					                        <span class="board_addr">
					                        	<a href="/admin/fac_detail?fac_code=${fac.fac_code}&page=${page}&sort=${sort}&category=${category}&keyword=${keyword}" class="fac_list_link">
					                            	${fac.fac_addr}
					                            </a>
					                        </span>
					                        <span class="board_stat">
					                            <b class="event_inprogress">
					                            		<c:if test="${fac.fac_type == 1}">카페</c:if>
					                            		<c:if test="${fac.fac_type == 2}">식당</c:if>
					                            </b>
					                        </span>
					                        <span class="board_date">
					                        	${fn:substring(fac.fac_regdate, 0, 10)}
					                        </span>
					                        <span class="board_select">
					                            <input type="checkbox" name="fac_checkBox" class="fac_checkBox" value="${fac.fac_code}">
					                        </span>
					                    </li>
			                		</c:forEach>
			                	</c:if>
			                	<c:if test="${empty fac_list}">
			                		등록된 주변 시설이 없습니다.
			                	</c:if>
			                </ul>
			            </div> <!-- class="board_cont" -->
			        </form>
		            
		            <!-- board list page number -->
		            <div class="board_page">
		            	<!-- 처음 / 이전 -->
		                <div class="prev">
		                	<c:if test="${page == 1}">
				                <span>처음</span>
				            </c:if>
				            <c:if test="${page > 1}">
				                <a href="/admin/fac_list?attr_code=${attr_code}&page=1&sort=${sort}&category=${category}&keyword=${keyword}">
				                  <span>처음</span>
				                </a>
				            </c:if>
		                	<c:if test="${page < 11}">
				                <span>이전</span>
				            </c:if>
		                	<c:if test="${page >= 11}">
		                		<a href="/admin/fac_list?attr_code=${attr_code}&page=${startPage - 10}&sort=${sort}&category=${category}&keyword=${keyword}">
		                			<span>이전</span>
		                		</a>
		                	</c:if>
		                </div>
		                <!-- page -->
		                <ul class="board_page_num flex_row">
		                    <c:forEach var="p" begin="${startPage}" end="${endPage}" step="1">
								<c:if test="${p == page}">
									<li class="select_page">${p}</li>
								</c:if>
								<c:if test="${p != page}">
									<li>
										<a href="/admin/fac_list?attr_code=${attr_code}&page=${p}&sort=${sort}&category=${category}&keyword=${keyword}">
											${p}
										</a>
									</li>
								</c:if>
							</c:forEach>
		                </ul>
		                <!-- 다음 / 마지막 -->
		                <div class="next">
		                	<c:if test="${(startPage + 10) > totalPage}">
				                <span>다음</span>
				            </c:if>
		                	<c:if test="${(startPage + 10) <= totalPage}">
		                		<a href="/admin/fac_list?attr_code=${attr_code}&page=${startPage + 10}&sort=${sort}&category=${category}&keyword=${keyword}">>
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
		                    	<a href="/admin/fac_list?attr_code=${attr_code}&page=${totalPage}&sort=${sort}&category=${category}&keyword=${keyword}">
		                    		<span>마지막</span>
		                    	</a>
		                    </c:if>
		                </div>
		            </div> <!-- class="board_page" -->
		            <!-- search, sort-->
		            <div class="board_bottom">
		            	<form action="https://gieok.icu/admin/fac_list" class="board_bottom_form" name="search_form">
		            		<input type="hidden" value="${attr_code}" id="attr_code" name="attr_code">
			                <!-- sort -->
			                <div class="board_sort">
			                    <select name="sort" id="board_sort">
			                    	<option value="">
			                    		전체
			                    	</option>
			                        <option value="fac_code" <c:if test="${sort == 'fac_code'}">selected</c:if>>
			                        	번호순
			                        </option>
			                        <option value="fac_regdate" <c:if test="${sort == 'fac_regdate'}">selected</c:if>>
			                        	등록일순
			                        </option>
			                        <option value="fac_name" <c:if test="${sort == 'fac_name'}">selected</c:if>>
			                        	이름순
			                        </option>
			                    </select>
			                    <input type="submit" value="정렬" id="sort_btn">
		                    </div>
			                <!-- search -->
			                <div class="board_search">
			                    <select name="category" id="board_search">
			                        <option value="">
			                        	주변 시설
			                        </option>
			                        <option value="1" <c:if test="${category == '1'}">selected</c:if>>
			                        	카페
			                        </option>
			                        <option value="2" <c:if test="${category == '2'}">selected</c:if>>
			                        	식당
			                        </option>
			                    </select>
			                    <input type="text" name="keyword" placeholder="검색어" id=keyword <c:if test="${keyword != ''}">value = "${keyword}"</c:if>>
			                    <input type="submit" value="검색" id="search_btn">
		                    </div>
	                    </form>
		            </div> <!-- class="board_bottom" -->
				</div> <!-- class="faclist_default" -->
			</div> <!-- class="faclist_container" -->
		</div> <!-- class="content" -->

<!-- 주변 시설 등록 버튼 -->
<script type="text/javascript" src="../resources/JS/admin/fac_list.js"></script>

<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" />
