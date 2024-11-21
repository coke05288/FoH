package com.hzipyb.userservice.domain.user.service;

import com.hzipyb.userservice.domain.user.entity.User;
import com.hzipyb.userservice.domain.user.entity.UserLoginHistory;
import com.hzipyb.userservice.domain.user.exception.UserNotFoundException;
import com.hzipyb.userservice.domain.user.repository.UserLoginHistoryRepository;
import com.hzipyb.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(String name, String email, Integer age, String gender, String password){
        User newUser = new User();

        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setAge(age);
        newUser.setGender(gender);
        newUser.setPassword(passwordEncoder.encode(password));

        return userRepository.save(newUser);
    }

    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String userEmail){
        return userRepository.findByEmail(userEmail);
    }

    public User updateUser(Long userId, String name, String email, Integer age) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);

        return userRepository.save(user);
    }

    public List<UserLoginHistory> getUserLoginHistories(Long userId){
        return userRepository.findById(userId)
                .map(User::getLoginHistoryList)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Invalid old password");
        }
    }

    public void logUserLogin(User user, String ipAddress) {
        UserLoginHistory loginHistory = new UserLoginHistory();

        loginHistory.setUser(user);
        loginHistory.setLoginTime(LocalDateTime.now());
        loginHistory.setIpAddress(ipAddress);

        userLoginHistoryRepository.save(loginHistory);
    }
}
