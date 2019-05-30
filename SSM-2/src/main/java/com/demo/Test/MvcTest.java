package com.demo.Test;
//ʹ��Spring�ṩ�Ĳ�������ģ��

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.bean.Employee;
import com.github.pagehelper.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml" })
@WebAppConfiguration
public class MvcTest {
	//����Springmvc��ioc
	@Autowired
	WebApplicationContext context;
	// ����mvc����
	MockMvc mockMvc;

	@Before
	public void initMockMvc() {
	mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		//ģ�������õ�����ֵ
	MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/emps1").param("pn", "5")).andReturn();
	//����ɹ��Ժ��������л���pageInfo ��ȡ��pageInfo������֤
	MockHttpServletRequest request=result.getRequest();
	PageInfo  pi=(PageInfo) request.getAttribute("pageInfo");
	System.out.println("��ǰҳ��:"+pi.getPageNum());
	System.out.println("��ҳ��"+pi.getPages());
	System.out.println("�ܼ�¼��"+pi.getTotal());
	System.out.println("��ҳ��������ʾ��ҳ��");
	int[] nums =pi.getNavigatepageNums();
	for (int i : nums) {
		System.out.println(" "+i);
	}
	//��ȡԱ������
	List<Employee> list =pi.getList();
	for (Employee employee : list) {
		System.out.println("ID:"+employee.getEmpId()+"--Name:"+employee.getEmpName());
	}
	}
}
