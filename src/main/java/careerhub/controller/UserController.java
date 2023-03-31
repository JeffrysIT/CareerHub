package careerhub.controller;

import careerhub.model.UserDetails;
import careerhub.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-details")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping
    public List<UserDetails> getAllUsers() {
        return userDetailsService.getAllUserDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable("id") Long id) {
        UserDetails user = userDetailsService.getUserDetailsById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails user) {
        UserDetails createdUser = userDetailsService.saveUserDetails(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetails> updateUser(@PathVariable("id") Long id, @RequestBody UserDetails user) {
        UserDetails updatedUser = userDetailsService.updateUserDetails(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }

}

