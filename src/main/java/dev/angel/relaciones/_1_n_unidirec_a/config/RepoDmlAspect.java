package dev.angel.relaciones._1_n_unidirec_a.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect @Component
public class RepoDmlAspect {
    private static final Logger log = LoggerFactory.getLogger(RepoDmlAspect.class);

    // Limita a los repos del caso UNI-A
    @AfterReturning(
            "execution(* dev.angel.relaciones._1_n_unidirec_a..repository..*.save*(..)) || " +
                    "execution(* dev.angel.relaciones._1_n_unidirec_a..repository..*.delete*(..)) || " +
                    "execution(* dev.angel.relaciones._1_n_unidirec_a..repository..*.deleteAll*(..)) || " +
                    "execution(* dev.angel.relaciones._1_n_unidirec_a..repository..*.flush(..))"
    )
    public void logDml(JoinPoint jp) {
        log.info("REPO DML [{}] args={}", jp.getSignature().toShortString(),
                jp.getArgs() == null ? 0 : jp.getArgs().length);
    }
}
