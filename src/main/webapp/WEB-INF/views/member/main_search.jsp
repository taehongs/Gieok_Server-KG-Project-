<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
 <!-- ***** header ***** -->
<jsp:include page="../static/header.jsp" />
<link rel="stylesheet" href="../resources/CSS/member/main_search.css">

<c:if test="${!empty msg}">
	<script>
		alert(${msg});
	</script>
</c:if>


    <!--// search box start -->
    <form method="get" class="search_box" action="https://gieok.icu/main_search">
        <input type="text" placeholder="어디로 떠나실건가요?:)" name="search" id="search" maxlength="20" value="${search}">
        <!-- search icon -->
        <label for="submit_btn">
	        <svg width="23" height="22" viewBox="0 0 23 22" fill="none" xmlns="http://www.w3.org/2000/svg">
	            <circle cx="10.1102" cy="9.396" r="7.896" stroke="#B3B3B3" stroke-width="3" />
	            <path d="M16.0896 15.8757L20.7143 20.5005" stroke="#B3B3B3" stroke-width="3" stroke-linecap="round" />
	        </svg>
        </label>
	    <input type="submit" id="submit_btn">
    </form>
    <!-- search box end //-->



    <!-- container section start //-->
    <div class="content">

        <div class="search_type1 search_content">
            <h1>명소(${acount}) <span>검색 결과</span> </h1>
            <div class="line"></div>
            <ul>
            	<c:if test="${!empty alist}">
            		<c:forEach var="list" items="${alist}">
            			<li>
                    		<div class="board_info">
                        		<a href="/member/attr?attr_code=${list.attr_code}">
                        		<span>${list.attr_name}</span>
                        		<span>${list.attr_addr}</span>
                        		</a>
                    		</div>
                		<li>
            		</c:forEach>
            	</c:if>
            </ul>
            <c:if test="${empty alist}">
                     <p><b>검색 결과가 없습니다</b></p></c:if>
            <input type="button" class="view_more_btn" value="더 보기" onclick="location.href='https://gieok.icu/member/map'">
        </div>

        <div class="search_type2 search_content">
            <h1>공지/이벤트(${bcount}) <span>검색 결과</span> </h1>
            <div class="line"></div>
             <c:if test="${!empty blist}">
            <ul>
                <c:forEach var="item" items="${blist}">
                <li>
                    <div class="board_info">
                        <a href="/board_cont?no=${item.board_no}&page=1">
                        <span>
	                        <c:if test ="${item.board_type eq '1' }">[공지사항]</c:if>
	                        <c:if test ="${item.board_type eq '2' }">[이벤트]</c:if>
                        	${item.board_title}
                        </span>
                        <span class="board_content">${fn:substring(item.board_content,0,10)}</span>
                        <span class="board_content">${item.board_content}</span>
                        </a>
                    </div>
                </li>
 
              </c:forEach>
            </ul>
            </c:if>
            <c:if test="${empty blist}">
                     <p><b>검색 결과가 없습니다</b></p></c:if>
            <input type="button" class="view_more_btn" value="더 보기" onclick="location.href='https://gieok.icu/board_list?board_sort=all&search_option=board_title&list_search=${search}'">

        </div>

        <div class="search_type3 search_content">
            <h1>동행(${withcount}) <span>검색 결과</span> </h1>
            <div class="line"></div>
           <c:if test="${!empty withlist}"> 
            <ul>
               <c:forEach var="item" items="${withlist}">
                <li>
                    <div class="board_info">
                    <a href="board_with_list?category=board_location&keyword=${search}">
                        <span class="board_content">[${item.board_location}]${item.board_title}</span>
                        <span class="board_content">${item.board_content}</span>
                    </a>
                    </div>
                </li>

                </c:forEach>
            </ul>
            </c:if>
            <c:if test="${empty withlist}">
                     <p><b>검색 결과가 없습니다</b></p></c:if>
			<input type="button" class="view_more_btn" value="더 보기" 
			onclick="location.href='https://gieok.icu/board_with_list?category=board_location&keyword=${search}'">
        </div>

        <div class="search_type4 search_content">
            <h1>포토/이벤트(${photocount}) <span>검색 결과</span> </h1>
            <div class="line"></div>
             <c:if test="${!empty photolist}"> 
            <ul>
                <c:forEach var="item" items="${photolist}">
                <li>
                    <div class="board_img" name="board_img">
                    <img src="../resources/upload/event/${item.board_img}">
                    </div>
                    <div class="board_info">
                    <a href="photo_event_detail?photo_no=${item.board_no}">
                        <span>${item.board_title}</span>
                        <span>${item.board_content}</span>
                    </a>
                    </div>
                </li>
               </c:forEach>
            </ul>
            </c:if>
            <c:if test="${empty photlist}">
                     <p><b>검색 결과가 없습니다</b></p></c:if>

            <!-- view more icon -->
            <input type="button" class="view_more_btn" value="더 보기" 
            onclick="location.href='https://gieok.icu/photo_event_list?sortBy=board_regDate&category=board_title&keyword=${search}'">


        </div>

    <!-- container section end //-->

<!-- ***** footer ***** -->
<script>

	let search_value = document.querySelector('#search').value;
	const span_all = document.querySelectorAll('span');
	for(var i=0; i <span_all.length; i++){
	var search1 = span_all[i].innerText.indexOf(search_value);
		if(search<0){
	    }else{
	    	span_all[i].innerHTML =  span_all[i].innerText.replace(search_value, "<span style='color:blue; font-weight: bold;'>"+search_value+"</span>");
	    }
	}

</script>

<jsp:include page="../static/footer.jsp" />