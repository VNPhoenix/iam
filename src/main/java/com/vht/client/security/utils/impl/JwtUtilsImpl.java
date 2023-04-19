package com.vht.client.security.utils.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vht.client.config.JwtConfig;
import com.vht.client.definition.TimeZones;
import com.vht.client.security.utils.JwtUtils;
import com.vht.client.utils.ZonedDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtilsImpl implements JwtUtils {

    private static final String DEV_SECRET_KEY = "Developer@12345789!";

    private final JwtConfig config;
    private final Algorithm algorithm;

    @Autowired
    public JwtUtilsImpl(JwtConfig config) {
        this.config = config;
        this.algorithm = Algorithm.HMAC512(DEV_SECRET_KEY);
    }

    @Override
    public String generateToken(Authentication auth) {
        UserDetails principal = (UserDetails) auth.getPrincipal();
        ZonedDateTime zdt = ZonedDateTimeUtils.now(TimeZones.VIETNAM);
        Date issueDate = ZonedDateTimeUtils.toDate(zdt);
        Date expirationDate = ZonedDateTimeUtils.toDate(zdt.plusSeconds(
                config.getExpirationDuration().getSeconds()));
        return JWT.create()
                .withSubject(principal.getUsername())
                .withIssuedAt(issueDate)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    @Override
    public DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    @Override
    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        return verifier.verify(token);
    }

    @Override
    public boolean isTokenExpired(DecodedJWT decodedJWT) {
        Date expirationDate = decodedJWT.getExpiresAt();
        return expirationDate.before(new Date());
    }

    @Override
    public String getUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }
}
