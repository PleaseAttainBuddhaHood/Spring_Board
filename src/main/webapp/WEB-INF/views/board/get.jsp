<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@ include file="../common/header.jsp" %>


<style>
.uploadResult
{
	width: 100%;
	background-color: gray;
}

.uploadResult ul
{
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li
{
	list-style: none;
	padding: 10px;
	align-content: center;
	text-align: center;
}

.uploadResult ul li img
{
	width: 100px;
}

.uploadResult ul li span
{
	color: white;
}

.bigPictureWrapper
{
	position: absolute;
	display: none;
	justify-content: center;
	align-items: center;
	top: 0%;
	width: 100%;
	height: 100%;
	background-color: gray;
	z-index: 100;
	background: rgba(255, 255, 255, 0.5);
}

.bigPicture
{
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img
{
	width: 600px;
}

</style>


<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">게시글 읽기</h1>
    </div>
</div>


<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                		게시물 조회
            </div>
            
            
 <div class="panel-body">
 
	 <div class="form-group">
	 	<label>글 번호</label>
	 	<input class="form-control" name="bno" value='<c:out value="${board.bno}" />' readonly>
	 </div>
 	
 	 <div class="form-group">
	 	<label>제목</label>
	 	<input class="form-control" name="title" value='<c:out value="${board.title}" />' readonly>
	 </div>
	 
	  <div class="form-group">
	 	<label>내용</label>
	 	<textarea class="form-control" row="3" name="content" readonly><c:out value="${board.content}" />
	    </textarea>
	 </div>
	 
	  <div class="form-group">
	 	<label>작성자</label>
	 	<input class="form-control" name="writer" value='<c:out value="${board.writer}" />' readonly>
	 </div>
	
	<sec:authentication property="principal" var="pinfo" />
	
		<sec:authorize access="isAuthenticated()">
			<c:if test="${pinfo.username eq board.writer}">
			 <button type="submit" data-oper="modify" class="btn btn-default">수정</button>
			 <button type="submit" data-oper="remove" class="btn btn-danger">삭제</button>
		 	</c:if>
	 	</sec:authorize> 
	

	 		 <button type="submit" data-oper="list" class="btn btn-info">목록</button>
	 
	 
	 <form id="operForm" action="/board/modify" method="get">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	 
	 	<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno}" />'>
	 	<input type="hidden" name="currentPage" value='<c:out value="${ph.currentPage}" />'>
	 	<input type="hidden" name="pageRowSize" value='<c:out value="${ph.pageRowSize}" />'>
	 	<input type="hidden" name="searchType" value='<c:out value="${ph.searchType}" />'>
	 	<input type="hidden" name="searchKeyword" value='<c:out value="${ph.searchKeyword}" />'>
	 </form>
 
   </div>
  </div>
 </div>
</div>


<!-- 첨부파일 원본 이미지 표시-->
<div class="bigPictureWrapper">
	<div class="bigPicture">

	</div>
</div>

<!-- 첨부파일 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">첨부 파일</div>
			<div class="panel-body">
				<div class="uploadResult">
					<ul>

					</ul>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 댓글 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> 댓글
				
				<sec:authorize access="isAuthenticated()">
					<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">댓글 작성</button>
				</sec:authorize>
				
			</div>

			<div class="panel-body">
				<ul class="chat"></ul>
			</div>

			<div class="panel-footer">

			</div>

		</div>
	</div>
</div>




<!-- 댓글 등록 모달창 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
   			<div class="modal-header">
	   			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		   			&times;
	   			</button>
	   
	   			<h4 class="modal-title" id="myModalLabel">댓글 확인</h4>
   			</div>
   
			<div class="modal-body">
				<div class="form-group">
					<label>댓글</label>
					<input class="form-control" name="reply" value="새로운 댓글">
				</div>

				<div class="form-group">
					<label>작성자</label>
					<input class="form-control" name="replier" value="댓글러" readonly>
				</div>
				
				<div class="form-group">
					<label>작성일</label>
					<input class="form-control" name="replyDate" value="">
				</div>
			</div>

			<div class="modal-footer">
				<button id="modalModBtn" type="button" class="btn btn-warning">저장</button>
				<button id="modalRemoveBtn" type="button" class="btn btn-danger">삭제</button>
				<button id="modalRegisterBtn" type="button" class="btn btn-primary">등록</button>
				<button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
			
		</div>
	</div>
</div>	 

            
<script type="text/javascript" src="/resources/js/reply.js"></script>            


<!-- 첨부파일 관련 -->
<script>
$(document).ready(function()
{
	(function()
	{
		let bno = '<c:out value="${board.bno}" />';
	
		
		$.getJSON("/board/getAttachList", {bno: bno}, function(arr)
		{
			console.log(arr);

			let str = "";

			$(arr).each(function(i, attach)
			{
				if(attach.imageType)
				{
					let fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
					
					str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid 
								+ "' data-filename='" + attach.fileName + "' data-type='" + attach.imageType + "'><div>";
					str += "<img src='/display?fileName=" + fileCallPath + "'>";
					str += "</div></li>";
				}
				else
				{
					str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid 
								+ "' data-filename='" + attach.fileName + "' data-type='" + attach.imageType + "'><div>";
					str += "<span>" + attach.fileName + "</span><br>";
					str += "<img src='/resources/img/attach.png'>";
					str += "</div></li>";				
				}
			});

			$(".uploadResult ul").html(str);
		});
	})();


	
	$(".uploadResult").on("click", "li", function(e)
	{
		console.log("첨부파일 클릭");
	
		let liObj = $(this);
		let path = encodeURIComponent(liObj.data("path") + "/" + liObj.data("uuid") + "_" + liObj.data("filename"));
	
		if(liObj.data("type"))
		{
			// 파일 경로는 함수로 전달될 때 문제가 생기므로, replace()를 이용하여 변환 후 전달
			showImage(path.replace(new RegExp(/\\/g), "/"));
		}
		else
		{
			self.location = "/download?fileName=" + path;
		}
	});


	function showImage(fileCallPath)
	{
		$(".bigPictureWrapper").css("display", "flex").show();
	
		$(".bigPicture")
		.html("<img src='/display?fileName=" + fileCallPath + "'>")
		.animate({width: '100%', height: '100%'}, 1000);
	}
	

	// 원본 이미지 창 닫기
	$(".bigPictureWrapper").on("click", function(e)
	{
		$(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
	
		setTimeout(function()
		{
			$('.bigPictureWrapper').hide();
		});
	});
});
</script>



<!-- 댓글 관련 -->
<script>
$(document).ready(function()
{
	let bnoValue = '<c:out value="${board.bno}" />';
	let replyUL = $(".chat");

	showList(1);


	function showList(page)
	{
		replyService.getList({bno:bnoValue, page:page || 1}, function(replyCnt, list)
		{
			if(page == -1)
			{
				currentPage = Math.ceil(replyCnt / 10.0);
				showList(currentPage);
				return;
			}


			let str = "";

			
			if(list == null || list.length == 0)
			{
				return;
			}

			
			for(let i = 0, len = list.length || 0; i < len; i++)
			{
				str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
				str += "	<div><div class='header'><strong class='primary-font'>[작성자] " + list[i].replier + "</strong>";
				str += "		<small class='pull-right text-muted'>" + replyService.displayTime(list[i].replyDate) + "</small></div>";
				str += "		<p>" + list[i].reply + "</p></div></li>";
			}

			replyUL.html(str);

			showReplyPage(replyCnt);
		});
	}
	

	// 댓글 모달창 처리
	let modal = $(".modal");
	
	let modalInputReply = modal.find("input[name='reply']");
	let modalInputReplier = modal.find("input[name='replier']");
	let modalInputReplyDate = modal.find("input[name='replyDate']");
	
	let modalModBtn = $("#modalModBtn");
	let modalRemoveBtn = $("#modalRemoveBtn");
	let modalRegisterBtn = $("#modalRegisterBtn");

	
	let replier = null;
	
	
	<sec:authorize access="isAuthenticated()">
		loginId = '<sec:authentication property="principal.username" />';
	</sec:authorize>
	
	
	let csrfHeaderName = "${_csrf.headerName}";
	let csrfTokenValue="${_csrf.token}";

	
	$("#addReplyBtn").on("click", function(e)
	{
		modal.find("input").val("");
		modal.find("input[name='replier']").val(loginId);
		modalInputReplyDate.closest("div").hide();
		
		modal.find("button[id != 'modalCloseBtn']").hide();
		modalRegisterBtn.show();

		$(".modal").modal("show");
	});
	
	
	$(document).ajaxSend(function(e, xhr, options)
	{
		xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
	});


	// 모달창 - 댓글 등록 버튼의 처리
	modalRegisterBtn.on("click", function(e)
	{
		let reply = 
		{
			reply: modalInputReply.val(),
			replier: modalInputReplier.val(),
			bno: bnoValue
		};

		replyService.add(reply, function(result)
		{
			modal.find("input").val("");
			modal.modal("hide");

			showList(-1);
		});
	});

	
	// 특정 댓글의 클릭 이벤트. 이벤트 동적 위임
	$(".chat").on("click", "li", function(e)
	{
		let rno = $(this).data("rno");
		let originalReplier = modalInputReplier.val();
		
		replyService.get(rno, function(reply)
		{
			// '댓글 작성자의 아이디'가 '로그인한 사용자'와 같으면 '수정', '삭제' 버튼 보여주기
			if(reply.replier == loginId)
			{
				modalInputReply.val(reply.reply);
				modalInputReplier.val(reply.replier);
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");

				modal.data("rno", reply.rno);

				modal.find("button[id != 'modalCloseBtn']").hide();
				modalInputReplyDate.show();
				modalModBtn.show();
				modalRemoveBtn.show();
			}
			else
			{
				modalInputReply.val(reply.reply).attr("readonly", "readonly");
				modalInputReplier.val(reply.replier);
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
				
				modal.data("rno", reply.rno);
				
				modal.find("button[id != 'modalCloseBtn']").hide();
			}

			$(".modal").modal("show");
		});
	});


	// 댓글 수정
	modalModBtn.on("click", function(e)
	{
		let originalReplier = modalInputReplier.val();
		
		let reply = 
		{
			rno: modal.data("rno"), 
			reply: modalInputReply.val(),
			replier: originalReplier
		};

		replyService.update(reply, function(result)
		{
			alert("댓글 수정 완료");
			modal.modal("hide");
			showList(currentPage);
		});

	});


	// 댓글 삭제
	modalRemoveBtn.on("click", function(e)
	{
		let rno = modal.data("rno");
		let originalReplier = modalInputReplier.val();

		console.log("loginId : " + loginId);
		
		if(confirm("댓글을 삭제하시겠습니까?"))
		{
			replyService.remove(rno, originalReplier, function(result)
			{
				alert("댓글 삭제 완료");
				modal.modal("hide");
				showList(currentPage);
			});
		}
		else
		{
			modal.modal("hide");
			return;		
		}
	});


	// 댓글 페이지 번호 출력
	let currentPage = 1;
	let replyPageFooter = $(".panel-footer");

	
	function showReplyPage(replyCnt)
	{
		let endNum = Math.ceil(currentPage / 10.0) * 10;
		let startNum = endNum - 9;

		let prev = startNum != 1;
		let next = false;

		let str = "<ul class='pagination pull-right'>";


		if(endNum * 10 < replyCnt)
		{
			next = true;
		}
		else
		{
			endNum = Math.ceil(replyCnt / 10.0);
		}

		if(prev)
		{
			str += "<li class='page-item'><a class='page-link' href='" + (startNum - 1) + "'>이전</a></li>";
		}

		for(let i = startNum; i <= endNum; i++)
		{
			let active = currentPage == i ? "active" : "";

			str += "<li class='page-item " + active + " '><a class='page-link' href='" + i + "'>" + i + "</a></li>";
		}

		if(next)
		{
			str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) + "'>다음</a></li>";
		}

		str += "</ul></div>";

		replyPageFooter.html(str);
	}


	// 페이지 번호 클릭 시, 새로운 댓글을 가져오기
	replyPageFooter.on("click", "li a", function(e)
	{
		e.preventDefault();
		
		let targetCurrentPage = $(this).attr("href");
		currentPage = targetCurrentPage;

		showList(currentPage);
	});
	
});
</script>



<script type="text/javascript">
$(document).ready(function()
{
	let operForm = $("#operForm");


	// 목록 버튼
	$("button[data-oper='list']").on("click", function(e)
	{
		operForm.find("#bno").remove();
		operForm.attr("action", "/board/list");
		operForm.submit();
	});
	
	
	// 수정 버튼
	$("button[data-oper='modify']").on("click", function(e)
	{
		operForm.attr("action", "/board/modify").submit();
	});

	
	// 삭제 버튼
	$("button[data-oper='remove']").on("click", function(e)
	{
		e.preventDefault();
		
		
		if(confirm("글을 삭제하시겠습니까?"))
		{
			operForm.attr("action", "/board/remove");
			operForm.attr("method", "post");
			operForm.submit();		
		}
		else
		{
			return;	
		}
		
	});
	
});
</script>
            

<%@ include file="../common/footer.jsp" %>
