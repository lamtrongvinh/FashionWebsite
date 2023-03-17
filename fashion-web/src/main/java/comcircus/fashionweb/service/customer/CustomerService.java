package comcircus.fashionweb.service.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.user.customer.Customer;

@Service
public interface CustomerService {
    public Customer getCustomer(Long id);
    public Customer saveCustomer(Customer customer);
    public void deleteCustomer(Long id);
    public List<Customer> getCustomers();
    public Customer updateCustomer(Long id, Customer customer);
}
