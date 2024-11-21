package com.hzipyb.userservice.domain.user.controller;

import com.hzipyb.userservice.domain.user.dto.PasswordChangeDTO;
import com.hzipyb.userservice.domain.user.dto.UserDTO;
import com.hzipyb.userservice.domain.user.entity.User;
import com.hzipyb.userservice.domain.user.entity.UserLoginHistory;
import com.hzipyb.userservice.domain.user.exception.DuplicateUserException;
import com.hzipyb.userservice.domain.user.exception.UnAuthorizedAccessException;
import com.hzipyb.userservice.domain.user.exception.UserNotFoundException;
import com.hzipyb.userservice.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(
            @RequestBody UserDTO userDTO)
    {
        try{
            User user = userService.createUser(userDTO.getName(), userDTO.getEmail(), userDTO.getAge(), userDTO.getGender(), userDTO.getPassword());
            return ResponseEntity.ok(user);
        }catch (DuplicateUserException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long userId)
    {
        User user = userService.getUserById(userId).orElseThrow(()-> new UserNotFoundException("User not found with ID: " + userId));

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestBody UserDTO userDTO
    ){
        User user = userService.updateUser(userId, userDTO.getName(), userDTO.getEmail(), userDTO.getAge());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/login-histories")
    public ResponseEntity<List<UserLoginHistory>> getUserLoginHistories(
            @PathVariable Long userId
    ){
        List<UserLoginHistory> userLoginHistories = userService.getUserLoginHistories(userId);

        return ResponseEntity.ok(userLoginHistories);
    }

    @PostMapping("/{userId}/password-change")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long userId,
            @RequestBody PasswordChangeDTO passwordChangeDto
    ){
        try{
            userService.changePassword(userId, passwordChangeDto.getOldPassword(), passwordChangeDto.getNewPassword());
            return ResponseEntity.ok().build();
        }catch(UnAuthorizedAccessException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
