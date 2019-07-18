;(function($,window,document,undefined){
    $.extend({
    	Constans:{
			RESPONSE_HEADER_NOTE:"header_note",
			RESPONSE_HEADER_ERROR:"header_error",
			RESPONSE_HEADER_JUMP:"header_jump"
		},
		
		/**************************************************************************
		 * 
		 * 处理分页
		 * 
		 *************************************************************************/ 
		_bindPager: function($searchPager) {
			// 添加分页样式
			var $this = this,
				currentPage = window.parseInt($searchPager.find("#page_current_page").text(),10),
				totalPages = window.parseInt($searchPager.find("#page_total_page").text(),10),
				totalElements = window.parseInt($searchPager.find("#page_total_element").text(),10);
			//当没有查询到数据或返回数据异常的时候，页面显示0条记录，1页，当前页在第1页。
			if(isNaN(totalPages) || totalPages < 2 ||isNaN(currentPage) || currentPage < 0 ||
					isNaN(totalElements) || totalElements <= 0){
				$("#page_pager", this.currentTarget).hide();
			}else{
				$("#page_pager", this.currentTarget)
					.each(function() {
					var $ul = $("<ul class='pagination m-t-0 m-b-10 pull-right'>");
					var $li_prev = $("<li ><a href='javascript:;' class='pager_item' value='"+(currentPage-1)+"'>«</a></li>").appendTo($ul);
					if(currentPage === 0){
						$li_prev.addClass("disabled").find("a").removeClass("pager_item");
					}
					var startPoint = 0,endPoint = 4;
					if(currentPage > 2){
						startPoint = currentPage -2;
						endPoint = currentPage +2;
					}
					if(endPoint >= totalPages){
						startPoint = totalPages -4;
						endPoint = totalPages -1;
					}
					if(startPoint < 0){
						startPoint = 0;
					}
					for(var point = startPoint;point<=endPoint;point++){
						var $li_point = $("<li ><a href='javascript:;' class='pager_item' value='"+point+"'>"+(point+1)+"</a></li>").appendTo($ul);
						if(point === currentPage){
							$li_point.addClass("active").find("a").removeClass("pager_item");
						}
					}
					var $li_next = $("<li ><a href='javascript:;' class='pager_item' value='"+(currentPage + 1)+"'>»</a></li>").appendTo($ul); 
					if(currentPage === totalPages){
						$li_next.addClass("disabled").find("a").removeClass("pager_item");
					}
					$(this).html($ul);
				}).show();
			}
		},
		
		/**************************************************************************
		 * 
		 * 处理列表
		 * 
		 *************************************************************************/
		_handleSearchReasult: function($responseText) {
			var hasText = $responseText.find("#page_query tbody").children().size() > 0;
			if(hasText) {
				var $tbody=$responseText.find("#page_query tbody");
				$('#data-table').find("> tbody").empty().append($tbody.html());
			}
			else {
				$('#data-table').find("> tbody").empty()
				.append("<tr><td colspan='15' ><div class='taiji_not_found'>没有检索到符合条件的数据！</div></td></tr>");
			}
			this._bindDataClick();
		},
		
		/**************************************************************************
		 * 
		 * 处理分页跳转
		 * 
		 *************************************************************************/
		_handlePageClick: function(pageclickednumber) {
			// 分页标签点击处理
			var $pageNo = $("input[name='pageNo']", this.currentTarget);
			if ($pageNo.attr("name")) {
				// 如果pageNo存在，则直接修改其值
				$pageNo.val(pageclickednumber);
			} else {
				// 否则，创建pageNo，并追加到form标签下。
				$pageNo = $(
				"<input type='text' id='pageNo' name='pageNo'/>")
				.val(pageclickednumber)
				.appendTo($("#pageQueryForm"), this.currentTarget);
			}
			// 提交表单
			$("#pageQueryForm", this.currentTarget).trigger("submit");
		},
		
		/**************************************************************************
		 * 
		 * 显示模态框
		 * 
		 *************************************************************************/
		_showModal: function(opts, data) {
			var defaults={
					size:"",
					backdrop:true
				};
			var options = $.extend({}, defaults, opts);
			var templateModal='<div class="modal fade" role="dialog" aria-labelledby="myModalLabel">'+
							  		'<div class="modal-dialog" role="document">'+
										'<div class="modal-content">'+
										'</div>'+
									'</div>'+
							  '</div>';
			$modalDialog=$(templateModal).appendTo(document.body);
			$modalDialog.on("hidden.bs.modal",function(){
				$(this).removeClass("modal-message");
				$(this).find(".modal-dialog").removeClass("modal-lg").removeClass("modal-sm");
				$(this).find(".modal-content").empty();
			});
			$modalDialog.find(".modal-dialog").addClass(options.size);
			$modalDialog.find(".modal-content").html(data);
			$modalDialog.modal({backdrop:options.backdrop,show:true});
			return $modalDialog;
		},
		
		/**************************************************************************
		 * 
		 * 关闭模态框
		 * 
		 *************************************************************************/
		_hideModal: function(options) {
			$(".modal-open>.modal.in:last").modal("hide");
		},
		
		/**************************************************************************
		 * 
		 * 表格数据处理
		 * 
		 *************************************************************************/
		_handleTableData: function(responseText,table,trNode) {
			var $row = $(responseText).find(".result_data tr");
			if(table=="add"){
				var $tbody=$('#data-table', this.currentTarget).find("> tbody");
				$tbody.prepend($row);
			} else if(table=="update"){
				$("#data-table",this.currentTarget).find(".custom_clicked")
												   .replaceWith($row.addClass("custom_clicked"));
			} else if(table=="delete"){
				trNode.remove();
			}
			this._bindDataClick();
		},
		
		/**************************************************************************
		 * 
		 * 表格点击事件
		 * 
		 *************************************************************************/
		_bindDataClick: function() {
			$('#data-table',this.currentTarget)
					.find(" > tbody > tr")
					.off("click")
					.on("click",function(event) {
								$(this).addClass("custom_clicked")
										.siblings().removeClass("custom_clicked");
							});
		},
		
		/**************************************************************************
		 * 
		 * 显示loading
		 * 
		 *************************************************************************/
		showLoading: function() {
			$loadingDiv = $("#page-loader");
			if(!$loadingDiv.length) {
				var html = '<div id="page-loader" class="fade in" style="filter:alpha(opacity=60);opacity:0.6;">'
						 +		'<span class="spinner"></span>'
						 + '</div>';
				$loadingDiv=$(html).prependTo(document.body);
			}
		},
		
		/**************************************************************************
		 * 
		 * 隐藏loading
		 * 
		 *************************************************************************/
		hideLoading: function() {
			$("#page-loader").addClass("hide");
		},
		
		/**************************************************************************
		 * 
		 * 显示警告信息
		 * 
		 *************************************************************************/
		showWarnMsg: function(message) {
			console.log(message);
			$warnMsgDiv = $("#warn-message");
			if(!$warnMsgDiv.length) {
				var html = '<div id="warn-message" class="alert alert-warning alert-dismissible" role="alert">'
						 + 		'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
						 + 		'<strong>Warning！</strong>'
						 + 		'<span></span>'
						 + '</div>';
				$warnMsgDiv=$(html).prependTo($("#myManager"));
			}
			$warnMsgDiv.find("span").last().text(message);
		},
		
		/**************************************************************************
		 * 
		 * 显示信息
		 * isSuccess:true/false
		 * type:blue/green/red/orange/purple/dark
		 *************************************************************************/
		showMsg: function(isSuccess,message) {
			if(message == null || message == undefined) {
				message = '';
			}
			console.log(isSuccess+','+message);
			if(isSuccess) {
				$.dialog({
					theme: 'light',
					title: '操作成功！',
					content: message,
					backgroundDismiss: true,
					type: 'dark',
				});
			} else {
				$.dialog({
					theme: 'light',
					title: '操作失败！',
					content: message,
					backgroundDismiss: true,
					type: 'red',
				});
			}
		},
	});
    
    //查询
    $.fn.manage = function(options) {
    	// 区别ajaxSubmit
    	this.ajaxForm({
			type: "post", //提交方式 
	        success: function (responseText, status, xhr) { //提交成功的回调函数
	        	var $responseText = $(responseText);
	        	//处理分页
	        	$._bindPager($responseText.find("#page_query_pager"));
	        	//显示列表
	        	$._handleSearchReasult($responseText);
	        },
	        error: function (xhr, status, error) {
	        	$.showWarnMsg("系统异常，请稍后重试！");
	        }
		});
    }
})(jQuery,window,document);