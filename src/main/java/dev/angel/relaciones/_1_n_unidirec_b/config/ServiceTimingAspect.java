package dev.angel.relaciones._1_n_unidirec_b.config;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Mide el tiempo de ejecución de métodos de servicio.
 * Aplica a cualquier clase dentro de dev.angel.relaciones..service..*
 */
@Aspect
@Component
public class ServiceTimingAspect {

    private static final Logger log = LoggerFactory.getLogger(ServiceTimingAspect.class);

    // umbral en ms para resaltar llamadas “lentas”
    private static final long WARN_THRESHOLD_MS = 150L;

    @Around("execution(* dev.angel.relaciones..service..*(..))")
    public Object timeServices(ProceedingJoinPoint pjp) throws Throwable {
        long t0 = System.nanoTime();
        try {
            return pjp.proceed();
        } finally {
            long ns = System.nanoTime() - t0;
            long ms = ns / 1_000_000L;
            String sig = pjp.getSignature().toShortString();
            if (ms >= WARN_THRESHOLD_MS) {
                log.warn("SERVICE SLOW {} took {} ms", sig, ms);
            } else {
                log.info("SERVICE {} took {} ms", sig, ms);
            }
        }
    }
}
