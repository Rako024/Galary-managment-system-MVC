package com.galery.service;

import com.galery.entities.Customer;
import com.galery.entities.Ticket;
import com.galery.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TicketService ticketService;

    public CustomerService(CustomerRepository customerRepository, TicketService ticketService) {
        this.customerRepository = customerRepository;
        this.ticketService = ticketService;
    }
    public void createCustomer(Customer customer) {

        customerRepository.save(customer);
    }
    public Customer getCustomerById(long id){
       Optional<Customer> customerOptional = customerRepository.findById(id);
       Customer customer = customerOptional.get();

        return customer;
    }
    public Customer loginCustomer(String email, String password){
       Optional<Customer> customerOptional = customerRepository.findByEmail(email);
       Customer customer = customerOptional.get();
       if(customer.getPassword().equals(password)){
           return customer;
       }else
           return null;
    }

    public void ticketAddCustomer(long customerId, long ticketId){
        Customer customer = getCustomerById(customerId);
        Ticket ticket = ticketService.getTicketById(ticketId);
        customer.getTickets().add(ticket);
        ticket.setCustomer(customer);
        ticketService.save(ticket);
        customerRepository.save(customer);

    }
}
