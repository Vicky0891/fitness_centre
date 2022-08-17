package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.connection.DataSource;
import dao.entity.GymMembership;
import dao.interfaces.GymMembershipDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GymMembershipDaoImpl implements GymMembershipDao {

    private static final String DELETE = "UPDATE gymmemberships SET deleted = true WHERE id = ?";
    private static final String UPDATE = "UPDATE gymmemberships SET number_of_visits = ?, "
            + "type_of_training = ?, cost = ? WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO gymmemberships (number_of_visits, type_of_training,"
            + " cost) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT g.id, g.number_of_visits, g.type_of_training, g.cost "
            + "FROM gymmemberships g WHERE g.deleted = false ORDER BY g.number_of_visits";
    private static final String COUNT_ALL = "SELECT count(*) AS total FROM gymmemberships g WHERE g.deleted = false";
    private static final String SELECT_ALL_BY_PAGED = "SELECT g.id, g.number_of_visits, g.type_of_training, g.cost "
            + "FROM gymmemberships g WHERE g.deleted = false ORDER BY g.number_of_visits LIMIT ? OFFSET ?";
    private static final String SELECT_BY_ID = "SELECT g.id, g.number_of_visits, g.type_of_training, g.cost "
            + "FROM gymmemberships g WHERE g.id = ? AND g.deleted = false";

    private DataSource dataSource;

    public GymMembershipDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public GymMembership get(Long id) {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processGymMembership(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<GymMembership> getAll() {
        List<GymMembership> gymMemberships = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                gymMemberships.add(processGymMembership(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return gymMemberships;
    }

    @Override
    public List<GymMembership> getAll(int limit, Long offset) {
        List<GymMembership> gymMemberships = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                gymMemberships.add(processGymMembership(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return gymMemberships;
    }

    @Override
    public GymMembership create(GymMembership gymMembership) {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, (gymMembership.getNumberOfVisits()));
            statement.setString(2, gymMembership.getTypeOfTraining());
            statement.setBigDecimal(3, gymMembership.getCost());
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public GymMembership update(GymMembership gymMembership) {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, (gymMembership.getNumberOfVisits()));
            statement.setString(2, gymMembership.getTypeOfTraining());
            statement.setBigDecimal(3, gymMembership.getCost());
            statement.setLong(4, gymMembership.getId());

            statement.executeUpdate();
            return get(gymMembership.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return false;
    }

    private GymMembership processGymMembership(ResultSet result) throws SQLException {
        GymMembership gymMembership = new GymMembership();
        gymMembership.setId(result.getLong("id"));
        gymMembership.setNumberOfVisits(result.getInt("number_of_visits"));
        gymMembership.setTypeOfTraining(result.getString("type_of_training"));
        gymMembership.setCost(result.getBigDecimal("cost"));
        return gymMembership;
    }

    @Override
    public long count() {
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(COUNT_ALL);
            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        throw new RuntimeException("Couldn't count gymmemberships");
    }

    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
        }
    }

}
