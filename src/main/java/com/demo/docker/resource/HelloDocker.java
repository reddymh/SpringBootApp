package com.demo.docker.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/hellodocker")

public class HelloDocker{

	@GetMapping
	public String hellodocker() {
		
		return "Hello Docker";
	}
	
}

