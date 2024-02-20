package vn.dangdnh.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.dangdnh.dto.request.token.JwtTokenVerificationCommand;
import vn.dangdnh.dto.response.TokenVerification;
import vn.dangdnh.service.TokenService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final List<String> antMatchersNotFiltered;
    private final AntPathMatcher antPathMatcher;

    public JwtTokenFilter(final TokenService tokenService,
                          final List<String> antMatchersNotFiltered) {
        this.tokenService = tokenService;
        this.antMatchersNotFiltered = antMatchersNotFiltered != null
                ? antMatchersNotFiltered : Collections.emptyList();
        this.antPathMatcher = new AntPathMatcher();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return antMatchersNotFiltered.stream()
                .anyMatch(e -> antPathMatcher.match(e, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getToken(httpServletRequest);
        if (token != null) {
            JwtTokenVerificationCommand command = new JwtTokenVerificationCommand(token);
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

    private String getToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerValue != null && headerValue.startsWith("Bearer")) {
            return headerValue.substring("Bearer ".length());
        }
        return null;
    }
}
