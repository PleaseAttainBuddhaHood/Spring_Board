<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<%@ include file="../common/header.jsp" %>


<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">게시물 등록</h1>
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
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li
{
	list-style: none;
	padding: 10px;
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

.bigPicture img
{
	width: 600px;
}

</style>
	
	

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                		게시물 등록
            </div>
            
 <div class="panel-body">
 
 	<form role="form" action="/board/register" method="post">
 		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

 		<div class="form-group">
 			<label>제목</label>
 			<input class="form-control" name="title">
 		</div>
 		
 		<div class="form-group">
 			<label>내용</label>
 			<textarea class="form-control" rows="3" name="content"></textarea>
 		</div>
 		
 		<div class="form-group">
 			<label>작성자</label>
 			<input class="form-control" name="writer" value='<sec:authentication property="principal.username" />' readonly>
 		</div>
 		
 		<button type="submit" class="btn btn-default">등록</button>
 		<button type="reset" class="btn btn-default">초기화</button>
 	</form>
 	
   </div>
  </div>
 </div>
</div>


<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">첨부 파일</div>
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


            
            
<script>
$(document).ready(function(e)
{
	let formObj = $("form[role='form']");

	$("button[type='submit']").on("click", function(e)
	{
		e.preventDefault();

		let str = "";
		let title = $("input[name='title']");
		let content = $("input[name='content']");


		if(title.value === null || content.value === null)
		{
			alert("제목과 내용을 입력해주세요.");
			return;
		}


		$(".uploadResult ul li").each(function(i, obj)
		{
			let jobj = $(obj);

			str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
			str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
			str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
			str += "<input type='hidden' name='attachList[" + i + "].imageType' value='" + jobj.data("type") + "'>";
		});

		formObj.append(str).submit();
	});


	// 파일 업로드 시
	let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	let maxSize = 5242880; // 5MB

	
	function checkExtension(fileName, fileSize)
	{
		if(fileSize >= maxSize)
		{
			alert("파일 사이즈 초과");
			return false;
		}


		if(regex.test(fileName))
		{
			alert("해당 종류의 파일은 업로드 불가");
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
			beforeSend: function(xhr)
			{
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			data: formData,
			type: 'post',
			dataType: 'json',
			success: function(result)
			{
				console.log(result);
				showUploadResult(result);
			}
		});

	});


	// 업로드된 결과를 화면에 썸네일 등을 만들어 처리
	function showUploadResult(uploadResultArr)
	{
		if(!uploadResultArr || uploadResultArr.length == 0)
		{
			return;
		}


		let uploadUL = $(".uploadResult ul");
		let str = "";

		$(uploadResultArr).each(function(i, obj)
		{
			if(obj.image)
			{
				let fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);

				str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' ";
				str += "data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>";
				str += "<div>";
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
				str += "data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>";
				str += "<div>";
				str += "<span>" + obj.fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' "; 
				str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
				str += "<a><img src='/resources/img/attach.png'></a>";
				str += "</div></li>";
			}
		});

		uploadUL.append(str);
	}


	
	$(".uploadResult").on("click", "button", function(e)
	{
		console.log("파일 삭제");

		let targetFile = $(this).data("file");
		let type = $(this).data("type");
		let targetLi = $(this).closest("li");


		$.ajax
		({
			url: '/deleteFile',
			data: {fileName:targetFile, type:type},
			beforeSend: function(xhr)
			{
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			dataType: 'text',
			type: 'post',
			success: function(result)
			{
				targetLi.remove();
			}
		});

	});

});
</script>

<%@ include file="../common/footer.jsp" %>
