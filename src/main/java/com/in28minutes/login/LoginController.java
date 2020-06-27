package com.in28minutes.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("name")
public class LoginController {
	
	@Autowired
     LoginService service ;
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	//@ResponseBody
	public String showLoginPage() {
		return "login";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	//@ResponseBody
	public String handleUserLogin(@RequestParam String name,
			                         @RequestParam String password ,ModelMap model) {
		
		if(!service.validateUser(name, password)) {
			
			model.put("errorMessage", "Invalid Credentials");
			return "login";
			
		}
		else {
			
		model.put("name",name);
		model.put("password", password);
		System.out.println("The name is !!!!!! "+name+"Password is "+password);
		return "welcome";
		}
	}
}
