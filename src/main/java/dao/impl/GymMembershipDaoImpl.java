package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.connection.DataSource;
import dao.entity.GymMembership;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class GymMembershipDaoImpl implements GymMembershipDao {

    private static final String DELETE = "UPDATE gymmembership SET deleted = true WHERE id = ?";
    private static final String UPDATE = "UPDATE gymmembership SET trainer_id = ?, number_of_visits = ?, "
            + "type_of_training = ?, cost = ? WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO gymmembership (trainer_id, number_of_visits, type_of_training,"
            + " cost) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT tr.last_name, g.id, g.trainer_id, g.number_of_visits, g.type_of_training, g.cost "
            + "FROM gymmembership g JOIN trainers tr ON tr.id = g.trainer_id WHERE g.deleted = false AND tr.deleted = false";
    private static final String SELECT_BY_ID = "SELECT tr.last_name, g.id, g,trainer_id, g.number_of_visits, g.type_of_training, g.cost "
            + "FROM gymmembership g JOIN trainers tr ON tr.id = g.trainer_id WHERE g.id = ? AND g.deleted = false AND tr.deleted = false";
    private static final String SELECT_TRAINER_ID = "SELECT tr.id FROM trainers tr WHERE last_name = ?";

    private DataSource dataSource;
    private TrainerDao trainerDao;

    public GymMembershipDaoImpl(DataSource dataSource, TrainerDao trainerDao) {
        this.dataSource = dataSource;
        this.trainerDao = trainerDao;
    }

    @Override
    public GymMembership get(Long id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processGymMembership(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public List<GymMembership> getAll() {
        List<GymMembership> gymMemberships = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                gymMemberships.add(processGymMembership(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return gymMemberships;
    }

    @Override
    public GymMembership create(GymMembership gymMembership) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, getTrainerId(gymMembership.getTrainer().getLastName()));
            statement.setInt(2, (gymMembership.getNumberOfVisits()));
            statement.setString(3, gymMembership.getTypeOfTraining());
            statement.setBigDecimal(4, gymMembership.getCost());
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public GymMembership update(GymMembership gymMembership) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE);
            statement.setInt(1, getTrainerId(gymMembership.getTrainer().getLastName()));
            statement.setInt(2, (gymMembership.getNumberOfVisits()));
            statement.setString(3, gymMembership.getTypeOfTraining());
            statement.setBigDecimal(4, gymMembership.getCost());
            statement.setLong(5, gymMembership.getId());

            statement.executeUpdate();
            return get(gymMembership.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return false;
    }

    private GymMembership processGymMembership(ResultSet result) throws SQLException {
        GymMembership gymMembership = new GymMembership();
        gymMembership.setId(result.getLong("id"));
        Long trainerId = result.getLong("trainer_id");
        gymMembership.setTrainer(trainerDao.get(trainerId));
        gymMembership.setNumberOfVisits(result.getInt("number_of_visits"));
        gymMembership.setTypeOfTraining(result.getString("type_of_training"));
        gymMembership.setCost(result.getBigDecimal("cost"));
        return gymMembership;
    }

    private int getTrainerId(String lastName) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_TRAINER_ID);
            statement.setString(1, lastName);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        log.error("Unable to establish connection or error in id");
        throw new RuntimeException();
    }
}
