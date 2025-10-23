package br.com.devjobs.api.devjobs_api.controller;

import br.com.devjobs.api.devjobs_api.dto.UserResponseDTO;
import br.com.devjobs.api.devjobs_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import br.com.devjobs.api.devjobs_api.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class
UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
        try {
            var result = this.userService.execute(user);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        var users = this.userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }
}
