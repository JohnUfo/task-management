package uz.taskmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.taskmanagementsystem.record.LoginRequestRecord;
import uz.taskmanagementsystem.service.LoginService;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(summary = "User Login", description = "Authenticates a user and returns a JWT token upon success.")
    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequestRecord LoginRequestRecord) {
        String token = loginService.verify(LoginRequestRecord);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.ok(token);
    }
}
