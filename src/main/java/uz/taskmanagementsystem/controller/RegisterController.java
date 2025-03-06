package uz.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.taskmanagementsystem.record.RegisterRequestRecord;
import uz.taskmanagementsystem.service.RegisterService;
import uz.taskmanagementsystem.service.UserService;

@Controller
@RequestMapping("/api/register")
@Tag(name = "Authentication", description = "Endpoints for user registration")
public class RegisterController {

    private final RegisterService registerService;
    private final UserService userService;

    public RegisterController(RegisterService registerService, UserService userService) {
        this.registerService = registerService;
        this.userService = userService;
    }

    @Operation(summary = "User Registration", description = "Registers a new user in the system.")
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody RegisterRequestRecord registerRequest) {
        if (userService.existsByUsername(registerRequest.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        registerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }
}
