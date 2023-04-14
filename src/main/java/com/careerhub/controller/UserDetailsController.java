package com.careerhub.controller;

import com.careerhub.dto.UserDetailsRequestDTO;
import com.careerhub.model.UserDetails;
import com.careerhub.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable("id") Long id) {
        UserDetails user = userDetailsService.getUserDetailsById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetails> updateUser(@PathVariable("id") Long id, @RequestBody UserDetailsRequestDTO userDTO) {
        UserDetails updatedUser = userDetailsService.updateUserDetails(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

}
