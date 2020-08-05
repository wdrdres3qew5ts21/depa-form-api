/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.depa.user.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.depa.user.model.user.impl.UserImpl;
import com.depa.user.model.user.impl.UserVanila;
import org.bson.types.ObjectId;

/**
 *
 * @author Test
 */
public interface UserRepository extends MongoRepository<UserVanila, ObjectId>{
    
    public UserVanila findByEmail(String email);
   
}
