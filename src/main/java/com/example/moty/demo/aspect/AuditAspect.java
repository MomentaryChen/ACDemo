package com.example.moty.demo.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.moty.demo.annotation.Audit;
import com.example.moty.demo.service.AuditLogService;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogService auditLogService;

    @Pointcut("@annotation(audit)")
    public void auditPointcut(Audit audit) {}

    @Around("auditPointcut(audit)")
    public Object around(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed(); // 執行 Controller 方法
        } finally {
            // 非同步寫入 Audit Log
            String details = Arrays.toString(joinPoint.getArgs());
            auditLogService.log(audit.action().getAction(), details);
        }
        return result;
    }
}
