package com.fastlane.contact.app.controller;


import com.fastlane.contact.app.model.Contact;
import com.fastlane.contact.app.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listContacts", contactService.getAllContacts());
        return "index";
    }

    @GetMapping("/showNewContactForm")
    public String showNewContactForm(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "new_contact";
    }

    @PostMapping("/saveContact")
    public String saveContact(@ModelAttribute("contact") Contact contact) {
        contactService.saveContact(contact);
        return "redirect:/";
    }

    @GetMapping("/EditContact/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Contact contact = contactService.getContactById(id).orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
        model.addAttribute("contact", contact);
        return "update_contact";
    }

    @GetMapping("/deleteContact/{id}")
    public String deleteContact(@PathVariable(value = "id") long id) {
        contactService.deleteContact(id);
        return "redirect:/";
    }
}
