package dev.angel.relaciones._1_1_bidirec.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Log de operaciones DML en repos del módulo 1-1 BI.
 * Aplica a métodos {@code save*}, {@code delete*}, {@code deleteAll*}, {@code flush()}.
 */
@Aspect @Component
public class RepoDmlAspect {
    private static final Logger log = LoggerFactory.getLogger(RepoDmlAspect.class);

    @AfterReturning(
            "execution(* dev.angel.relaciones._1_1_bidirec..repository..*.save*(..)) || " +
                    "execution(* dev.angel.relaciones._1_1_bidirec..repository..*.delete*(..)) || " +
                    "execution(* dev.angel.relaciones._1_1_bidirec..repository..*.deleteAll*(..)) || " +
                    "execution(* dev.angel.relaciones._1_1_bidirec..repository..*.flush(..))"
    )
    public void logDml(JoinPoint jp) {
        log.info("REPO DML [{}] args={}", jp.getSignature().toShortString(),
                jp.getArgs() == null ? 0 : jp.getArgs().length);
    }
}
