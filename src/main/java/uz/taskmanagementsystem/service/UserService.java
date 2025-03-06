package uz.taskmanagementsystem.service;

import org.springframework.stereotype.Service;
import uz.taskmanagementsystem.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}