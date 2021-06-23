package com.safestagram.ws.security;


import com.safestagram.ws.model.TokenModel;
import com.safestagram.ws.persistence.entity.UserAccountEntity;
import com.safestagram.ws.persistence.entity.UserEntity;
import com.safestagram.ws.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtGenerator {

    private final UserService userService;
    @Value("{auth.jwt.tokenSecret}")
    private String tokenSecret;

    public TokenModel generate(UserAccountEntity userAccount) {
        Claims claims = Jwts.claims()
                .setSubject(userAccount.getUsername());

        UserEntity user = userService.getByAccount(userAccount);

        claims.put("userId", user.getId());

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();

        return new TokenModel(token);
    }
}
