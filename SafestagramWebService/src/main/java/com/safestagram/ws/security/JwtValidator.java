package com.safestagram.ws.security;

import com.safestagram.ws.persistence.entity.UserAccountEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("{auth.jwt.tokenSecret}")
    private String tokenSecret;

    public UserAccountEntity validate(String token){

        UserAccountEntity user = null;

        Claims body = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        user = new UserAccountEntity();

        user.setUsername(body.getSubject());
        user.setId(((Number) body.get("userId")).longValue());

        return user;
    }


}
