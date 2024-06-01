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
        if (request.getRequestURI().equals("/api/v1/auth/register")
                || request.getRequestURI().equals("/api/v1/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handlerExceptionResolver.resolveException(request, response, null, new MissingAuthHeaderException("Missing authorization header"));
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwt);
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Extract the role to validate role-based authentication
                Claims claims = jwtService.extractAllClaims(jwt);
                String roleId = claims.get("roleId").toString();
                RoleResponseDto role = roleService.findById(roleId);
                String permission = role.getPermissions();

                if (!validateRouteForRoleBasedAuthentication(request.getRequestURI(), request.getMethod(), permission)) {
                    handlerExceptionResolver.resolveException(request, response, null, new MissingAuthHeaderException("Unauthorized access!"));
                    return;
                }

                if (jwtService.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private boolean validateRouteForRoleBasedAuthentication(String route, String method, String permissions) {
        if (route.startsWith("/api/v1/roles")) {
            if (!permissions.equals("ALL")) {
                return method.equals("GET");
            }
        }

        if (route.startsWith("/api/v1/departments")) {
            if (!permissions.equals("ALL")) {
                return method.equals("GET");
            }
        }

        if (route.startsWith("/api/v1/users")) {
            if (!permissions.equals("ALL")) {
                return method.equals("GET");
            }
        }

        if (route.startsWith("/api/v1/attendance")) {
            if (!permissions.equals("ALL")) {
                return method.equals("GET");
            }
        }

        return true;
    }
}
