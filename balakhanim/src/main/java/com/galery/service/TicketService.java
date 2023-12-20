package com.galery.service;

import com.galery.entities.Ticket;
import com.galery.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){this.ticketRepository = ticketRepository;}
    public List<Ticket> getAllTicket(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(long Id){
        Optional<Ticket> ticketOptional = ticketRepository.findById(Id);
        Ticket ticket = ticketOptional.get();
        return ticket;
    }

    public void createTicket(String name,
                             String date,
                             String type,
                             int price,
                              String location,
                             String about){
        Ticket ticket = new Ticket();
        ticket.setName(name);
        ticket.setDate(date);
        ticket.setType(type);
        ticket.setPrice(price);
        ticket.setLocation(location);
        ticket.setAbout(about);
        ticketRepository.save(ticket);
    }
    public void updateTicket(Long id, Ticket updatedTicket) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setName(updatedTicket.getName());
        existingTicket.setDate(updatedTicket.getDate());
        existingTicket.setType(updatedTicket.getType());
        existingTicket.setPrice(updatedTicket.getPrice());
        existingTicket.setLocation(updatedTicket.getLocation());
        existingTicket.setAbout(updatedTicket.getAbout());

        ticketRepository.save(existingTicket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
    public void save(Ticket ticket){
        ticketRepository.save(ticket);
    }

    public void removeCustomerFromTicket(long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        ticket.removeCustomer();
        ticketRepository.save(ticket);
    }

}
