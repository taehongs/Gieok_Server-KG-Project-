<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />
<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
   <jsp:include page="../static/sidebar.jsp" />
</c:if>


<link type="text/css" rel="stylesheet"
   href="../resources/CSS/admin/attr_list.css">

<div class="content">
   <!-- 주변 시설 명소 목록 ================================================ -->
   <!-- contents: board list box -->
   
   <div class="board_list">
   <form action="https://gieok.icu/admin/attr_delete" method="POST" onsubmit="return attr_delete_validate();">
   	  <input type="hidden" value="${page}" name="page">
   	  <input type="hidden" value="${sort}" name="sort">
   	  <input type="hidden" value="${category}" name="category">
   	  <input type="hidden" value="${keyword}" name="keyword">
      <div class="board_top">
         <h1 class="board_title">명소 목록</h1>

         <span class="select_delete"><button class="del_btn">선택 삭제</button></span> 
         <input type="button" value="명소 등록" class="add_contents">
      </div>
      <!-- board column title -->
      <div class="board_cont">
         <div class="list_title">
            <span class="board_no">번호</span> <span class="board_writer">명소
               이름</span> <span class="board_title">주소</span> <span class="board_stat">지역</span>
            <span class="board_date">등록 일자</span> <span class="board_select">전체
               선택</span>
         </div>
         <!-- board rows -->
         <ul class="list">
            <c:if test="${!empty attr_list }">
               <c:forEach var="attr" items="${attr_list }">
                  <li><span class="board_no">${attr.attr_code } </span> 
                  <span class="board_writer"> 
                     <a href="/admin/attr_detail?attr_code=${attr.attr_code }&page=${page }
                     		&sort=${sort}&category=${category}&keyword=${keyword}" class="detail_name_link">
                           ${attr.attr_name } 
                     </a>
                  </span> 
                  <span class="board_title">
                     <a href="/admin/attr_detail?attr_code=${attr.attr_code }&page=${page }
                     		&sort=${sort}&category=${category}&keyword=${keyword}" class="detail_addr_link">
                        ${attr.attr_addr }
                     </a>
                  </span>
                  <span class="board_stat"> ${attr.attr_city } </span> 
                  <span class="board_date"> ${attr.attr_regDate } </span> 
                  <span class="board_select"> 
                     <input type="checkbox" name="attr_checkBox" class="attr_checkBox" value="${attr.attr_code }">
                  </span>
               </li>
               </c:forEach>
            </c:if>
         </ul>
      </div>
      </form>

      <!-- board list page number -->
      <div class="board_page">

         <div class="prev">
            <c:if test="${page==1 }">
               <span class="prev_next">처음</span>
            </c:if>
            <c:if test="${page>1 }">
               <a
                  href="/admin/attr_list?page=1&sort=${sort}&category=${category}&keyword=${keyword}">
                  <span class="prev_next">처음</span>
               </a>
            </c:if>
            <c:if test="${page<11 }">
               <span class="prev_next">이전</span>
            </c:if>
            <c:if test="${page>=11 }">
               <a
                  href="/admin/attr_list?page=${startPage-10 }&sort=${sort}&category=${category}&keyword=${keyword}">
                  <span class="prev_next">이전</span>
               </a>
            </c:if>
         </div>

         <ul class="board_page">
            <c:forEach var="p" begin="${startPage }" end="${endPage }" step="1">
               <c:if test="${p==page }">
                  <li class="select_page">${p }</li>
               </c:if>

               <c:if test="${p!=page }">
                  <li><a
                     href="/admin/attr_list?page=${p }&sort=${sort}&category=${category}&keyword=${keyword}">${p }</a></li>
               </c:if>
            </c:forEach>
         </ul>

         <div class="next">
            <c:if test="${(startPage+10)>totalPage }">
               <span class="prev_next">다음</span>
            </c:if>
            <c:if test="${(startPage+10)<=totalPage }">
               <a
                  href="/admin/attr_list?page=${startPage+10 }&sort=${sort}&category=${category}&keyword=${keyword}">
                  <span class="prev_next">다음</span>
               </a>
            </c:if>
            <c:if test="${page==totalPage }">
               <span class="prev_next">마지막</span>
            </c:if>
            <c:if test="${page<totalPage }">
               <a
                  href="/admin/attr_list?page=${totalPage }&sort=${sort}&category=${category}&keyword=${keyword}">
                  <span class="prev_next">마지막</span>
               </a>
            </c:if>
         </div>
      </div>

      <!-- search, sort-->

      <form action="https://gieok.icu/admin/attr_list" class="board_bottom"
         name="search_form">
         <!-- sort -->
         <div class="board_sort">
            <select name="sort" id="board_sort">
            	<option value="">전체</option>
                <option value="attr_code" <c:if test="${sort == 'attr_code'}">selected</c:if>>
 					번호순
 				</option>
                <option value="attr_name" <c:if test="${sort == 'attr_name'}">selected</c:if>>
                 	이름순
                </option>
            </select> <input type="submit" value="정렬" id="sort_btn">
         </div>
         <!-- search -->
         <div class="board_search">
            <select name="category" id="board_search">
               <option value="">카테고리</option>
               <option value="attr_name"
                  <c:if test="${category=='attr_name'}">selected</c:if>>
                  명소이름</option>
               <option value="attr_city"
                  <c:if test="${category=='attr_city'}">selected</c:if>>지역</option>
            </select> <input type="text" name="keyword" placeholder="검색어"
               <c:if test="${keyword != ''}">value="${keyword }"</c:if>> <input
               type="submit" value="검색" id="search_btn">
         </div>
      </form>
   </div>
</div>
<!-- container section end //-->




<script type="text/javascript" src="../resources/JS/admin/attr_list.js"></script>

<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" /> 