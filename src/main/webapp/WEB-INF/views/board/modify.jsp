<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

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
 
   	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 
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
	 	<textarea class="form-control" row="3" name="content"><c:out value="${board.content}" /></textarea>
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



<div class="bigPictureWrapper">
	<div class="bigPicture">

	</div>
</div>



<style>
.uploadResult
{
	width: 100%;
	background-color: gray;
}

.uploadResult ul
{
  display:flex;
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
  color:white;
}

.bigPictureWrapper 
{
  position: absolute;
  display: none;
  justify-content: center;
  align-items: center;
  top:0%;
  width:100%;
  height:100%;
  background-color: gray; 
  z-index: 100;
  background:rgba(255,255,255,0.5);
}

.bigPicture 
{
  position: relative;
  display:flex;
  justify-content: center;
  align-items: center;
}

.bigPicture img 
{
  width:600px;
}

</style>


<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">파일 첨부</div>
			<div class="panel-body">

				<div class="form-group uploadDiv">
					<input type="file" name="uploadFile" multiple>
				</div>

				<div class="uploadResult">
					<ul>

					</ul>
				</div>
			</div>
		</div>
	</div>
</div>


<sec:authentication property="principal" />


<%@ include file="../common/footer.jsp" %>
            

<script type="text/javascript">
$(document).ready(function()
{
	let formObj = $("form");
	
	
	// 버튼 클릭 이벤트 (완료, 목록)
	$("button").on("click", function(e)
	{
		e.preventDefault();
		
		let operation = $(this).data("oper");
		
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
		else if(operation === 'modify')
		{
			let str = "";

			$(".uploadResult ul li").each(function(i, obj)
			{
				let jobj = $(obj);

				str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
				str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
				str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
				str += "<input type='hidden' name='attachList[" + i + "].imageType' value='" + jobj.data("type") + "'>";
			});

			formObj.append(str).submit();
		}
		
		formObj.submit();
	});
	
});
</script>



<!-- 첨부파일 보여주기 -->
<script>
$(document).ready(function()
{(
	function()
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
					let fileCallPath =  encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);

					str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' ";
					str += "data-filename='" + attach.fileName + "' data-type='" + attach.imageType + "' ><div>";
					str += "<span> " + attach.fileName + "</span>";
					str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' ";
					str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
					str += "<img src='/display?fileName=" + fileCallPath + "'>";
					str += "</div></li>";
				}
				else
				{
					let fileCallPath =  encodeURIComponent(attach.uploadPath + "/" + attach.uuid + "_" + attach.fileName);

					str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' ";
					str += "data-filename='" + attach.fileName + "' data-type='" + attach.imageType + "' ><div>";
					str += "<span> " + attach.fileName + "</span><br/>";
					str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' ";
					str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
					str += "<a href='#'><img src='/resources/img/attach.png'></a>";
					str += "</div></li>";
				}
			});


			$(".uploadResult ul").html(str);
		});
	}
)();
});


$(".uploadResult").on("click", "button", function(e)
{
	console.log("파일 삭제");

	if(confirm("이 파일을 삭제하시겠습니까?"))
	{
		let targetLi = $(this).closest("li");
		targetLi.remove();
	}
});


let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
let maxSize = 5242880; // 5MB


function checkExtension(fileName, fileSize)
{
	if(fileSize > maxSize)
	{
		alert("파일 크기 초과");
		return false;
	}


	if(regex.test(fileName))
	{
		alert("해당 종류의 파일은 업로드할 수 없습니다.");
		return false;
	}

	return true;
}


let csrfHeaderName = "${_csrf.headerName}";
let csrfTokenValue = "${_csrf.token}";


$("input[type='file']").change(function(e)
{
	let formData = new FormData();
	let inputFile = $("input[name='uploadFile']");
	let files = inputFile[0].files;
	

	for(let i = 0; i < files.length; i++)
	{
		if(!checkExtension(files[i].name, files[i].size))
		{
			return false;
		}

		formData.append("uploadFile", files[i]);
	}

	
	$.ajax
	({
		url: '/uploadAjaxAction',
		processData: false,
		contentType: false,
		data: formData, 
		type: 'post',
		beforeSend: function(xhr)
		{
			xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		},
		dataType: 'json',
		success: function(result)
		{
			console.log(result);
			showUploadResult(result);
		}
	});
});



function showUploadResult(uploadResultArr)
{
	let uploadUL = $(".uploadResult ul");
	let str = "";

	if(!uploadResultArr || uploadResultArr.length == 0)
	{
		return;
	}


	$(uploadResultArr).each(function(i, obj)
	{
		if(obj.image)
		{
			let fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);

			str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' ";
			str += "data-filename='" + obj.fileName + "' data-type='" + obj.image + "'><div>";
			str += "<span>" + obj.fileName + "</span>";
			str += "<button type='button' data-file=\'" + fileCallPath + "\' ";
			str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
			str += "<img src='/display?fileName=" + fileCallPath + "'>";
			str += "</div></li>";
		}
		else
		{
			let fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
			let fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");

			str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' ";
			str += "data-filename='" + obj.fileName + "' data-type='" + obj.image + "'><div>";
			str += "<span>" + obj.fileName + "</span>";
			str += "<button type='button' data-file=\'" + fileCallPath + "\' ";
			str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
			str += "<a href='#'><img src='/resources/img/attach.png'></a>";
			str += "</div></li>";
		}
	});

	uploadUL.append(str);
}

</script>
            