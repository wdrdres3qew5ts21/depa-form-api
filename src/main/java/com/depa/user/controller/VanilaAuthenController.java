/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.depa.user.controller;

import com.depa.user.model.user.impl.UserImpl;
import com.depa.user.model.user.impl.UserVanila;
import com.depa.user.repository.UserRepository;
import com.depa.user.service.internal.TokenAuthenticationService;
import java.util.HashMap;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Test
 */
@RestController
@CrossOrigin("*")
public class VanilaAuthenController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @PostMapping("/vanila/create/user")
    public ResponseEntity createVanilaUserAuthen(@RequestBody UserVanila vanilaUserAuthen) {
        UserVanila savedVanilaUser = userRepository.save(vanilaUserAuthen);
        return new ResponseEntity(savedVanilaUser, HttpStatus.CREATED);
    }

    @PostMapping("/vanila/login/user")
    public ResponseEntity authenWithVanila(@RequestBody UserVanila userRequestForAuthen) {
        String userRequesEmail = userRequestForAuthen.getEmail();
        UserVanila vanilaUserInDatabase = userRepository.findByEmail(userRequesEmail);
        HashMap<String, String> response = new HashMap<>();

        if (vanilaUserInDatabase != null) {
            if (userRequestForAuthen.getPassword().equals(vanilaUserInDatabase.getPassword())) {
                String jwtToken = tokenAuthenticationService.createTokenUser(vanilaUserInDatabase);
                response.put("jwtToken", jwtToken);
                return new ResponseEntity(response, HttpStatus.OK);
            }
        }
        response.put("error", "not found user/ wrong password !");
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/vanila/users")
    public String getAllVanilaUsers() {
        return "lnwza vanila";
    }

}
