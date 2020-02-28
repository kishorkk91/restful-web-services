package com.demo.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

// This is User Controller class which handles incoming REST request from browser and Maps it to corresponding method
@RestController
public class UserJPAResource {

	// JPA to access data from database instead of static DAO
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	// http://localhost:8080/users
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUser() {
		return userRepository.findAll();
	}

	//http://localhost:8080/users/2
	@GetMapping("/jpa/users/{id}")
	public Optional<User> findOneUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		return user;
	}

	/* We can send input request from Postman and correct HTTP code (get status 201) for CREATE (best practices of REST)
	and also return exact location of URI to know which user has sent request, and that I can find in Headers in Postman
	 * */
	//http://localhost:8080/users
	//@RequestMapping(value = "/jpa/users", method = RequestMethod.POST)
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
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
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveUserPosts(@PathVariable int id) {

		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		return userOptional.get().getPosts();
	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createUserPosts(@PathVariable int id, @RequestBody Post post) {

		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		// Get the user
		User user = userOptional.get();
		// Set or Map the user into Post
		post.setUser(user);
		// we save the post into Database
		postRepository.save(post);

		URI location = ServletUriComponentsBuilder.
				fromCurrentRequest().path("/{id}").
				buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}