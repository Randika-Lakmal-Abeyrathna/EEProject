package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.dto.UserRequest;
import com.randikalakmal.adminservice.model.User;
import com.randikalakmal.adminservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList  = userService.getAllUsers();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<User> getUser(@PathVariable("email") String email){
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest){
        User addedUser = userService.addUser(userRequest);
        return new ResponseEntity<>(addedUser,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserRequest userRequest){
        User updatedUser = userService.updateUser(userRequest);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @PostMapping("/sendActivate")
    public ResponseEntity<?> sendActivationEmail(@RequestParam("email") String email){
        userService.sendUserActivationEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/accountverification/{token}")
    public ResponseEntity<?> activateUser(@PathVariable("token") String token){
        userService.activateUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update/image")
    public ResponseEntity<?> updateImage(@RequestParam("email") String email, MultipartFile image){
        userService.updateUserImage(email,image);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
