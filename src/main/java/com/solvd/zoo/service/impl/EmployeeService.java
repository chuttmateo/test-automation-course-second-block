package com.solvd.zoo.service.impl;

import com.solvd.zoo.dao.IEmployeeDAO;
import com.solvd.zoo.model.Employee;
import com.solvd.zoo.service.IAddressService;
import com.solvd.zoo.service.IEmployeeService;
import com.solvd.zoo.service.IPassportService;

import java.util.List;

public class EmployeeService implements IEmployeeService {

    private IEmployeeDAO employeeDAO;
    private IPassportService passportService;
    private IAddressService addressService;

    public EmployeeService(IEmployeeDAO employeeDAO, IPassportService passportService, IAddressService addressService) {
        this.employeeDAO = employeeDAO;
        this.passportService = passportService;
        this.addressService = addressService;
    }

    @Override
    public Employee getEntityById(long id) {
        Employee employee = employeeDAO.getEntityById(id);
        employee.setPassport(passportService.getEntityById(employee.getPassport().getId()));
        employee.setAddress(addressService.getEntityById(employee.getAddress().getId()));
        return employee;
    }

    @Override
    public void saveEntity(Employee entity) {
        passportService.saveEntity(entity.getPassport());
        addressService.saveEntity(entity.getAddress());
        employeeDAO.saveEntity(entity);
    }

    @Override
    public void updateEntity(Employee entity) {
        passportService.updateEntity(entity.getPassport());
        addressService.updateEntity(entity.getAddress());
        employeeDAO.updateEntity(entity);
    }

    @Override
    public void removeEntity(Employee entity) {
        passportService.removeEntity(entity.getPassport());
        addressService.removeEntity(entity.getAddress());
        employeeDAO.removeEntity(entity);
    }

    @Override
    public List<Employee> getEntities() {
        return employeeDAO.getEntities();
    }
}
