package uz.taskmanagementsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.taskmanagementsystem.model.User;
import uz.taskmanagementsystem.record.RegisterRequestRecord;
import uz.taskmanagementsystem.repository.UserRepository;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequestRecord RegisterRequestRecord) {
        if (RegisterRequestRecord.password() == null || RegisterRequestRecord.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        User user = new User();
        user.setUsername(RegisterRequestRecord.username());
        user.setPassword(passwordEncoder.encode(RegisterRequestRecord.password()));
        user.setFullName(RegisterRequestRecord.fullName());
        userRepository.save(user);
    }
}
