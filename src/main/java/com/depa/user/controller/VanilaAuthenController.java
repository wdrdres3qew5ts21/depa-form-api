/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.depa.user.controller;

import com.depa.user.model.user.impl.UserImpl;
import com.depa.user.model.user.impl.UserVanila;
import com.depa.user.repository.UserRepository;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Test
 */
@RestController
public class VanilaAuthenController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/vanila/create/user")
    public ResponseEntity createVanilaUserAuthen(@RequestBody UserVanila vanilaUserAuthen) {
        UserVanila savedVanilaUser = userRepository.save(vanilaUserAuthen);
        return new ResponseEntity(savedVanilaUser, HttpStatus.CREATED);
    }

    @PostMapping("/vanila/login/user")
    public ResponseEntity authenWithVanila(@RequestBody UserVanila userRequestForAuthen) {
        String userRequesEmail = userRequestForAuthen.getEmail();
        UserVanila vanilaUserInDatabase = userRepository.findByEmail(userRequesEmail);
        if (vanilaUserInDatabase != null) {
            if (userRequestForAuthen.getPassword().equals(vanilaUserInDatabase.getPassword())) {
                return new ResponseEntity(userRequestForAuthen, HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/vanila/users")
    public String getAllVanilaUsers() {
        System.out.println("fick");
        return "lnwza vanila";
    }

}
