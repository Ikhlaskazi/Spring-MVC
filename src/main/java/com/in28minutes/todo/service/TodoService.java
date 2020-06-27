package com.in28minutes.todo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.in28minutes.todo.Todo;

@Service
public class TodoService {

  private static List<Todo> todos = new ArrayList<Todo>();
  private static int todoCount=5;
  
     
    static {
    	todos.add(new Todo(1, "in28Minutes", "Java", new Date(), true));
    	todos.add(new Todo(2, "in28Minutes", "Spring", new Date(), false));
    	todos.add(new Todo(3, "in28Minutes", "Angular", new Date(), true));
    	todos.add(new Todo(4, "in28Minutes", "Microservices", new Date(), false));
    	todos.add(new Todo(5, "in28Minutes", "FullStack", new Date(), true));
    }
 
    public void addTodo(String name ,String desc,Date targetDate,boolean isDone) {
    	todos.add(new Todo(++todoCount,name ,desc,targetDate,isDone));
    	
    }
    
   
    public List<Todo> retrieveTodos(String user){
    	List<Todo> filteredTodos =new ArrayList<Todo>();
    	for(Todo todo:todos) {
    		if(todo.getUser().equals(user)) {
    			filteredTodos.add(todo);
    		}
    	}
    	
		return filteredTodos;
		
	}
   
   
    public Todo retrieveTodo(int id) {
    	for(Todo todo:todos) 
    		if(todo.getId()==id)
    		{
    		return todo;
    	     }
    	return null;
    }
    
    public void updateTodo(Todo todo) {
    	todos.remove(todo);
    	todos.add(todo);
    }
    
	/*
	 * public Todo retrieveTodo(int id) { for (Todo todo : todos) { if (todo.getId()
	 * == id) return todo; } return null; }
	 * 
	 * public void updateTodo(Todo todo) { todos.remove(todo); todos.add(todo); }
	 */
	
      	
    public void deleteTodo(int id) {
      Iterator<Todo> iterator = todos.iterator();
      while(iterator.hasNext()) {
    	Todo todo=  iterator.next();
    	if(todo.getId()==id) {
    	  iterator.remove();
    	}
    	    	  
      }
    }
    
    
}
