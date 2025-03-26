package com.jihun.springsecuritystudyproject.global.security.details;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String secretKey;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // 클라이언트 요청으로 들어온 파라미터 값들을 저장 ( 여기서는 secret_key )
        this.secretKey = request.getParameter("secret_key");
    }
}
