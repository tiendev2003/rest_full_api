package fpt.com.rest_full_api.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import fpt.com.rest_full_api.model.Order;
import fpt.com.rest_full_api.model.UserEntity;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private UserEntity user;
    private Page<Order> orders;
}
