package  fpt.com.rest_full_api.service;

import fpt.com.rest_full_api.request.PasswordResetRequest;

public interface AuthenticationService {

    Boolean sendPasswordResetCode(String email);

    String getEmailByPasswordResetCode(String code);

    Boolean resetPassword(PasswordResetRequest request);
}
