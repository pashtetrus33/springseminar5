package ru.gb.spring_seminar5.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionLoggingAspect {
    @AfterThrowing(pointcut = "@annotation(ru.gb.spring_seminar5.aspects.LoggedExecution)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().toString();
        System.err.println("Exception in method " + methodName + ": " + ex.getMessage());
    }
}
