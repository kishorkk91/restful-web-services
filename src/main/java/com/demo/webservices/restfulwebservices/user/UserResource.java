package com.demo.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// This is User Controller class which handles incoming REST request from browser and Maps it to corresponding method
@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;

	// http://localhost:8080/users
	@GetMapping("/users")
	public List<User> retrieveAllUser() {
		return service.findAll();
	}

	//http://localhost:8080/users/2
	@GetMapping("/users/{id}")
	public User findOneUser(@PathVariable int id) {
		User user = service.fineOne(id);
		if(user==null){
			throw new UserNotFoundException("id-"+id);
		}
		return user;
	}

	/* We can send input request from Postman and correct HTTP code (get status 201) for CREATE (best practices of REST)
	and also return exact location of URI to know which user has sent request, and that I can find in Headers in Postman
	 * */
	//http://localhost:8080/users
	//@PostMapping("/users")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		//CREATEDUserResource
		//  /user/{id}   savedUser.getId()
		URI location = ServletUriComponentsBuilder.
				fromCurrentRequest().path("/{id}").
				buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

 	// DELETE resource
	//http://localhost:8080/users/3
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		if(user==null){
			throw new UserNotFoundException("id-"+id);
		}
		return ResponseEntity.noContent().build();
	}
}
