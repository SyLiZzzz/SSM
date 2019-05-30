<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工列表</title>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<script type="text/javascript"
	src="${APP_PATH}/static/js/jquery-3.3.1.min.js"></script>
<link href="${APP_PATH}/static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<script src="${APP_PATH}/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

	<!-- 新增模态框 -->
	<div class="modal fade" id="empAndModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">员工添加</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-10">
								<input type="text" name="empName" class="form-control"
									id="empName_add_input" placeholder="请输入姓名"> 
									<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10">
								<input type="text" name="email" class="form-control"
									id="email_add_input" placeholder="请输入邮箱">
									<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">性别</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="gender1_add_input" value="M"
									checked="checked">男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="gender2_add_input" value="F">女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">部门名</label>
							<div class="col-sm-5">
								<select class="form-control" name="dId"></select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改模态框 -->
	<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">员工修改</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="empName_update_static"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10">
								<input type="text" name="email" class="form-control"
									id="email_update_input" placeholder="请输入邮箱">
									<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">性别</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="gender1_update_input" value="M"
									checked="checked">男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="gender2_update_input" value="F">女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">部门名</label>
							<div class="col-sm-5">
								<select class="form-control" name="dId"></select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>员工系统</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-9">
				<button class="btn btn-primary" id="emp_and_modal_btn">新增</button>
				<button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all"/>
							</th>
							<th>序号</th>
							<th>员工名字</th>
							<th>性别</th>
							<th>邮箱</th>
							<th>所在部门</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页 -->
		<div class="row">
			<!-- 分页信息 -->
			<div class="col-md-4" id="page_info_area"></div>
			<!-- 分页条 -->
			<div class="col-md-8" id="page_nav_area"></div>
		</div>
	</div>
	<script type="text/javascript">
		var currentPage;
		//1.加载完成发送ajax请求
		$(function() {
			to_page(1);//首页
		});

		function to_page(pn) {
			$.ajax({
				url : "${APP_PATH}/emps",
				data : "pn=" + pn,
				type : "GET",
				success : function(result) {
					//1.解析并显示员工数据
					build_emps_table(result);
					//2.解析并显示分页信息
					build_page_info(result);
					//2.解析并显示分页条信息
					build_page_nav(result);
				}
			});
		};

		function build_emps_table(result) {
			//每次请求时清空一次数据
			$("#emps_table tbody").empty();
			var emps = result.extend.pageInfo.list;
			$.each(emps, function(index, item) {
				var checkBoxId =$("<td><input type='checkbox' class='check_item'/></td>");
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var genderTd = $("<td></td>").append(
						item.gender == 'M' ? "男" : "女");
				var emailTd = $("<td></td>").append(item.email);
				var deptNameTd = $("<td></td>")
						.append(item.department.deptName);
				var editBtn = $("<button></button>").addClass(
						"btn btn-primary btn-sm edit_btn").append(
						$("<span></span>").addClass(
								"glyphicon glyphicon-pencil")).append("编辑");
				//为编辑按钮添加自定义属性，来表示员工ID
				editBtn.attr("edit-id",item.empId);
				var delBtn = $("<button></button>").addClass(
						"btn btn-danger btn-sm delete_btn").append(
						$("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
				//为删除按钮添加自定义属性，来表示员工ID
				delBtn.attr("del-id",item.empId);
				var btnTd = $("<td></td>").append(editBtn).append(
						"&nbsp;&nbsp;").append(delBtn)
				$("<tr></tr>").append(checkBoxId).append(empIdTd).append(empNameTd).append(
						genderTd).append(emailTd).append(deptNameTd).append(
						btnTd).appendTo("#emps_table tbody");
			});
		}
		//显示分页信息
		function build_page_info(result) {
			$("#page_info_area").empty();
			$("#page_info_area").append(
					"当前第" + result.extend.pageInfo.pageNum + "页,总"
							+ result.extend.pageInfo.pages + "页,总共"
							+ result.extend.pageInfo.total + "条记录");
			currentPage=result.extend.pageInfo.pageNum;
		}
		//显示分页条,点击事件
		function build_page_nav(result) {
			$("#page_nav_area").empty();
			var ul = $("<ul></ul>").addClass("pagination");
			var firstPageLi = $("<li></li>").append(
					$("<a></a>").append("首页").attr("href", "#"));
			var prePageLi = $("<li></li>").append(
					$("<a></a>").append("&laquo;"));
			if (result.extend.pageInfo.hasPreviousPage == false) {
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			} else {
				//组件添加事件
				firstPageLi.click(function() {
					to_page(1);
				});
				prePageLi.click(function() {
					to_page(result.extend.pageInfo.pageNum - 1);
				});
			}
			var nextPageLi = $("<li></li>").append(
					$("<a></a>").append("&raquo;"));
			var lastPageLi = $("<li></li>").append(
					$("<a></a>").append("末页").attr("href", "#"));
			if (result.extend.pageInfo.hasNextPage == false) {
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
			} else {
				nextPageLi.click(function() {
					to_page(result.extend.pageInfo.pageNum + 1);
				});
				lastPageLi.click(function() {
					to_page(result.extend.pageInfo.pages);
				});
			}
			ul.append(firstPageLi).append(prePageLi);//添加首页和前一页的组件
			$.each(result.extend.pageInfo.navigatepageNums, function(index,
					item) {
				var numLi = $("<li></li>").append($("<a></a>").append(item));
				if (result.extend.pageInfo.pageNum == item) {
					numLi.addClass("active");
				}
				numLi.click(function() {
					to_page(item);
				});
				ul.append(numLi);
			});
			//添加下一页和末页的组件
			ul.append(nextPageLi).append(lastPageLi);
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		}
		
		//清空模态框表单样式与内容
		function reset_form(ele){
			$(ele)[0].reset();
			//清空表单样式
			$(ele).find("*").removeClass("han-error has-success");
			$(ele).find(".help-block").text("")
		}
		
		//模态框事件
		$("#emp_and_modal_btn").click(function() {
			//每次点击模态框都重置表单
			reset_form("#empAndModal form");
			//发送Ajax请求，查出部门信息，显示在下拉列表
			getDepts("#empAndModal select");
			$("#empAndModal").modal({})
		});
		//查出所有部门，并显示在下拉列表
		function getDepts(ele) {
			//每次都先清空下拉列表的值
			$(ele).empty();
			$.ajax({
				url : "${APP_PATH}/depts",
				type : "GET",
				success : function(result) {
					$.each(result.extend.depts, function() {
						var optionEle = $("<option></option>").append(
								this.deptName).attr("value", this.deptId);
						optionEle.appendTo(ele);
					});
				}
			});
		}
		//显示校验的提示信息
		function show_validate_msg(ele,status,msg){
			//每次都先清除状态
				$(ele).parent().removeClass("has-success has-error");
				$(ele).next("span").text("");
			if("success"==status){
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			}else if("error"==status){
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		
		
		//校验数据
		function validate_add_form() {
			//1.拿到校验的数据，使用正则表达式
			//每次访问前都应该清空之前元素的样式
			var empName = $("#empName_add_input").val();
			var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
			if (!regName.test(empName)) {
				show_validate_msg("#empName_add_input","error","用户名只能为2-5位中文Or6-16位英文和数字");
				return false;
			}else{
				show_validate_msg("#empName_add_input","success","");
				
			}
			;
			var email = $("#email_add_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!regEmail.test(email)) {
				show_validate_msg("#email_add_input","error","邮箱格式不正确");
				return false;
			}else{
				show_validate_msg("#email_add_input","success","");
			}
			;
			return true;
		}
		//验证用户是否已存在
		$("#empName_add_input").change(function(){
			//发送ajax请求校验用户名是否可用
			var empName=this.value;
			$.ajax({
				url:"${APP_PATH}/checkUser",
				type:"POST",
				data:"empName="+empName,
				success:function(result){
					if(result.code==100){
						show_validate_msg("#empName_add_input","success","用户名可用");
						$("#emp_save_btn").attr("ajax-va","success");
					}else{
						show_validate_msg("#empName_add_input","error",result.extend.va_msg);
						$("#emp_save_btn").attr("ajax-va","error");
					}
				}
			});
		});
		
		//保存事件
		$("#emp_save_btn").click(function() {
			//在保存前，先对用户输入的数据进行校验
			if (!validate_add_form()) {
				return flase;
			}; 
			//先判断用户名验证是否成功
			if($(this).attr("ajax-va")=="error"){
				return flase;
			}
			//1.模态框中填写的表单数据提交给服务器保存
			//2.发送Ajax请求保存员工
			$.ajax({
				url : "${APP_PATH}/emp",
				type : "POST",
				data : $("#empAndModal form").serialize(),
				success : function(result) {
					if(result.code==100){
						//保存成功后   1.关闭模态框  2.来到最后一页，显示刚保存的信息
						$("#empAndModal").modal('hide');
						//显示最后一页数据
						to_page(999999999);
					}else{
						//显示失败信息
						if(undefind !=result.extend.errorFields.email){
							//显示邮箱错误信息
							show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
						}
						if(undefind !=result.extend.errorFields.empName){
							//显示名字错误信息
							show_validate_msg("#empName_add_input","error",result.extend.errorFields.empName);
						}
					}
				}
			})
		});
		
		//因为按钮是ajax请求读出来的，所以绑定事件在按钮创建之前就绑定了click，所以绑定不上
		//使用on代替
		//绑定修改事件
		$(document).on("click",".edit_btn",function(){
			//1.查出部门信息并显示部门列表 ，
			getDepts("#empUpdateModal select");
			//2.查询出员工信息
			getEmp($(this).attr("edit-id"));
			//3.把员工的id传递给模态框的更新按钮
			$("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"));
			$("#empUpdateModal").modal({
			})
		})
		//查询员工信息显示列表
		function getEmp(id){
			$.ajax({
				url:"${APP_PATH}/emp/"+id,
				type:"GET",
				success:function(result){
					var empDate =result.extend.emp;
					$("#empName_update_static").text(empDate.empName);
					$("#email_update_input").val(empDate.email);
					$("#empUpdateModal input[name=gender]").val([empDate.gender]);
					$("#empUpdateModal select").val([empDate.dId]);
				}
			})
		}
		//点击更新，更新员工信息
		$("#emp_update_btn").click(function(){
			//验证邮箱是否合法
			var email = $("#email_update_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!regEmail.test(email)) {
				show_validate_msg("#email_update_input","error","邮箱格式不正确");
				return false;
			}else{
				show_validate_msg("#email_update_input","success","");
			};
			//发送ajax请求，保存员工发送的数据
			$.ajax({
				url:"${APP_PATH}/emp/"+$(this).attr("edit-id"),
				type:"PUT",
				/* type:"POST"
				data:$("#empUpdateModal form").serialize()+"&_method=PUT", */
				data:$("#empUpdateModal form").serialize(),
				success:function(result){
					$("#empUpdateModal").modal('hide');
					to_page(currentPage);//回到本页面
				},
			});
		});
		
		//删除事件 ---单个
		$(document).on("click",".delete_btn",function(){
			//1.确认删除对话框
			var empName=$(this).parents("tr").find("td:eq(2)").text();
			var empId=$(this).attr("del-id");
			if(confirm("确认删除【"+empName+"】吗？")){
				//确认发送ajax请求删除
				$.ajax({
					url:"${APP_PATH}/emp/"+empId,
					type:"DELETE",
					success:function(result){
						alert(result.msg);
						to_page(currentPage);//回到本页面
					}
				});
			}	
		});
		//删除事件 ---全部
		$("#check_all").click(function(){
			$(".check_item").prop("checked",$(this).prop("checked"));
		});
		
		$(document).on("click",".check_item",function(){
		var flag=$(".check_item:checked").length==$(".check_item").length;
			$("#check_all").prop("checked",flag);
		});
		$("#emp_delete_all_btn").click(function(){
			var empNames=""
			var del_idstr="";
			$.each($(".check_item:checked"),function(){
				empNames+=$(this).parents("tr").find("td:eq(2)").text()+",";
				del_idstr+=$(this).parents("tr").find("td:eq(1)").text()+"-";
			});
			//去除多余的,
			empNames=empNames.substring(0,empNames.length-1);
			del_idstr=del_idstr.substring(0,del_idstr.length-1);
			if(confirm("确认删除【"+empNames+"】吗？")){
				$.ajax({
					url:"${APP_PATH}/emp/"+del_idstr,
					type:"DELETE",
					success:function(result){
						alert(result.msg)
					to_page(currentPage);//回到本页面
					}
				});
			}
		});
	</script>
</body>
</html>