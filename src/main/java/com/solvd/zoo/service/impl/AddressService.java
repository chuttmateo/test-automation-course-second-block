package com.solvd.zoo.service.impl;

import com.solvd.zoo.dao.IAddressDAO;
import com.solvd.zoo.dao.impl.AddressDAO;
import com.solvd.zoo.model.Address;
import com.solvd.zoo.service.IAddressService;

import java.util.List;

public class AddressService implements IAddressService {
    private IAddressDAO addressDAO;

    public AddressService(IAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    @Override
    public Address getEntityById(long id) {
        return addressDAO.getEntityById(id);
    }

    @Override
    public void saveEntity(Address entity) {
        addressDAO.saveEntity(entity);
    }

    @Override
    public void updateEntity(Address entity) {
        addressDAO.updateEntity(entity);
    }

    @Override
    public void removeEntity(Address entity) {
        addressDAO.removeEntity(entity);
    }

    @Override
    public List<Address> getEntities() {
        return addressDAO.getEntities();
    }
}
