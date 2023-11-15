package vn.dangdnh.component;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtUtils {

    String generateToken(String username);

    DecodedJWT decodeToken(String token);

    DecodedJWT verifyToken(String token);

    boolean isTokenExpired(DecodedJWT decodedJWT);

    String getUsername(DecodedJWT decodedJWT);
}
