package com.demo.webservices.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// This notation tells Spring-Boot to handle REST request which is coming from Browser
@RestController
public class HelloWorldController {
	//Mapping of GET request from browser to the following URI
	@GetMapping(path = "/hello-world")
	public String helloWorld(){
		return "This is Hello-World text";
	}

	//Demo to return a Bean back and automatically convert as a JSON response on Browser window
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean(){
		return new HelloWorldBean("This is Hello-World text of BEAN");
	}

	//How to access Path Variable or Path Parameter
	// Example: http://localhost:8080/hello-world/path-variable/kishor
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
		return new HelloWorldBean(String.format("This is Hello-World text Mr./Ms. %s", name));
	}
}
