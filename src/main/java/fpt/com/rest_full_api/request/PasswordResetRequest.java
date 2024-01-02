package fpt.com.rest_full_api.request;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Data;

 

@Data
public class PasswordResetRequest {

    private String email;

    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
    private String password;

    @Size(min = 6, max = 16, message = "password confirmation must be between 6 and 16 characters long")
    @Nullable
    private String password2;
}
