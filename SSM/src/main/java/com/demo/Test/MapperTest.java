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

/*测试dao层的工作
推荐使用Spring项目自带的单元测试，可以自动注入我们需要的组件
1.导入SpringTest模块
2.使用@ContextConfiguration指定Spring配置文件的位置
3.直接autowired要使用的组件
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

	// 测试DepartmentMapper
	@Test
	public void testselect() {
		/*
		 * //1.创建SpringIOC容器 ApplicationContext ioc =new
		 * ClassPathXmlApplicationContext("applicationContext.xml"); //2.从容器中获取Mapper
		 * ioc.getBean(DepartmentMapper.class);
		 */
		System.out.println(departmentMapper);
		// 1.插入几个部门
		// departmentMapper.insertSelective(new Department(null, "开发部"));
		// departmentMapper.insertSelective(new Department(null, "测试部"));
		// 2.插入员工数据
		// employeeMapper.insertSelective(new Employee(null, "张三", "M", "ZS@qq.com",
		// 1));

		// 批量插入多个员工 :使用可以执行批量操作的sqlsession
		EmployeeMapper mapper = sqlsession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 100; i++) {
			String uid=UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Employee(null, uid, "M", uid+"@qq.com", 1));
		}
		System.out.println("批量完成");
	}
}
