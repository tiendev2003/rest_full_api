package fpt.com.rest_full_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.com.rest_full_api.exception.UserException;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.request.PasswordResetRequest;
import fpt.com.rest_full_api.response.ApiResponse;
import fpt.com.rest_full_api.service.AuthenticationService;
import fpt.com.rest_full_api.service.UserService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authService;

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsersHandler() throws UserException {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> getUserProfileHandler(@RequestHeader("Authorization") String jwt)
            throws UserException {

        System.out.println("/api/users/profile");
        UserEntity user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<UserEntity>(user, HttpStatus.ACCEPTED);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserEntity> updateUserProfileHandler(
            @RequestHeader("Authorization") String jwt,
            @RequestBody UserEntity updatedUser) throws UserException {
        UserEntity user = userService.updateUserProfileByJwt(jwt, updatedUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> postMethodName(@Valid @RequestBody PasswordResetRequest request,
            BindingResult result) {
        authService.resetPassword(request);
        ApiResponse response = new ApiResponse();
        response.setMessage("Change password successfully");
        response.setStatus(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
