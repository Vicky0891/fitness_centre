package dao.interfaces;

import java.util.List;

import dao.entity.GymMembership;

public interface GymMembershipDao {

    GymMembership get(Long id);

    List<GymMembership> getAll();

    List<GymMembership> getAll(int limit, Long offset);
    
    GymMembership create(GymMembership gymMembership);

    GymMembership update(GymMembership gymMembership);

    boolean delete(Long id);
    
    long count();

}
