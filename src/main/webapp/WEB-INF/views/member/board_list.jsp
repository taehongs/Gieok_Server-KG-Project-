<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../static/header.jsp" />

<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
	<jsp:include page="../static/sidebar.jsp" />
</c:if>
<c:if test="${(sessionScope.grade == 'm' || empty sessionScope.grade)  }">
	<jsp:include page="../static/board_sidebar.jsp"/>
	<!-- 회원용 리모컨 -->
</c:if>

    <link rel="stylesheet" href="../resources/CSS/static/header.css">
    <link rel="stylesheet" href="../resources/CSS/member/board_list.css">

	<div class="content">
        <!-- contents: board list box -->
        <div class="board_list">
        <form method="post">
        <input type="hidden" value="${page}" name="page">
        <input type="hidden" value="${board_sort}" name="board_sort">
        <input type="hidden" value="${search_option}" name="search_option">
        <input type="hidden" value="${list_search}" name="list_search">
        	<input type="hidden" value="${sessionScope.grade}" id="grade">
            <div class="board_top">
                <h1 class="board_title">NEWS & EVENT 목록</h1>
                <c:if test="${sessionScope.grade =='a' || sessionScope.grade =='s'}">
                	<span class="select_delete">
                	<button type="submit" class="del_btn" id="del_btn" formaction="https://gieok.icu/admin/board_del" onsubmit="deleteCheck()" disabled>선택 삭제</button>
                	<span class="del_count">(0)</span></span>
                	<input type="button" value="게시물 등록" class="add_contents" onclick="location.href='https://gieok.icu/admin/board_write';">
                </c:if>
            </div>
            <!-- board column title -->
            <div class="board_cont">
                <div class="list_title">
                    <span class="board_no">번호</span>
                    <span class="board_writer">작성자</span>
                    <span class="board_title">제목</span>
                    <span class="board_stat">상태</span>
                    <span class="board_date">등록 일자</span>
                    <c:if test="${empty sessionScope.grade || sessionScope.grade == 'm'}">
                    	<span class="board_select">조회</span>
                    </c:if>
                    <c:if test="${sessionScope.grade =='a' || sessionScope.grade =='s'}">
                    	<span class="board_select" >전체 선택</span>
                    </c:if>
                </div>
                <!-- board rows -->
                <ul class="list">
					<c:if test="${empty blist}">
						<p>등록된 글이 없습니다!</p>
					</c:if>
					
					<c:if test="${!empty blist}">
						<c:forEach var="list" items="${blist}">
		                    <li>
		                        <span class="board_no">${list.board_no}</span>
		                        <span class="board_writer">${list.board_writer}</span>
		                        <span class="board_title">
		                            <a href="/board_cont?no=${list.board_no}&page=${page}"><b>
		                            <c:if test="${list.board_type == 1}">[공지]</c:if>
		                            <c:if test="${list.board_type == 2}">[이벤트]</c:if>
		                            </b>${list.board_title}</a>
		                        </span>
		                        <span class="board_state">
									<c:if test="${today <= fn:substring(list.board_endDay,0,10)}">
									<b class="event_inprogress">진행중</b>
									</c:if>
									<c:if test="${!empty list.board_endDay && today > fn:substring(list.board_endDay,0,10)}">
									<b class="event_inprogress" style="color:black;">종료</b>
									</c:if>
		                            <c:if test="${empty list.board_endDay}">
									<b class="event_inprogress"></b>
									</c:if>
		                        </span>
		                        <span class="board_date">
		                            <c:if test="${list.board_type == 1}">${fn:substring(list.board_regDate,0,10)}</c:if>
		                            <c:if test="${list.board_type == 2}">
			                            ${fn:substring(list.board_startDay,0,10)}
			                            <br>
			                            ~${fn:substring(list.board_endDay,0,10)}
		                            </c:if>
		                        </span>
		                        <span class="board_select">
									<c:if test="${empty sessionScope.grade || sessionScope.grade == 'm'}">
									${list.board_hit}
									</c:if>
									<c:if test="${sessionScope.grade =='a' || sessionScope.grade =='s'}">
		                            <input type="checkbox" name="check_num" value="${list.board_no}">
		                            </c:if>
		                        </span>
		                    </li>
						</c:forEach>
					</c:if>
                </ul>
            </div>
            
            <c:if test="${sessionScope.grade == 'a' || sessionScope.grade == 's'}">
                		<button  class="event_end_button" formaction="https://gieok.icu/admin/event_end">
                			선택 이벤트 종료
                		</button>
	        </c:if>
        </form>
            <!-- board list page number -->
            <div class="board_page">
                <div class="prev">
                    <c:if test="${page==1}"><span>처음</span></c:if>
                    <c:if test="${page>1}"><span><a href="/board_list?page=1&board_sort=${board_sort}&search_option=${search_option}&list_search=${list_search}">처음</a></span></c:if>
                    <c:if test="${page<11}"><span>이전</span></c:if>
                    <c:if test="${page>=11}"><span><a href="/board_list?page=${startPage-10}&board_sort=${board_sort}&search_option=${search_option}&list_search=${list_search}">이전</a></span></c:if>
                </div>
                <ul class="board_page">
                    <c:forEach var="p" begin="${startPage}" end="${endPage}" step="1">
                    	<c:if test="${p == page}">
	                    	<li class="select_page">${p}</li>
                    	</c:if>
                    	<c:if test="${p != page}">
                    	<li><span><a href="/board_list?page=${p}&board_sort=${board_sort}&search_option=${search_option}&list_search=${list_search}">${p}</a></span></li>
                    	</c:if>
                    </c:forEach>
                </ul>
                <div class="next">
                <c:if test="${(page+10)>totalPage}"><span>다음</span></c:if>
                <c:if test="${(page+10)<=totalPage}"><span><a href="/board_list?page=${startPage+10}&board_sort=${board_sort}&search_option=${search_option}&list_search=${list_search}">다음</a></span></c:if>
				<c:if test="${page==totalPage}"><span>마지막</span></c:if>
				<c:if test="${page<totalPage}"><span><a href="/board_list?page=${totalPage}&board_sort=${board_sort}&search_option=${search_option}&list_search=${list_search}">마지막</a></span></c:if>
                </div>
            </div>
            <!-- search, event -->
	            <div class="board_bottom">
			<form method="get" name="sort_search" action="https://gieok.icu/board_list">
	                <!-- sort -->
	                <div class="board_sort">
	                    <select name="board_sort" id="board_sort">
	                        <option value="all">전체</option>
	                        <option value="1" 
	                        <c:if test="${board_sort == '1'}">selected</c:if>>공지</option>
	                        <option value="2"
	                        <c:if test="${board_sort == '2'}">selected</c:if>>이벤트</option>
	                        <option value="desc"
	                        <c:if test="${board_sort == 'desc'}">selected</c:if>>최신순</option>
	                        <option value="asc"
	                        <c:if test="${board_sort == 'asc'}">selected</c:if>>오래된순</option>
	                    </select>
	                    <input type="submit" value="정렬">
	                </div>
	                <!-- search -->
	                <div class="board_search">
	                    <select name="search_option" id="board_search">
	                        <option value="board_title"
	                        <c:if test="${search_option == 'board_title'}">selected</c:if>>제목</option>
	                        <option value="board_content"
	                        <c:if test="${search_option == 'board_content'}">selected</c:if>>내용</option>
	                        <option value="board_writer" 
	                        <c:if test="${search_option == 'board_writer'}">selected</c:if>>작성자</option>
	                    </select>
	                    <input type="text" name="list_search" placeholder="검색어를 입력해주세요:)"
	                    <c:if test="${list_search != ''}">value="${list_search}"</c:if>>
	                    <input type="submit" value="검색">
	                </div>
			</form>
	                <!-- event end -->

	            </div>
        </div>
	</div>
    <!-- container section end //-->
    <script src="/resources/JS/member/board_list.js"></script>
<jsp:include page="../static/footer.jsp"/>   