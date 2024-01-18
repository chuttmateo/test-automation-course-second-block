package com.solvd.zoo;

import com.solvd.zoo.dao.impl.AddressDAO;
import com.solvd.zoo.dao.impl.EmployeeDAO;
import com.solvd.zoo.dao.impl.PassportDAO;
import com.solvd.zoo.model.Address;
import com.solvd.zoo.model.Employee;
import com.solvd.zoo.model.Passport;
import com.solvd.zoo.service.IPassportService;
import com.solvd.zoo.service.impl.AddressService;
import com.solvd.zoo.service.impl.EmployeeService;
import com.solvd.zoo.service.impl.PassportService;

public class Main {
    public static void main(String[] args) {
        AddressDAO addressDAO = new AddressDAO();
        PassportDAO passportDAO = new PassportDAO();

        Address address = new Address();
        address.setCity("Hello city2");
        Passport passport = new Passport();
        passport.setNumber("NUMBER " + Math.random());

        /*addressDAO.saveEntity(address);
        System.out.println(address);
        System.out.println("");
        address.setCity("NY");
        addressDAO.updateEntity(address);
        System.out.println(addressDAO.getEntityById(address.getId()));
        System.out.println("");
        addressDAO.removeEntity(address);
        addressDAO.getEntities().forEach(System.out::println);*/

        /*passportDAO.saveEntity(passport);
        System.out.println(passport);
        System.out.println("");
        passport.setNumber("NsdafsadfY");
        passportDAO.updateEntity(passport);
        System.out.println(passportDAO.getEntityById(passport.getId()));
        System.out.println("");
        passportDAO.removeEntity(passport);
        passportDAO.getEntities().forEach(System.out::println);*/

        EmployeeService employeeService = new EmployeeService(
                new EmployeeDAO(),
                new PassportService(new PassportDAO()),
                new AddressService(new AddressDAO())
        );

        System.out.println(employeeService.getEntityById(1));
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Mateo");
        newEmployee.setLastName("Chutt");
        newEmployee.setPassport(passport);
        newEmployee.setAddress(address);

        employeeService.saveEntity(newEmployee);
        System.out.println(newEmployee);
        //changing values before Update into database
        newEmployee.setFirstName("CARLOS");
        newEmployee.getAddress().setCity("BYE CITY");
        newEmployee.getPassport().setNumber("NEW NUMBER " + Math.random());
        employeeService.updateEntity(newEmployee);
        System.out.println(newEmployee);
        employeeService.removeEntity(newEmployee);

        employeeService.getEntities().forEach(System.out::println);


        //hello
    }
}
