package dao.interfaces;

import java.util.List;

import dao.entity.GymMembership;

public interface GymMembershipDao {

    GymMembership get(Long id);

    List<GymMembership> getAll();

    GymMembership create(GymMembership gymMembership);

    GymMembership update(GymMembership gymMembership);

    boolean delete(Long id);

}
