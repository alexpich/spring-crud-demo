package com.alex.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alex.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

	private EntityManager entityManager;

	@Autowired
	public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Employee> findAll() {
		// Create a query
		Query theQuery = entityManager.createQuery("from Employee", Employee.class);

		// Execute a query and get a result list
		List<Employee> employees = theQuery.getResultList();

		// Return the results
		return employees;
	}

	@Override
	public Employee findById(int theId) {
		// Get employee
		Employee theEmployee = entityManager.find(Employee.class, theId);
		return theEmployee;
	}

	@Override
	public void save(Employee theEmployee) {
		// save or update the employee - if id==0 then save/insert else update
		Employee dbEmployee = entityManager.merge(theEmployee);

		// update with id from db... so we can get generated id for save/insert
		theEmployee.setId(dbEmployee.getId());

	}

	@Override
	public void deleteById(int theId) {
		  // delete object with primary key
		  Query theQuery = entityManager.createQuery("delete from Employee where id=:employeeId");

		  theQuery.setParameter("employeeId", theId);
		  theQuery.executeUpdate();
	}

}
