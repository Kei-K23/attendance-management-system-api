package dev.kei.config;

import dev.kei.dto.RoleResponseDto;
import dev.kei.exception.MissingAuthHeaderException;
import dev.kei.service.JwtService;
import dev.kei.service.RoleService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RoleService roleService;

    public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver,
                                   JwtService jwtService,
                                   UserDetailsService userDetailsService,
                                   RoleService roleService) {
        this.jwtService = jwtService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isUnprotectedEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handleException(request, response, new MissingAuthHeaderException("Missing authorization header"));
            return;
        }

        final String jwt = authHeader.substring(7);
        try {
            String username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                processAuthentication(request, response, filterChain, jwt, username);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception ex) {
            handleException(request, response, ex);
        }
    }

    private boolean isUnprotectedEndpoint(HttpServletRequest request) {
        List<String> unprotectedEndpoints = List.of("/api/v1/auth/register", "/api/v1/auth/login");
        return unprotectedEndpoints.contains(request.getRequestURI());
    }

    private void processAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String jwt, String username) throws IOException, ServletException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Claims claims = jwtService.extractAllClaims(jwt);
        String roleId = claims.get("roleId", String.class);
        RoleResponseDto role = roleService.findById(roleId);

        if (!validateRouteForRoleBasedAuthentication(request.getRequestURI(), request.getMethod(), role.getPermissions())) {
            handleException(request, response, new MissingAuthHeaderException("Unauthorized access!"));
            return;
        }

        if (jwtService.validateToken(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request, response, null, ex);
    }

    private boolean validateRouteForRoleBasedAuthentication(String route, String method, String permissions) {
        if (permissions.equals("ALL")) {
            return true;
        }

        List<String> protectedRoutes = List.of("/api/v1/roles", "/api/v1/departments", "/api/v1/users", "/api/v1/attendance");
        for (String protectedRoute : protectedRoutes) {
            if (route.startsWith(protectedRoute) && !method.equals("GET")) {
                return false;
            }
        }

        return true;
    }
}
