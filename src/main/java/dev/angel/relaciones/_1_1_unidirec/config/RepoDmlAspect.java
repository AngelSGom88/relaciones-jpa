package dev.angel.relaciones._1_1_unidirec.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepoDmlAspect {

    private static final Logger log = LoggerFactory.getLogger(RepoDmlAspect.class);

    // Todos los métodos bajo ..._1_1_unidirec...repository...
    @Pointcut("execution(* dev.angel.relaciones._1_1_unidirec..repository..*(..))")
    public void repoMethods() {}

    @Before("repoMethods()")
    public void beforeRepo() {
        log.info("→ Repository call");
    }

    @After("repoMethods()")
    public void afterRepo() {
        log.info("← Repository done");
    }
}