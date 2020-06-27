package com.in28minutes.todo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jws.WebParam.Mode;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.todo.service.TodoService;


@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
     private TodoService service ;
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	
	@RequestMapping(value = "/list-todos",method = RequestMethod.GET)
	//@ResponseBody
	public String showTodosList(ModelMap model) {
		//model.addAttribute("name", name);
		model.addAttribute("todos",service.retrieveTodos("in28Minutes"));
		
		return "list-todos";
	}
	
	@RequestMapping(value="/add-todo",method=RequestMethod.GET)
	public String showTodoPage(ModelMap model) {
		
		//We added this here because we wanted it before /add-todo" post method to avoid error 
		model.addAttribute("todo", new Todo(0,"Default name","Default desc",new Date(),false));
		return"todo";
	}

	
	@RequestMapping(value="/add-todo",method=RequestMethod.POST)
	                                     //@Valid is for hibernate validation check from Todo (Added dependency in pom as well for hibernate validation)
	                                     //BindingResult is to see any validation failures/Errors during binding 
	public String addTodo(ModelMap model,@Valid Todo todo ,BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		service.addTodo("in28Minutes",todo.getDesc(),new Date() ,true);
		//This is used to remove name from url 
		model.clear();
		//This return will send back to list of todos page
		return"redirect:list-todos";
	}
	
	@RequestMapping(value="/update-todo",method = RequestMethod.GET)
	public String updateTodo(ModelMap model,@RequestParam int id) {
		Todo todo=service.retrieveTodo(id);
		model.addAttribute("todo", todo);
		
		return "todo";
		
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo,BindingResult result) {
		if (result.hasErrors())
			return "todo";
        
		todo.setUser("in28Minutes"); //TODO:Remove Hardcoding Later
		service.updateTodo(todo);

		model.clear();// to prevent request parameter "name" to be passed
		return "redirect:/list-todos";
	}
	


	@RequestMapping(value="/delete-todo",method = RequestMethod.GET)
	public String deleteTodo(ModelMap model,@RequestParam int id) {
		service.deleteTodo(id);
		model.clear();
		return "redirect:list-todos";
		
	}
	
}
