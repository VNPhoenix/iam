package vn.dangdnh.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.dangdnh.dto.request.token.JwtTokenVerification;
import vn.dangdnh.dto.response.TokenVerification;
import vn.dangdnh.service.TokenService;

import java.io.IOException;
import java.util.List;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public JwtTokenFilter(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null) {
            String accessToken = bearerToken.substring("Bearer ".length());
            JwtTokenVerification command = new JwtTokenVerification(accessToken);
            TokenVerification result = tokenService.verifyToken(command);
            if (Boolean.TRUE.equals(result.getValid())) {
                List<SimpleGrantedAuthority> authorities = result.getAuthorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        result.getUsername(), "", authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
