package comcircus.fashionweb.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.repository.CustomerRepository;

@Service
public class CustomerServiceImp implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer exitsCustomer = customerRepository.findById(id).get();
        exitsCustomer.setFirst_name(customer.getFirst_name());
        exitsCustomer.setLast_name(customer.getLast_name());
        exitsCustomer.setPhone_number(customer.getPhone_number());
        exitsCustomer.setEmail(customer.getEmail());
        exitsCustomer.setAddress(customer.getAddress());
        exitsCustomer.setUrl_image(customer.getUrl_image());
        
        return customerRepository.save(exitsCustomer);
    }
    
}
