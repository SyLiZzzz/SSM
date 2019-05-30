package com.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.bean.Employee;
import com.demo.bean.Msg;
import com.demo.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/*
 * 处理员工的增删改查请求
 * */
@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	// 员工更新方法
	/*
	 * 如果直接发送ajax .type=PUT形式的请求，就是封装不上数据 问题： 请求体重会有数据但是Employee对象封装不上 原因 Tomcat中
	 * 会将请求体中的数据封装成一个Map对象 request.getParameter("xxx")就会中这个map中取值
	 * SpringMVC封装POJO对象的时候 会把POJO中每个属性的值，request.getParameter("xxx")拿到
	 * AJAX发送PUT请求引发的血案 PUT请求，请求体中的数据拿不到
	 * 因为Tomcat一看是put请求就不会封装请求体的中的数据为map,而只有POST形式的请求才封装为put
	 * 
	 * 如需要直接发送PUT请求，则还需要封装请求体中的数据 配置上HttpPutFormContentFilter过滤器
	 * 作用:将请求体中的数据解析包装成一个Map request被重新包装 ，request.getParamter()被重写。
	 */
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	@ResponseBody
	public Msg saveEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}

	// 查询员工信息
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}

	// 检查用户名
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkUser(@RequestParam("empName") String empName) {
		// 判断用户名是否是合法的表达式
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名只能为2-5位中文或6-16位英文和数字");
		}
		// 数据库用户名重复校验
		boolean b = employeeService.checkUser(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}

	/*
	 * 定义员工保存 1.支持JSR303
	 * 
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			// 校验失败，在模态框中返回校验信息
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名" + fieldError.getField());
				System.out.println("错误信息" + fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}

	}

	/*
	 * 导入jackson包
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		PageHelper.startPage(pn, 10);
		List<Employee> emps = employeeService.getAll();
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	// 删除 二合一
	@ResponseBody
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("id") String ids) {
		//批量删除
		if (ids.contains("-")) {
			List<Integer> del_ids =new ArrayList<>();
			String[] str_ids = ids.split("-");
			//组装id的集合
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		} else {
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}

	/*
	 * 查询员工数据
	 */

	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {// 引入pagehelper分页插件
																									// //在查询前调用方法,传入页码，以及，每页的大小
		PageHelper.startPage(pn, 10); // StartPage后紧跟的查询就是分页查询
		List<Employee> emps = employeeService.getAll();
		PageInfo page = new PageInfo(emps, 5); // 使用pageInfo包装查询后的结果，pegeInfo封装了详细的分页信息，包括有查询出来的数据
		model.addAttribute("pageInfo", page);// 传入连续显示的页数
		return "list";
	}

}
