let replyService = (function()
{
	
	// 댓글 추가
	function add(reply, callback, error)
	{
		console.log("댓글 추가");
		
		$.ajax
		({
			type: 'post',
			url: '/replies/new',
			data: JSON.stringify(reply),
			contentType: "application/json;charset=utf-8",
			success: function(result, status, xhr)
			{
				if(callback)
				{
					callback(result);
				}
			},
			error: function(xhr, status, er)
			{
				if(error)
				{
					error(er);
				}
			}
		});
	}
	
	
	
	// 특정 댓글 조회
	function get(rno, callback, error)
	{
		$.get("/replies/" + rno + ".json", function(result)
		{
			if(callback)
			{
				callback(result);
			}
		}).fail(function(xhr, status, err)
		{
			if(error)
			{
				error();
			}
		});
	}
	
	
	// 특정 게시물의 댓글 전체 조회
	function getList(param, callback, error)
	{
		let bno = param.bno;
		let page = param.page || 1;
		
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json", 
		function(data)
		{
			if(callback)
			{
				callback(data.replyCnt, data.list);
			}
		}).fail(
		function(xhr, status, err)
		{
			if(error)
			{
				error();
			}
		});
	}
	
	
	
	// 댓글 수정
	function update(reply, callback, error)
	{
		console.log("댓글 번호 : " + reply.rno);
		
		$.ajax
		({
			type: 'put',
			url: '/replies/' + reply.rno,
			data: JSON.stringify(reply),
			contentType: "application/json;charset=utf-8",
			success: function(result, status, xhr)
			{
				if(callback)
				{
					callback(result);
				}
			},
			error: function(xhr, status, er)
			{
				if(error)
				{
					error(er);
				}
			}
		});
	}

	
	
	// 댓글 삭제
	function remove(rno, replier, callback, error)
	{
		$.ajax
		({
			type: 'delete',
			url: '/replies/' + rno,
			data: JSON.stringify({rno:rno, replier:replier}),
			contentType: "application/json; charset=utf-8",
			success: function(deleteResult, status, xhr)
			{
				if(callback)
				{
					callback(deleteResult);
				}
			},
			error: function(xhr, status, er)
			{
				if(error)
				{
					error(er);
				}
			}
		});
	}
	
	
	
	// 오늘 등록된 댓글은 '시/분/초'로 표시, 그 이전 등록일은 '연/월/일'로 표시
	function displayTime(timeValue)
	{
		let today = new Date();
		let gap = today.getTime() - timeValue;
		let dateObj = new Date(timeValue);
		let str = "";

		if(gap < (1000 * 60 * 60 * 24))
		{
			let hh = dateObj.getHours();
			let mi = dateObj.getMinutes();
			let ss = dateObj.getSeconds();

			return [(hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss].join('');
		}
		else
		{
			let yy = dateObj.getFullYear();
			let mm = dateObj.getMonth() + 1;
			let dd = dateObj.getDate();

			return [yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd].join('');
		}
	}
	
	return {
		add: add,
		get: get,
		getList: getList,
		remove: remove,
		update: update,
		displayTime: displayTime
	};
})();