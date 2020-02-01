package com.demo.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// We could also use @Repository notation but we will use static Array list instead of JPA
@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();

	private static Integer userCounter = 3;

	static {
		users.add(new User(1, "KISHOR", new Date()));
		users.add(new User(2, "Priyanka", new Date()));
		users.add(new User(3, "Akshay", new Date()));
	}

	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++userCounter);
		}
		users.add(user);
		return user;
	}

	public User fineOne(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public User deleteById(int id) {
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}

	// Now create User resource as our REST controller
}