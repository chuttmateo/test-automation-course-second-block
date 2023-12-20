package com.solvd.zoo.service.impl;

import com.solvd.zoo.dao.IPassportDAO;
import com.solvd.zoo.dao.impl.PassportDAO;
import com.solvd.zoo.model.Passport;
import com.solvd.zoo.service.IPassportService;

import java.util.List;

public class PassportService implements IPassportService {

    private IPassportDAO passportDAO;

    public PassportService(IPassportDAO passportDAO) {
        this.passportDAO = passportDAO;
    }

    @Override
    public Passport getEntityById(long id) {
        return passportDAO.getEntityById(id);
    }

    @Override
    public void saveEntity(Passport entity) {
        passportDAO.saveEntity(entity);
    }

    @Override
    public void updateEntity(Passport entity) {
        passportDAO.updateEntity(entity);
    }

    @Override
    public void removeEntity(Passport entity) {
        passportDAO.removeEntity(entity);
    }

    @Override
    public List<Passport> getEntities() {
        return passportDAO.getEntities();
    }
}
