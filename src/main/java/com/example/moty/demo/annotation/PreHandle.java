package com.example.moty.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom pre-handle annotation
 * Used for pre-processing operations before method execution
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreHandle {
    
    /**
     * Whether to enable pre-handle processing
     */
    boolean enabled() default true;
    
    /**
     * Processing priority, lower values have higher priority
     */
    int order() default 0;
}
