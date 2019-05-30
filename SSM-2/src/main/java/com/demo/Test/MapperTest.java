package com.demo.Test;

import java.util.UUID;

import javax.swing.Spring;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.bean.Department;
import com.demo.bean.Employee;
import com.demo.dao.DepartmentMapper;
import com.demo.dao.EmployeeMapper;

/*����dao��Ĺ���
�Ƽ�ʹ��Spring��Ŀ�Դ��ĵ�Ԫ���ԣ������Զ�ע��������Ҫ�����
1.����SpringTestģ��
2.ʹ��@ContextConfigurationָ��Spring�����ļ���λ��
3.ֱ��autowiredҪʹ�õ����
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlsession;

	// ����DepartmentMapper
	@Test
	public void testselect() {
		/*
		 * //1.����SpringIOC���� ApplicationContext ioc =new
		 * ClassPathXmlApplicationContext("applicationContext.xml"); //2.�������л�ȡMapper
		 * ioc.getBean(DepartmentMapper.class);
		 */
		System.out.println(departmentMapper);
		// 1.���뼸������
		// departmentMapper.insertSelective(new Department(null, "������"));
		// departmentMapper.insertSelective(new Department(null, "���Բ�"));
		// 2.����Ա������
		// employeeMapper.insertSelective(new Employee(null, "����", "M", "ZS@qq.com",
		// 1));

		// ����������Ա�� :ʹ�ÿ���ִ������������sqlsession
		EmployeeMapper mapper = sqlsession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 100; i++) {
			String uid=UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Employee(null, uid, "M", uid+"@qq.com", 1));
		}
		System.out.println("�������");
	}
}
