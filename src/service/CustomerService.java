package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;

public class CustomerService {

    HashMap<String, Customer> customers = new HashMap<>();

    private static CustomerService customerService = null;

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public void addCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String email) {
        if (!customers.containsKey(email)) {
            throw new IllegalArgumentException("Customer with this email does not exists.\n");
        }
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
