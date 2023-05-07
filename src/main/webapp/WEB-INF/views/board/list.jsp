<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%@ include file="../common/header.jsp" %>


<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">게시판</h1>
    </div>
</div>


<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                		게시글 목록
            </div>

 <div class="panel-body">
     <table class="table table-striped table-bordered table-hover">
         <thead>
             <tr>
                 <th>#번호</th>
                 <th>제목</th>
                 <th>작성자</th>
                 <th>작성일</th>
                 <th>수정일</th>
             </tr>
         </thead>
         
         
         <c:forEach items="${list}" var="board">
         	<tr>
         		<td><c:out value="${board.bno}" /></td>
         		<td>
         			<a class="move" href='<c:out value="${board.bno}" />'>
         				<c:out value="${board.title}" />
         				<b>[<c:out value="${board.replyCnt}" />]</b>  
       				</a>
       			</td>
         		<td><c:out value="${board.writer}" /></td>
         		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}" /></td>
         		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}" /></td>
         	</tr>
         </c:forEach>
      </table>
            
            
            
       <!-- 검색 -->
       <div class="row">
       	<div class="col-lg-12">
       		
       		<button id="regBtn" type="button" class="btn btn-success pull-right">글 쓰기</button>
       		
       		<form id="searchForm" action="/board/list" method="get">
       			<select name="searchType">
       				<option value="T" 
       					<c:out value="${pageMaker.ph.searchType eq 'T' ? 'selected' : ''}" />>제목</option>
       				<option value="C"
       					<c:out value="${pageMaker.ph.searchType eq 'C' ? 'selected' : ''}" />>내용</option>
       				<option value="W"
       					<c:out value="${pageMaker.ph.searchType eq 'W' ? 'selected' : ''}" />>작성자</option>
       				<option value="TC"
       					<c:out value="${pageMaker.ph.searchType eq 'TC' ? 'selected' : ''}" />>제목 + 내용</option>
       				<option value="TW"
       					<c:out value="${pageMaker.ph.searchType eq 'TW' ? 'selected' : ''}" />>제목 + 작성자</option>
       				<option value="TWC"
       					<c:out value="${pageMaker.ph.searchType eq 'TWC' ? 'selected' : ''}" />>제목 + 내용 + 작성자</option>
       			</select>
       			
       			<input type="text" name="searchKeyword" value='<c:out value="${pageMaker.ph.searchKeyword}"/>'>
       			<input type="hidden" name="currentPage" value='<c:out value="${pageMaker.ph.currentPage}"/>'>
       			<input type="hidden" name="pageRowSize" value='<c:out value="${pageMaker.ph.pageRowSize}"/>'>
       			
       			<button class="btn btn-default">검색</button>
       		</form>
       	</div>
       </div>
            
            
            
       <!-- 페이지 번호 출력 -->
       <div class="pull-right">
       
       	<ul class="pagination">
       	
       		<c:if test="${pageMaker.prev}">
       			<li class="paginate_button previous">
       			<a href="${pageMaker.startPage - 1}">이전</a></li>
       		</c:if>
       		
       		<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
       			<li class="paginate_button ${pageMaker.ph.currentPage == num ? 'active' : ''}">
       				<a href="${num}">${num}</a>
       			</li>	   
		    </c:forEach>
       		
       		<c:if test="${pageMaker.next}">
       			<li class="paginate_button next">
       				<a href="${pageMaker.endPage + 1}">다음</a>
   				</li>
       		</c:if>
       	</ul>
       </div>
            
            
       <!-- 페이지 이동 시 값 전달-->
       <form id="actionForm" action="/board/list" method="get">
       	 <input type="hidden" name="currentPage" value='${pageMaker.ph.currentPage}'>
       	 <input type="hidden" name="pageRowSize" value='${pageMaker.ph.pageRowSize}'>
       	 <input type="hidden" name="searchType" value='<c:out value="${pageMaker.ph.searchType}"/>'>
       	 <input type="hidden" name="searchKeyword" value='<c:out value="${pageMaker.ph.searchKeyword}"/>'>
       </form>
            
            
            
       <!-- 글 등록/삭제 후 모달창 출력 --> 
       <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
       	 	aria-labelledby="myModalLabel" aria-hidden="true">
       	<div class="modal-dialog">
       		<div class="modal-content">
       			<div class="modal-header">
       				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
       					&times;
       				</button>
       				
       				<h4 class="modal-title" id="myModalLabel">처리 결과</h4>
       			</div>
       			
       			<div class="modal-body">처리되었습니다.</div>
       			<div class="modal-footer">
       				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
       			</div>
       		</div>
       	</div>	 
  	 </div>
    </div>
   </div>
  </div>
</div>

            
            
<script type="text/javascript">
$(document).ready(function()
{
	// 게시물 번호. 일회용 데이터. 새로고침을 하거나 /board/list를 호출할 때는 아무 내용이 없음(도배 방지)
	let result = '<c:out value="${result}" />';
	
	checkModal(result);
	
	// window의 히스토리 객체를 통해 현재 페이지는 모달창을 띄울 필요가 없다고 표시함
	history.replaceState({}, null, null);
	
	
	// 글 등록 후 모달창
	function checkModal(result)
	{
		if(result === '' || history.state)
		{
			return;
		}
		
		if(parseInt(result) > 0)
		{
			$(".modal-body").html(parseInt(result) + "번 게시글이 등록되었습니다.");		
		}
		
		$("#myModal").modal("show");
	}
	
	
	
	// 새 글 쓰기 
	$("#regBtn").on("click", function()
	{
		self.location="/board/register";
	});
	
	
	let actionForm = $("#actionForm");
	
	
	
	$(".paginate_button a").on("click", function(e)
	{
		e.preventDefault();
		
		actionForm.find("input[name='currentPage']").val($(this).attr("href"));
		actionForm.attr("action", "/board/list");
		actionForm.submit();
	});
	
	
	
	$(".move").on("click", function(e)
	{
		e.preventDefault();
		
		actionForm.append("<input type='hidden' name='bno' value='" + $(this).attr("href") + "'>");
		actionForm.attr("action", "/board/get");
		actionForm.submit();
	});
	
	
	// 검색 버튼 이벤트 처리
	let searchForm = $("#searchForm");
	
	$("#searchForm button").on("click", function(e)
	{
		
		if(!searchForm.find("input[name='searchKeyword']").val())
		{
			alert("키워드를 입력하세요");
			return false;
		}
		
		
		searchForm.find("input[name='currentPage']").val("1");
		
		e.preventDefault();
		
		searchForm.submit();
	});
});
</script>
            

<%@ include file="../common/footer.jsp" %>
