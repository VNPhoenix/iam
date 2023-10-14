package vn.dangdnh.security.utils.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.dangdnh.config.JwtConfig;
import vn.dangdnh.security.utils.JwtUtils;

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
    public String generateToken(String username) {
        Date issueDate = new Date();
        long now = issueDate.getTime();
        Date expirationDate = new Date(now + config.getExpirationInMillis());
        return JWT.create()
                .withSubject(username)
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
