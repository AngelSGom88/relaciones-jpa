// src/main/java/dev/angel/relaciones/_1_n_bidirec/config/RepoDmlAspect.java
package dev.angel.relaciones._1_n_bidirec.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepoDmlAspect {

    private static final Logger log = LoggerFactory.getLogger(RepoDmlAspect.class);

    // Log para operaciones de mutación en repos (save*/delete*)
    @AfterReturning(
            "execution(* dev.angel.relaciones._1_n_bidirec.repository..*.save*(..)) || " +
                    "execution(* dev.angel.relaciones._1_n_bidirec.repository..*.delete*(..))")
    public void logRepoMutation(JoinPoint jp) {
        log.info("[REPO] {}", jp.getSignature());
    }
}
