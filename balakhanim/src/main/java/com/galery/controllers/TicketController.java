package com.galery.controllers;

import com.galery.entities.Ticket;
import com.galery.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bilet")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {this.ticketService = ticketService;}

    @GetMapping("/")
    public String getTicketList(Model model){
        List<Ticket> tickets = ticketService.getAllTicket();
        model.addAttribute("tickets", tickets);
        return "ticket-list";
    }

    @GetMapping("/add")
    public String getCreate(){
        return "ticket-creat";
    }

    @PostMapping("/create")
    public String createTicket(@RequestParam String name,
                               @RequestParam String date,
                               @RequestParam String type,
                               @RequestParam int price,
                               @RequestParam String location,
                               @RequestParam String about){
        ticketService.createTicket(name,date,type,price,location,about);
        return "redirect:/bilet/";
    }
    @GetMapping("/edit-ticket/{id}")
    public String getEditTicket(@PathVariable long id, Model model){
        model.addAttribute("ticket",ticketService.getTicketById(id));
        return "ticket-edit";
    }

    @PostMapping("/edit")
    public String editTicket(@ModelAttribute("ticket") Ticket updatedTicket) {
        ticketService.updateTicket(updatedTicket.getId(), updatedTicket);
        return "redirect:/bilet/";
    }
    @GetMapping("/delete-ticket/{id}")
    public String deleteTicket(@PathVariable long id){
        ticketService.deleteTicket(id);
        return "redirect:/bilet/";
    }
}
