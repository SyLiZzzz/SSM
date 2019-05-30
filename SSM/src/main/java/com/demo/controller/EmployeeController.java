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
 * ����Ա������ɾ�Ĳ�����
 * */
@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	// Ա�����·���
	/*
	 * ���ֱ�ӷ���ajax .type=PUT��ʽ�����󣬾��Ƿ�װ�������� ���⣺ �������ػ������ݵ���Employee�����װ���� ԭ�� Tomcat��
	 * �Ὣ�������е����ݷ�װ��һ��Map���� request.getParameter("xxx")�ͻ������map��ȡֵ
	 * SpringMVC��װPOJO�����ʱ�� ���POJO��ÿ�����Ե�ֵ��request.getParameter("xxx")�õ�
	 * AJAX����PUT����������Ѫ�� PUT�����������е������ò���
	 * ��ΪTomcatһ����put����Ͳ����װ��������е�����Ϊmap,��ֻ��POST��ʽ������ŷ�װΪput
	 * 
	 * ����Ҫֱ�ӷ���PUT��������Ҫ��װ�������е����� ������HttpPutFormContentFilter������
	 * ����:���������е����ݽ�����װ��һ��Map request�����°�װ ��request.getParamter()����д��
	 */
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	@ResponseBody
	public Msg saveEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}

	// ��ѯԱ����Ϣ
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}

	// ����û���
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkUser(@RequestParam("empName") String empName) {
		// �ж��û����Ƿ��ǺϷ��ı��ʽ
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "�û���ֻ��Ϊ2-5λ���Ļ�6-16λӢ�ĺ�����");
		}
		// ���ݿ��û����ظ�У��
		boolean b = employeeService.checkUser(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "�û���������");
		}
	}

	/*
	 * ����Ա������ 1.֧��JSR303
	 * 
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			// У��ʧ�ܣ���ģ̬���з���У����Ϣ
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("������ֶ���" + fieldError.getField());
				System.out.println("������Ϣ" + fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}

	}

	/*
	 * ����jackson��
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		PageHelper.startPage(pn, 10);
		List<Employee> emps = employeeService.getAll();
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	// ɾ�� ����һ
	@ResponseBody
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("id") String ids) {
		//����ɾ��
		if (ids.contains("-")) {
			List<Integer> del_ids =new ArrayList<>();
			String[] str_ids = ids.split("-");
			//��װid�ļ���
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
	 * ��ѯԱ������
	 */

	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {// ����pagehelper��ҳ���
																									// //�ڲ�ѯǰ���÷���,����ҳ�룬�Լ���ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 10); // StartPage������Ĳ�ѯ���Ƿ�ҳ��ѯ
		List<Employee> emps = employeeService.getAll();
		PageInfo page = new PageInfo(emps, 5); // ʹ��pageInfo��װ��ѯ��Ľ����pegeInfo��װ����ϸ�ķ�ҳ��Ϣ�������в�ѯ����������
		model.addAttribute("pageInfo", page);// ����������ʾ��ҳ��
		return "list";
	}

}
