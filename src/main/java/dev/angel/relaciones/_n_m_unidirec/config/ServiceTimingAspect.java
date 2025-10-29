package dev.angel.relaciones._n_m_unidirec.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect @Component
public class ServiceTimingAspect {
    private static final Logger log = LoggerFactory.getLogger(ServiceTimingAspect.class);
    private static final long WARN_THRESHOLD_MS = 200L;

    // Solo services del módulo N–M BI
    @Around("execution(* dev.angel.relaciones._n_m_bidirecc..service..*(..))")
    public Object medir(ProceedingJoinPoint pjp) throws Throwable {
        long t0 = System.nanoTime();
        try { return pjp.proceed(); }
        finally {
            long ms = (System.nanoTime() - t0) / 1_000_000L;
            String sig = pjp.getSignature().toShortString();
            if (ms >= WARN_THRESHOLD_MS) log.warn("SERVICE SLOW [{}] {} ms", sig, ms);
            else                         log.info("SERVICE [{}] {} ms", sig, ms);
        }
    }
}
