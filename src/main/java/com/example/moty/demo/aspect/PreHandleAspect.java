package com.example.moty.demo.aspect;

import com.example.moty.demo.annotation.PreHandle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Pre-handle aspect
 * Handles pre-processing logic for @PreHandle annotation
 */
@Aspect
@Component
public class PreHandleAspect {

    private static final Logger logger = LoggerFactory.getLogger(PreHandleAspect.class);

    /**
     * Pre-handle pointcut
     * Intercepts all methods using @PreHandle annotation
     */
    @Before("@annotation(preHandle)")
    public void preHandle(JoinPoint joinPoint, PreHandle preHandle) {
        if (!preHandle.enabled()) {
            logger.debug("Pre-handle disabled, skipping processing");
            return;
        }

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        logger.info("Executing pre-handle - class: {}, method: {}, priority: {}",
                className, methodName, preHandle.order());

        // Specific pre-processing logic can be added here
        // For example: parameter validation, permission checks, logging, etc.

        logger.info("Pre-handle completed - class: {}, method: {}", className, methodName);
    }
}
