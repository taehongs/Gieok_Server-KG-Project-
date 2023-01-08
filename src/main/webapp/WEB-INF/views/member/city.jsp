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

<link type="text/css" rel="stylesheet" href="../resources/CSS/member/city.css">

	<!-- 관광명소 -->
	<div class="attraction_default">
		<!-- 관광 명소 container -->
		<div class="attraction_container">
			<c:if test="${(sessionScope.grade == 'm' || empty sessionScope.grade)  }">
				<!-- 회원용 리모컨 -->
				<div class="attr_remotectr_con">
					<p class="attr_remotectr_name wf-gangwonTT">${province_name }</p>
					<ul class="attr_remotectr_list">
						<li class="attr_remotectr_item"><a href="/member/map" class="attr_remotectr_menu">
							지도보기
						</a></li>
					</ul>
				</div>
			</c:if>
			<!-- 관광 명소 지도 -->
			<div class="attr_map_container">
				<input type="hidden" value="${province }" id="province">
				<input type="hidden" value="${latitude }" id="latitude">
				<input type="hidden" value="${longitude }" id="longitude">
				<div id="map" class="attr_map"></div>
				<div class="attr_card_con">
					<ul class="attr_card_list">
						<c:forEach var="attr" items="${attr_list }">
							<input type="hidden" value="${attr.attr_addr }" class="attr_pin">
							<li class="attr_card_item">
								<a href="/member/attr?city_name=${attr.attr_city}
								&latitude=${latitude}&longitude=${longitude}&attr_code=${attr.attr_code }">
									<img src="/resources/upload/${attr.attr_img1}" class="attr_card_img">
									<p class="attr_card_name">${attr.attr_name }</p>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>

<!-- 지도 api -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3ca3d095c5fa4364799bef0568bbc89e&libraries=services"></script>
<script type="text/javascript" src="../resources/JS/member/city.js"></script>

<!-- ***** footer ***** -->
<jsp:include page="../static/footer.jsp" />