package com.example.moty.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Role permission verification annotation
 * Used to verify if user has specified role permissions
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
    
    /**
     * Required role list, user needs to have any one of these roles
     */
    String[] value();
    
    /**
     * Whether to require all specified roles (AND operation)
     * false: having any one role is sufficient (OR operation)
     * true: must have all roles (AND operation)
     */
    boolean requireAll() default false;
    
    /**
     * Error message when permissions are insufficient
     */
    String message() default "Insufficient permissions";
}
