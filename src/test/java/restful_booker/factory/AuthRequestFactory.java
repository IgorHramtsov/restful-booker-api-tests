package restful_booker.factory;

import restful_booker.config.BookerConfig;
import restful_booker.dto.request.AuthRequestDto;

public class AuthRequestFactory {

    private AuthRequestFactory() {
    }

    public static AuthRequestDto validAdminAuth() {
        AuthRequestDto request = new AuthRequestDto();
        request.setUsername(BookerConfig.get("booker.admin.username"));
        request.setPassword(BookerConfig.get("booker.admin.password"));

        return request;
    }
}
