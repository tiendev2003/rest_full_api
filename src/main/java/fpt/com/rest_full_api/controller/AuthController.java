package fpt.com.rest_full_api.controller;

import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
 import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fpt.com.rest_full_api.exception.UserException;
import fpt.com.rest_full_api.model.Role;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.repository.RoleRepository;
import fpt.com.rest_full_api.repository.UserRepository;
import fpt.com.rest_full_api.request.LoginRequest;
import fpt.com.rest_full_api.request.PasswordResetRequest;
import fpt.com.rest_full_api.response.ApiResponse;
import fpt.com.rest_full_api.response.AuthResponse;

import fpt.com.rest_full_api.security.JWTGenerator;
import fpt.com.rest_full_api.service.AuthenticationService;
import fpt.com.rest_full_api.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Auth API")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JWTGenerator jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CartService cartService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AuthenticationService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody UserEntity user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String phone = user.getMobile();
		UserEntity isEmailExist = userRepository.findByEmail(email);
		// Check email exists
		if (isEmailExist != null) {
			// System.out.println("--------- exist "+isEmailExist).getEmail());

			throw new UserException("Email Is Already Used With Another Account");
		}

		// Create new user
		UserEntity createdUser = new UserEntity();
		createdUser.setEmail(email);
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		createdUser.setUsername(email);
		Role roles = roleRepository.findByName("USER").get();
		createdUser.setRoles(Collections.singletonList(roles));
		createdUser.setPassword(passwordEncoder.encode(password));

		createdUser.setMobile(phone);

		UserEntity savedUser = userRepository.save(createdUser);
		cartService.createCart(savedUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token, true);
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		System.out.println(username + " && " + password);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						username,
						password));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();

		authResponse.setStatus(true);
		authResponse.setJwt(token);

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

	@PostMapping("/forgot")
	public ResponseEntity<ApiResponse> forgotPassword(@RequestParam String email, Model model) {
		if (!authService.sendPasswordResetCode(email)) {
			return new ResponseEntity<>(new ApiResponse("Email not found", false), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ApiResponse("Reset password code is send to your E-mail", true), HttpStatus.OK);
	}

	@PostMapping("/reset")
	public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody PasswordResetRequest request,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		if (authService.resetPassword(request)) {
			return new ResponseEntity<>(new ApiResponse("Password reset successfully", true), HttpStatus.OK);

		}
		return new ResponseEntity<>(new ApiResponse("Password reset failed", false), HttpStatus.BAD_REQUEST);

	}
 

	@GetMapping("/authorize/google")
	public void getMethodName() {
		 
	}

}
