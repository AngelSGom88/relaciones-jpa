package dev.angel.relaciones._n_m_bidirec.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect @Component
public class RepoDmlAspect {
    private static final Logger log = LoggerFactory.getLogger(RepoDmlAspect.class);

    // Solo repos del módulo N–M BI
    @AfterReturning(
            "execution(* dev.angel.relaciones._n_m_bidirecc..repository..*.save*(..)) || " +
                    "execution(* dev.angel.relaciones._n_m_bidirecc..repository..*.delete*(..)) || " +
                    "execution(* dev.angel.relaciones._n_m_bidirecc..repository..*.deleteAll*(..)) || " +
                    "execution(* dev.angel.relaciones._n_m_bidirecc..repository..*.flush*(..))"
    )
    public void logDml(JoinPoint jp) {
        log.info("REPO DML [{}] args={}", jp.getSignature().toShortString(),
                jp.getArgs() == null ? 0 : jp.getArgs().length);
    }
}
