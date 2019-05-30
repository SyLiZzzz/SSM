package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.Employee;
import com.demo.bean.EmployeeExample;
import com.demo.bean.EmployeeExample.Criteria;
import com.demo.dao.EmployeeMapper;

@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper employeeMapper;

	// ��ѯ����Ա��
	public List<Employee> getAll() {

		return employeeMapper.selectByExampleWithDept(null);
	}

	// ���淽��
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	// �����û����Ƿ����
	public boolean checkUser(String empName) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;// ����0==true ����ǰ��������
	}

	// ����id��ѯԱ��
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	// Ա������
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}

	public void deleteBatch(List<Integer> ids) {
		// TODO Auto-generated method stub
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
	}

}
