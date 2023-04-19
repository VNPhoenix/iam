package com.vht.client.security.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;

public interface JwtUtils {

    String generateToken(Authentication auth);

    DecodedJWT decodeToken(String token);

    DecodedJWT verifyToken(String token);

    boolean isTokenExpired(DecodedJWT decodedJWT);

    String getUsername(DecodedJWT decodedJWT);
}
