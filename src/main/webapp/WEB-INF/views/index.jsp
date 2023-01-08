<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
 <!-- ***** header ***** -->
<jsp:include page="./static/header.jsp" />
<link rel="stylesheet" href="../resources/CSS/index.css">

<c:if test="${!empty msg}">
	<script>
		alert(${msg});
	</script>
</c:if>

<div class="scrollbar">
	<label class="label_item item1"></label>
	<label class="label_item item2"></label>
</div>

<!--// search box start -->
    <div class="search_box">
        <h1>
            <span>Let's</span><br>
            Remember
        </h1>
        <form method="get" action="/main_search" onsubmit="return keyword()">
	        <input type="text" name="search" id="search" placeholder="어디로 떠나실건가요?:)" maxlength="20">
	        <!-- search icon -->
	        <label for="submit_btn">
		        <svg width="23" height="22" viewBox="0 0 23 22" fill="none" xmlns="http://www.w3.org/2000/svg">
		            <circle cx="10.1102" cy="9.396" r="7.896" stroke="#B3B3B3" stroke-width="3" />
		            <path d="M16.0896 15.8757L20.7143 20.5005" stroke="#B3B3B3" stroke-width="3" stroke-linecap="round" />
		        </svg>
	        </label>
	        <input type="submit" id="submit_btn">
        </form>
    </div>
    <!-- search box end //-->

    <!--// contents start -->
    <div class="contents">

        <article class="contents_container">

            <div class="content_left" style="background-image:url(../resources/upload/${attrimg.attr_img1})">
                <!--div는 block.WRAP이라고생각하기-->
                <a href="member/attr?city_name=${attrimg.attr_city }&latitude=${attrimg.city_latitude }&longitude=${attrimg.city_longitude }&attr_code=${attrimg.attr_code}" class="content_text">
                    <span>전국 유명 여행지</span><br>
                    <span>22년 베스트픽 여행지들을 한눈에 확인해요!</span>
                    <span>${attrTotalCount}</span>
                    <span>여개의 여행지들을<br>둘러보실수 있어요 :)</span>
                    <p>보러가기!</p>
                </a>
            </div>
            <div class="content_middle">
            <c:forEach var="item" items="${photoimg}" begin="0" end="0">
                <div class="cont_mid_top" style="background-image:url(../resources/upload/event/${item.board_img})">
                    <a href="photo_event_detail?photo_no=${item.board_no}">
                    	<span>22년 11월 베스트 샷</span><br>
                    	<span>${item.board_writer}</span>
                	</a>
                </div>
            </c:forEach>
                <div class="cont_mid_bottom">
                    <ul class="notice_board_view">
                        <h1 class="notice_board_title" style="cursor:pointer;" onclick="location.href='/board_list'">
			                            기억하자 <span>NEWS!!</span></h1>
                        <div class="line"></div>
                        <c:forEach var="list" items="${blist}">
                        	<li>
                        	<a href="/board_cont?no=${list.board_no}&page=1">
                        		<c:if test="${list.board_type eq '1'}">
                        			[공지사항]
                        		</c:if>
                        		<c:if test="${list.board_type eq '2'}">
                        			[이벤트]
                        		</c:if>
                        		${list.board_title}
                        		</a>
                        	</li>
                        </c:forEach>
                    </ul>

                    <div class="notice_view_more">
                        <input type="button" class="view_more" value="더 많은 소식 확인하기" onclick="location.href='/board_list';">
                        <!-- view more icon -->
                        <svg width="9" height="5" viewBox="0 0 9 5" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1 1L4.5 4.5L8 1" stroke="#333333" stroke-linecap="round"
                                stroke-linejoin="round" />
                        </svg>
                    </div>


                </div>
            </div>

            <div class="content_right">
            <c:forEach var="item" items="${attrlike}" begin="0" end="4" step="1">
                <a href="/member/attr?city_name=${item.attr_city }&latitude=${item.city_latitude }&longitude=${item.city_longitude }&attr_code=${item.attr_code}" 
                class="item" style="background-image:url(../resources/upload/${item.attr_photo})">${item.attr_name}</a>
			</c:forEach>
            </div>

        </article>
    </div>
    <!-- contents end //-->
<script src="../resources/JS/index.js"></script>

<!-- ***** footer ***** -->

<jsp:include page="./static/footer.jsp" />