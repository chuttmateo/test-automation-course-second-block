package com.solvd.zoo.dao.impl;

import com.solvd.zoo.dao.IAddressDAO;
import com.solvd.zoo.dao.connectionpool.ConnectionPool;
import com.solvd.zoo.model.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO implements IAddressDAO {
    @Override
    public Address getEntityById(long id) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        Address address = new Address();

        try (PreparedStatement ps = conn
                .prepareStatement("SELECT id, city FROM addresses WHERE id=?")){
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                address.setId(resultSet.getLong(1));
                address.setCity(resultSet.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
        return address;
    }

    @Override
    public void saveEntity(Address entity) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        if (entity == null) return;

        try (PreparedStatement ps = conn
                .prepareStatement("INSERT INTO addresses (city) VALUES (?)", Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, entity.getCity());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            while (generatedKeys.next()){
                entity.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public void updateEntity(Address entity) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        if (entity == null) return;

        try (PreparedStatement ps = conn
                .prepareStatement("UPDATE addresses SET city=? WHERE id=?")){
            ps.setString(1, entity.getCity());
            ps.setLong(2,entity.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public void removeEntity(Address entity) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        if (entity == null) return;

        try (PreparedStatement ps = conn
                .prepareStatement("DELETE FROM addresses WHERE id=?")){
            ps.setLong(1,entity.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public List<Address> getEntities() {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        List<Address> list = new ArrayList<>();

        try (PreparedStatement ps = conn
                .prepareStatement("SELECT id, city FROM addresses")){
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                Address address = new Address();
                address.setId(resultSet.getLong(1));
                address.setCity(resultSet.getString(2));
                list.add(address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
        return list;
    }
}
