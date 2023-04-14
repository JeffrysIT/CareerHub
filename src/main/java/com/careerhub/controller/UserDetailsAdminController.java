package com.careerhub.controller;

import com.careerhub.dto.UserDetailsRequestDTO;
import com.careerhub.model.UserDetails;
import com.careerhub.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/user-details")
public class UserDetailsAdminController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<UserDetails> createUser(@RequestBody UserDetailsRequestDTO userDTO) {
        UserDetails createdUser = userDetailsService.saveUserDetails(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }

}
