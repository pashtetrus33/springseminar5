package ru.gb.spring_seminar5.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Pointcut("within(@ru.gb.spring_seminar5.aspects.TrackUserAction *)")
    public void beansAnnotatedWith() {
    }

    @Pointcut("@annotation(ru.gb.spring_seminar5.aspects.TrackUserAction)")
    public void methodsAnnotatedWith() {
    }

    @Around("beansAnnotatedWith() || methodsAnnotatedWith()")
    public Object loggableAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        Level level = extractLevel(joinPoint);

        log.atLevel(level).log("target = {}", joinPoint.getTarget().getClass());
        log.atLevel(level).log("method = {}", joinPoint.getSignature().getName());
        log.atLevel(level).log("args = {}", Arrays.toString(joinPoint.getArgs()));
        try {
            Object returnValue = joinPoint.proceed();
            log.atLevel(level).log("result = {}", returnValue);
            return returnValue;
        } catch (Throwable e) {
            log.atLevel(level).log("exception = [{}, {}]", e.getClass(), e.getMessage());
            throw e;
        }
    }

    private Level extractLevel(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        TrackUserAction annotation = signature.getMethod().getAnnotation(TrackUserAction.class);
        if (annotation != null) {
            return annotation.level();
        }

        return joinPoint.getTarget().getClass().getAnnotation(TrackUserAction.class).level();
    }
}
