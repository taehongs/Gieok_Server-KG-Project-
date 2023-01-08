<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../static/header.jsp" />

<!--// summernote -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

    <script src="../resources/summernote/JS/summernote-ko-KR.js"></script>
    <script src="../resources/summernote/JS/summernote-lite.js"></script>
    <link rel="stylesheet" href="../resources/summernote/CSS/summernote-lite.css">
<!-- summernote //-->


<c:if test="${(sessionScope.grade == 'a' || sessionScope.grade=='s')}">
	<jsp:include page="../static/sidebar.jsp" />
</c:if>
<c:if test="${sessionScope.grade == 'm'  }">
	<!-- 회원용 리모컨 -->
</c:if>

<link rel="stylesheet" href="../resources/CSS/static/header.css">
<link rel="stylesheet" href="../resources/CSS/admin/board_write.css">

<div class="content">	
	<!-- contents: board list box -->
	<form method="post"  name="board_notice" class="board_notice" action="https://gieok.icu/admin/board_edit_ok" enctype="multipart/form-data" onclick="doubleSubmitCheck()"><%-- ${b } --%>
	    <input type="hidden" name="no" value=${no }>
	    <input type="hidden" name="page" value=${page }>
	    <div class="board_container">
	        <h1>공지사항 등록</h1>
	    </div>
	    <div class="board_title">
	        <select name="board_type" id="board_type">
	            <c:if test="${blist.board_type==1}">
	            <option value="notice" selected>공지사항</option>
	            </c:if>
	            <c:if test="${blist.board_type==2}">
	            <option value="event" selected>이벤트</option>
	            </c:if>
	        </select>
	        <input type="text" name="board_title" id="board_title" maxlength="30" placeholder="글 제목을 작성해 주세요:)"  value="${blist.board_title}" required>
	    </div>
	    <div class="board_middle">
	        <textarea name="board_content" class="board_content" id="summernote" placeholder="글 내용을 작성해 주세요:)" required>${blist.board_content}</textarea>
	        <div class="board_date_img">
	            <div class="board_date">
	                <p>기간선택(시작 ~ 종료)</p>
	                <p><input type="date" id="start_date" name="start_date" value="${fn:substring(blist.board_startDay,0,10)}"></p>
	                <p><input type="date" id="end_date" name="end_date" value="${fn:substring(blist.board_endDay,0,10)}"></p>
	            </div>
	            <c:if test="${blist.board_type==2}">
	             <div class="board_img">
	                 <p>미리보기</p>
	                 <p>250px * 400px</p>
	                 <img src="../resources/upload/event/${fn:substring(blist.board_regDate,0,10)}/${blist.board_img1}" alt="" id="upload_img1" width="100px" height="160px">
	                 <label for="file1">배너 등록</label>
	                 <input type="file" id="file1" name="imgfile1" onchange="previewImage1()">
	                 <p>280px * 150px</p>
	                 <img src="../resources/upload/event/${fn:substring(blist.board_regDate,0,10)}/${blist.board_img2}" alt="" id="upload_img2" width="100px" height="53px">
	                 <label for="file2">배너 등록</label>
	                 <input type="file" id="file2" name="imgfile2" onchange="previewImage2()">
	             </div>
	            </c:if>
	        </div>
	    </div>
	    <div class="board_button">
	        <input type="submit"  value="저장" onclick="board_write_submit()">
	        <input type="button" value="취소" onclick="history.back();">
	      </div>
	  </form>
	</div>
	<!-- container section end //-->
	    <!-- // summernote -->
<script>
	$(document).ready(
		function() {
			var toolbar = [
				// [groupName, [list of button]]
				[ 'fontNames', [ 'fontname' ] ],
				[ 'fontSizes', [ 'fontsize' ] ],
				[ 'font', [ 'bold', 'italic', 'underline', 'strikethrough' ] ],
				[ 'color', [ 'forecolor', 'color' ] ],
				[ 'para', [ 'paragraph' ] ],
				[ 'height', [ 'height' ] ], ];

			var setting = {
				height : 450,
				focus : false,
				lang : "ko-KR",
				toolbar : toolbar,
				placeholder : '글 제목을 작성해 주세요:)',
				fontNames : [ 'GangwonEduPowerExtraBoldA', 'Arial',
						'Arial Black', 'Comic Sans MS', 'Courier New',
						'맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체' ],
				fontSizes : [ '8', '9', '10', '11', '12', '14', '16', '18',
						'20', '22', '24', '28', '30', '36', '50', '72' ]
			}

			$('#summernote').summernote(setting);
		});

	var doubleSubmit = false;
	function doubleSubmitCheck() {
		if (doubleSubmit) {
			return doubleSubmit;
		} else {
			doubleSubmit = true;
			return false;
		}
	}
</script>
<!-- summernote // -->
    
<script src="../resources/JS/admin/board_edit.js"></script>
<jsp:include page="../static/footer.jsp"/>   