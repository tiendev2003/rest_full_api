package fpt.com.rest_full_api.service.ịmpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.repository.UserRepository;
import fpt.com.rest_full_api.request.PasswordResetRequest;
import fpt.com.rest_full_api.security.MailService;
import fpt.com.rest_full_api.service.AuthenticationService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    @Transactional
    public Boolean sendPasswordResetCode(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        user.setPasswordResetCode(UUID.randomUUID().toString());
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", user.getFirstName());
        attributes.put("resetCode", "/auth/reset/" + user.getPasswordResetCode());
        mailService.sendMessageHtml(user.getEmail(), "Password reset", "password-reset-template", attributes);
        return true;
    }

    @Override
    public String getEmailByPasswordResetCode(String code) {
        return userRepository.getEmailByPasswordResetCode(code)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));
    }

    @Override
    @Transactional
    public Boolean resetPassword(PasswordResetRequest request) {
        if (request.getPassword() != null ) {
            return false;
        }
        UserEntity user = userRepository.findByEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPasswordResetCode(null);
      
        return true;
    }
}
