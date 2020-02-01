package com.demo.webservices.restfulwebservices.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ApiModel(description = "All details about the user !")
@Entity // User entity is managed by JPA
public class User {

	@Id
    @GeneratedValue
	private Integer id;

	@Size(min = 3, message = "Name should have at least 3 characters")
	@ApiModelProperty(notes = "Name should have at least 3 characters")
	private String name;

	@Past
	@ApiModelProperty(notes = "birthdate cannot be in future")
	private Date birthDate;

	@OneToMany(mappedBy = "user")
	private List<Post> posts;

	protected User(){
			}

	public User(Integer id, String name, Date birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", birthDate=" + birthDate +
				'}';
	}


}
