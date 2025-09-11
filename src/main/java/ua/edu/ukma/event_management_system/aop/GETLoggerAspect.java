package ua.edu.ukma.event_management_system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GETLoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(GETLoggerAspect.class);

    @Pointcut(value = "execution(* ua.edu.ukma.event_management_system.controller..get*(..))")
    private void getPointCut() {}

    @AfterReturning(pointcut = "getPointCut()", returning = "result")
    public void getLogging(JoinPoint joinPoint, Object result) {
        logger.info("Successfully returned {} from {} with id={}", result.getClass().getSimpleName(), joinPoint.getSignature().getName(), joinPoint.getArgs()[0]);
    }
}
