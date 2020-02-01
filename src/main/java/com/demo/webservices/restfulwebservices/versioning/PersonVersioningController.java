package com.demo.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	// URI Versioning ex. used by Twitter
	@GetMapping("v1/person")
	public PersonV1 personV1(){
		return new PersonV1("Kishor Jadhav");
	}

	@GetMapping("v2/person")
	public PersonV2 personV2(){
		return new PersonV2( new Name("Kishor","Jadhav"));
	}

	// Request Parameter versioning ex. Amazon
	@GetMapping(value="/person/param", params = "version=1")
	public PersonV1 paramV1(){
		return new PersonV1("Kishor Jadhav");
	}

	@GetMapping(value="/person/param", params = "version=2")
	public PersonV2 paramV2(){
		return new PersonV2( new Name("Kishor","Jadhav"));
	}

	// Header versioning (parameter in the request header) ex. Microsoft
	@GetMapping(value="/person/header", headers = "X-API-VERSION=1")
	public PersonV1 headerV1(){
		return new PersonV1("Kishor Jadhav");
	}

	@GetMapping(value="/person/header", headers = "X-API-VERSION=2")
	public PersonV2 headerV2(){
		return new PersonV2( new Name("Kishor","Jadhav"));
	}

	// Produces versioning (parameter is in the request header) also called as MIME-type/Accept-Header Versioning
	// Content Negotiation or accept header ex. GitHub
	@GetMapping(value="/person/produces", produces = "application/vnd.company.app-v1+json")
	public PersonV1 producesV1(){
		return new PersonV1("Kishor Jadhav");
	}

	@GetMapping(value="/person/produces", produces = "application/vnd.company.app-v2+json")
	public PersonV2 producesV2(){
		return new PersonV2( new Name("Kishor","Jadhav"));
	}

/*
* Pros and Cons using above 4 approaches
* 1. URI pollution
* --> URI and Request Param versioning we are polluting the URI space however, no pollution in other 2 versioning at all
* 2. Misuse of HTTP Headers
* --> but Header and MIME is called misuse of HTTP headers, cause HTTP header were never intended for Versioning
* 3. Cashing
* --> Header and MIME cannot deal with Cashing because they have same URI, you always need to look at Header and stuff
* 4. Excecute request on browser
* --> Cannot execute Header and MIME request on browser, they always need REST service client like POSTMAN
* 5. API documentation
*  --> Its easy to generate documentation for URI and Request Param. versioning cause we can directly generate from code
*
* Conclusion: There is no solution which would fit everybody, so before you start building any APT try testing different
*             which is would feet your needs versioning
*
* */


}
