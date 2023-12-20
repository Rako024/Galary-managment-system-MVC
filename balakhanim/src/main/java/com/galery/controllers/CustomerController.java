package com.galery.controllers;

import com.galery.entities.Customer;
import com.galery.entities.Ticket;
import com.galery.service.CustomerService;
import com.galery.service.TicketService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    private TicketService ticketService;

    public CustomerController(CustomerService customerService,TicketService ticketService){
        this.customerService = customerService;
        this.ticketService = ticketService;
    }

    @GetMapping("/add")
    public String showCreateCustomerPage() {

        return "customer-create";
    }


    @PostMapping("/create")
    public String createCustomer(@RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String password,
                                 @RequestParam String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setPassword(password);
        customer.setEmail(email);

        customerService.createCustomer(customer);
        return "redirect:/customer/login/";
    }

    @GetMapping("/index/{id}")
    public String getIndex(@PathVariable long id, Model model){
        Customer customer =customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        List<Ticket> tickets = customer.getTickets();
        model.addAttribute("tickets",tickets);
        return "index";
    }

    @GetMapping("/singin")
    public String getSingin(){
        return "Customer-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password){
        Customer customer = customerService.loginCustomer(email,password);
        if(customer != null){
            return "redirect:/customer/index/"+customer.getId();
        }else
            return "redirect:/customer/singin";
    }

    @GetMapping("/buyticket/{id}")
    public String buyTicket(@PathVariable long id, Model model){
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        List<Ticket> tickets = ticketService.getAllTicket();
        model.addAttribute("tickets",tickets);
        return "customer-buyticket";
    }

    @PostMapping("/buy/{customerId}")
    public String buy(@PathVariable long customerId, @RequestParam long selectedTicket){
        customerService.ticketAddCustomer(customerId,selectedTicket);
        return "redirect:/customer/index/"+customerId;
    }

    @GetMapping("/delete-ticket/{customerId}/{ticketId}")
    public String deleteTicket(@PathVariable long customerId, @PathVariable long ticketId) {
        ticketService.removeCustomerFromTicket(ticketId);
        return "redirect:/customer/index/" + customerId;
    }

}
