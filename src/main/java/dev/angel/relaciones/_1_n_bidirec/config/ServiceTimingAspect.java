// src/main/java/dev/angel/relaciones/_1_n_bidirec/config/ServiceTimingAspect.java
package dev.angel.relaciones._1_n_bidirec.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceTimingAspect {

    private static final Logger log = LoggerFactory.getLogger(ServiceTimingAspect.class);

    // Mide la duración de TODOS los métodos de la capa service de este módulo
    @Around("execution(* dev.angel.relaciones._1_n_bidirec.service..*(..))")
    public Object timeServices(ProceedingJoinPoint pjp) throws Throwable {
        long t0 = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long ms = System.currentTimeMillis() - t0;
            log.info("[SERVICE] {} took {} ms", pjp.getSignature(), ms);
        }
    }
}
