package com.example.moty.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permission verification annotation
 * Used to verify if user has specified permissions
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    
    /**
     * Required permission list, user needs to have any one of these permissions
     */
    String[] value();
    
    /**
     * Whether to require all specified permissions (AND operation)
     * false: having any one permission is sufficient (OR operation)
     * true: must have all permissions (AND operation)
     */
    boolean requireAll() default false;
    
    /**
     * Error message when permissions are insufficient
     */
    String message() default "Insufficient permissions";
}
