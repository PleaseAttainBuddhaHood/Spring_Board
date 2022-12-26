<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="../common/header.jsp" %>


<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">게시글 수정</h1>
    </div>
</div>


<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                		게시글 수정 페이지
            </div>
 <div class="panel-body">
 
 <form role="form" action="/board/modify" method="post">
 
 	<input type="hidden" name="currentPage" value='<c:out value="${ph.currentPage}" />'>
 	<input type="hidden" name="pageRowSize" value='<c:out value="${ph.pageRowSize}" />'>
 	<input type="hidden" name="searchType" value='<c:out value="${ph.searchType}" />'>
 	<input type="hidden" name="searchKeyword" value='<c:out value="${ph.searchKeyword}" />'>
 
	 <div class="form-group">
	 	<label>글 번호</label>
	 	<input class="form-control" name="bno" value='<c:out value="${board.bno}" />' readonly>
	 </div>
 	
 	 <div class="form-group">
	 	<label>제목</label>
	 	<input class="form-control" name="title" value='<c:out value="${board.title}" />'>
	 </div>
	 
	  <div class="form-group">
	 	<label>내용</label>
	 	<textarea class="form-control" row="3" name="content"><c:out value="${board.content}" />
	    </textarea>
	 </div>
	 
	  <div class="form-group">
	 	<label>작성자</label>
	 	<input class="form-control" name="writer" value='<c:out value="${board.writer}" />' readonly>
	 </div>
	 
	  <div class="form-group">
	 	<label>등록일</label>
	 	<input class="form-control" name="regDate" value='<fmt:formatDate pattern = "yyyy/MM/dd"
	 		   value="${board.regdate}" />' readonly>
	  </div>
	  
  	  <div class="form-group">
	 	<label>수정일</label>
	 	<input class="form-control" name="updateDate" value='<fmt:formatDate pattern = "yyyy/MM/dd"
	 		   value="${board.updateDate}" />' readonly>
	  </div>
	 
	 <button type="submit" data-oper="modify" class="btn btn-default">완료</button>
	 <button type="submit" data-oper="list"   class="btn btn-info">목록</button>
 </form>
   </div>
  </div>
 </div>
</div>

<%@ include file="../common/footer.jsp" %>
            

<script type="text/javascript">
$(document).ready(function()
{
	let formObj = $("form");
	
	
	$("button").on("click", function(e)
	{
		e.preventDefault();
		
		let operation = $(this).data("oper");
		
		console.log(operation);
		
		
		if(operation === 'list')
		{
			let currentPageTag = $("input[name='currentPage']").clone();
			let pageRowSizeTag = $("input[name='pageRowSize']").clone();
			let searchTypeTag = $("input[name='searchType']").clone();
			let searchKeywordTag = $("input[name='searchKeyword']").clone();
			
			formObj.attr("action", "/board/list").attr("method", "get");
			formObj.empty();
			
			formObj.append(currentPageTag);
			formObj.append(pageRowSizeTag);
			formObj.append(searchTypeTag);
			formObj.append(searchKeywordTag);
		}
		
		formObj.submit();
	});
	
})
</script>
            