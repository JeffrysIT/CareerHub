package com.careerhub.controller;

import com.careerhub.dto.UserDetailsRequestDTO;
import com.careerhub.model.UserDetails;
import com.careerhub.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserDetailsById(@PathVariable("id") Long id) {
        UserDetails userDetails = userDetailsService.getUserDetailsById(id);
        return ResponseEntity.ok(userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetails> updateUserDetails(@PathVariable("id") Long id,
                                                         @RequestBody UserDetailsRequestDTO userDetailsDTO) {
        UserDetails updatedUserDetails = userDetailsService.updateUserDetails(id, userDetailsDTO);
        return ResponseEntity.ok(updatedUserDetails);
    }

    @PostMapping
    public ResponseEntity<UserDetails> createUserDetails(@RequestBody UserDetailsRequestDTO userDetailsDTO) {
        UserDetails createdUserDetails = userDetailsService.saveUserDetails(userDetailsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable("id") Long id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }

}
