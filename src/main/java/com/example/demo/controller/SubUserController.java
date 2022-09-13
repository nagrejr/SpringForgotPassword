package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.SubUser;
import com.example.demo.service.SubUserService;

@Controller
public class SubUserController {
	
	@Autowired
	private SubUserService subUserService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewSubUserForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		SubUser subUser = new SubUser();
		model.addAttribute("subUser", subUser);
		return "new_subuser.html";
	}
	
	@PostMapping("/saveSubUser")
	public String saveEmployee(@ModelAttribute("subUser") SubUser subUser) {
		// save employee to database
		subUserService.saveSubUser(subUser);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		SubUser subUser = subUserService.getSubUserById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("subUser", subUser);
		return "update_subuser";
	}
	
	@GetMapping("/deleteSubUser/{id}")
	public String deleteSubUser(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.subUserService.deleteSubUserById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<SubUser> page = subUserService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<SubUser> listSubUsers = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listSubUsers", listSubUsers);
		return "index";
	}

}
