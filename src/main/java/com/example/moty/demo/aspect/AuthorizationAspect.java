package com.example.moty.demo.aspect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.moty.demo.annotation.RequireAuth;
import com.example.moty.demo.annotation.RequirePermission;
import com.example.moty.demo.annotation.RequireRole;
import com.example.moty.demo.enums.HttpStatusEnum;
import com.example.moty.demo.util.ApiResponseUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Authorization aspect
 * Handles authentication and authorization related annotations
 */
@Aspect
@Component
@Slf4j
public class AuthorizationAspect {
    
    /**
     * Handle @RequireAuth annotation
     */
    @Around("@annotation(requireAuth)")
    public Object handleRequireAuth(ProceedingJoinPoint joinPoint, RequireAuth requireAuth) throws Throwable {
        log.debug("Executing authentication check - method: {}", joinPoint.getSignature().getName());
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("User not authenticated - method: {}", joinPoint.getSignature().getName());
            return ApiResponseUtil.error(HttpStatusEnum.UNAUTHORIZED, requireAuth.message());
        }
        
        log.debug("Authentication check passed - user: {}", authentication.getName());
        return joinPoint.proceed();
    }
    
    /**
     * Handle @RequireRole annotation
     */
    @Around("@annotation(requireRole)")
    public Object handleRequireRole(ProceedingJoinPoint joinPoint, RequireRole requireRole) throws Throwable {
        log.debug("Executing role permission check - method: {}, required roles: {}", 
                    joinPoint.getSignature().getName(), Arrays.toString(requireRole.value()));
        
        // Check authentication first
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("User not authenticated - method: {}", joinPoint.getSignature().getName());
            return ApiResponseUtil.error(HttpStatusEnum.UNAUTHORIZED, "Not logged in");
        }
        
        // Check role permissions
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> userRoles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        
        Set<String> requiredRoles = Arrays.stream(requireRole.value())
                .collect(Collectors.toSet());
        
        boolean hasPermission;
        if (requireRole.requireAll()) {
            // AND operation: must have all roles
            hasPermission = userRoles.containsAll(requiredRoles);
        } else {
            // OR operation: having any one role is sufficient
            hasPermission = requiredRoles.stream().anyMatch(userRoles::contains);
        }
        
        if (!hasPermission) {
            log.warn("Insufficient role permissions - user: {}, has roles: {}, required roles: {}", 
                       authentication.getName(), userRoles, requiredRoles);
            return ApiResponseUtil.error(HttpStatusEnum.FORBIDDEN, requireRole.message());
        }
        
        log.debug("Role permission check passed - user: {}", authentication.getName());
        return joinPoint.proceed();
    }
    
    /**
     * Handle @RequirePermission annotation
     */
    @Around("@annotation(requirePermission)")
    public Object handleRequirePermission(ProceedingJoinPoint joinPoint, RequirePermission requirePermission) throws Throwable {
        log.debug("Executing permission check - method: {}, required permissions: {}", 
                    joinPoint.getSignature().getName(), Arrays.toString(requirePermission.value()));
        
        // Check authentication first
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("User not authenticated - method: {}", joinPoint.getSignature().getName());
            return ApiResponseUtil.error(HttpStatusEnum.UNAUTHORIZED, "Not logged in");
        }
        
        // Check permissions
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> userPermissions = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        
        Set<String> requiredPermissions = Arrays.stream(requirePermission.value())
                .collect(Collectors.toSet());
        
        boolean hasPermission;
        if (requirePermission.requireAll()) {
            // AND operation: must have all permissions
            hasPermission = userPermissions.containsAll(requiredPermissions);
        } else {
            // OR operation: having any one permission is sufficient
            hasPermission = requiredPermissions.stream().anyMatch(userPermissions::contains);
        }
        
        if (!hasPermission) {
            log.warn("Insufficient permissions - user: {}, has permissions: {}, required permissions: {}", 
                       authentication.getName(), userPermissions, requiredPermissions);
            return ApiResponseUtil.error(HttpStatusEnum.FORBIDDEN, requirePermission.message());
        }
        
        log.debug("Permission check passed - user: {}", authentication.getName());
        return joinPoint.proceed();
    }
}
