package dao.interfaces;

import java.util.List;

import dao.entity.Customer;

public interface CustomerDao {
    
    Customer get(Long id);

    List<Customer> getAll();
    
    Customer create(Customer customer);

    Customer update(Customer customer);
    
    Customer updateByAdmin(Customer customer);
    
    Customer getCustomerByEmail(String email);
    
    List<Customer> getAllByType(String name);
    
    boolean delete(Long id);

}
