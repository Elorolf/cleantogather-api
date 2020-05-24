package com.yellowstone.cleantogather.api.user;

import com.yellowstone.cleantogather.api.common.exception.NotFoundException;

import com.yellowstone.cleantogather.api.common.service.email.EmailService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This means this class is a rest controller
@RequestMapping(path="/users") // This means URL's start with /events (after Application path)

@CrossOrigin
public class UserController {
	
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final EmailService emailService;

    @Autowired
    public UserController(UserRepository userRepository, ModelMapper mapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.emailService = emailService;
    }

    @ApiOperation("Create a new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public User postUser(@RequestBody User user) { return userRepository.save(user); }
    
    @ApiOperation("Get all the users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<User> getAllUsers() {


        emailService.sendSimpleMessage("florent.favole@gmail.com", "caca", "prout");
        return (List<User>) userRepository.findAll();
    }
    
    @ApiOperation("Get an user by his id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    
    @ApiOperation("Patch an existent user")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public User patchUser(@RequestBody User user, @PathVariable("id") Long id) {
        User userToUpdate = userRepository.findById(id).orElseThrow(NotFoundException::new);
        mapper.map(user, userToUpdate);
        return userRepository.save(userToUpdate);
    }

    @ApiOperation("Delete an user")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable("id") Long id) {
        User deletedUser = userRepository.findById(id).orElseThrow(NotFoundException::new);
        userRepository.deleteById(id);
        return deletedUser;
    }
}