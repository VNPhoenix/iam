package vn.dangdnh.config.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;

public class NotFilteredPredicate implements Predicate<HttpServletRequest> {

    private final List<String> antMatchersNotFiltered;
    private final AntPathMatcher antPathMatcher;

    @Autowired
    public NotFilteredPredicate(final List<String> antMatchersNotFiltered) {
        this.antMatchersNotFiltered = antMatchersNotFiltered;
        this.antPathMatcher = new AntPathMatcher();
    }

    @Override
    public boolean test(HttpServletRequest httpServletRequest) {
        return antMatchersNotFiltered.stream()
                .anyMatch(e -> antPathMatcher.match(e, httpServletRequest.getRequestURI()));
    }
}
