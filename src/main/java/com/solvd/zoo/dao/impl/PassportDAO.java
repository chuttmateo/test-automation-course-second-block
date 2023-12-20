package com.solvd.zoo.dao.impl;

import com.solvd.zoo.dao.IPassportDAO;
import com.solvd.zoo.dao.connectionpool.ConnectionPool;
import com.solvd.zoo.model.Passport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassportDAO implements IPassportDAO {
    @Override
    public Passport getEntityById(long id) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        Passport passport = new Passport();

        try (PreparedStatement ps = conn
                .prepareStatement("SELECT id, number FROM passports WHERE id=?")){
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                passport.setId(resultSet.getLong(1));
                passport.setNumber(resultSet.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
        return passport;
    }

    @Override
    public void saveEntity(Passport entity) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        if (entity == null) return;

        try (PreparedStatement ps = conn
                .prepareStatement("INSERT INTO passports (number) VALUES (?)", Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, entity.getNumber());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()){
                entity.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public void updateEntity(Passport entity) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        if (entity == null) return;

        try (PreparedStatement ps = conn
                .prepareStatement("UPDATE passports SET number=? WHERE id=?")){
            ps.setString(1, entity.getNumber());
            ps.setLong(2,entity.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public void removeEntity(Passport entity) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        if (entity == null) return;

        try (PreparedStatement ps = conn
                .prepareStatement("DELETE FROM passports WHERE id=?")){
            ps.setLong(1,entity.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public List<Passport> getEntities() {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection conn = connectionPool.getConnection();

        List<Passport> list = new ArrayList<>();

        try (PreparedStatement ps = conn
                .prepareStatement("SELECT id, number FROM passports")){
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                Passport passport = new Passport();
                passport.setId(resultSet.getLong(1));
                passport.setNumber(resultSet.getString(2));
                list.add(passport);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connectionPool.releaseConnection(conn);
        }
        return list;
    }
}
