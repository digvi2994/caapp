package com.erpca.erpcaapp.controller;

import com.erpca.erpcaapp.model.Customer;
import com.erpca.erpcaapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private  CustomerService svc;

//    public CustomerController(CustomerService svc) {
//        this.svc = svc;
//    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", svc.getAllCustomers());
        return "customers";         // templates/customers.html
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer_form";     // templates/customer_form.html
    }

    @PostMapping
    public String create(@ModelAttribute Customer customer) {
        svc.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", svc.getCustomerById(id));
        return "customer_form";
    }

    @PostMapping("/update/{id}")
    public String update(
      @PathVariable Long id,
      @ModelAttribute Customer customer
    ) {
        customer.setCustID(id);
        svc.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        svc.deleteCustomer(id);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("customer", svc.getCustomerById(id));
        return "customer_view";      // templates/customer_view.html
    }
}