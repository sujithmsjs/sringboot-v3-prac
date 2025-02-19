package com.sujix.sbfeatures.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.sujix.sbfeatures.client.UserClient;
import com.sujix.sbfeatures.model.Response;
import com.sujix.sbfeatures.model.User;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v2/users")
public class UserControllerV2 {

	@Autowired
	private WebClient userClient;
	
	@Autowired
	private UserClient client;

	@GetMapping
	public Flux<User> getUsers() {
		return userClient.get().retrieve().bodyToFlux(User.class);
	}

	@GetMapping("/{id}")
	public Mono<User> getUsersV4(@PathVariable("id") int id) {
		log.info("Inside getUsersV4 with id {}", id);
		return client.getUserById(id).onErrorComplete();
	}
	
	@PostMapping
	public Mono<User> saveUser( @RequestBody User user) {
		log.info("Inside saveUser {}", user);
		return client.saveUser(user);
	}
	
	@DeleteMapping("/{id}")
	public Mono<User> deleteUser(@PathVariable("id") int id) {
		log.info("Inside deleteUser with id {}", id);
		return client.deleteUser(id).onErrorReturn(new User());
	}
	
	@PutMapping("/{id}")
	Mono<User> updateUser(@PathVariable("id") int id, @RequestBody User user){
		log.info("Inside updateUser with id {}, payload {}", id, user);
		return client.updateUser(id, user);
	}
	
	@PatchMapping("/{id}")
	Mono<User> patchUser(@PathVariable("id") int id, @RequestBody User user){
		return client.patchUser(id, user);
	}
}
