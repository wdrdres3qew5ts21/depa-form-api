/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.depa.user.model.user.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Test
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Document("users")
public class UserVanila extends UserImpl{
    
    @Id
    private ObjectId userId;
    private String password;
    private String userType;
    
}
