package dev.angel.relaciones._1_n_unidirec_b.config;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Log sencillo para operaciones DML de repositorios.
 * Aplica a métodos {@code save*}, {@code delete*}, {@code deleteAll*}, {@code flush()}
 * dentro de {@code dev.angel.relaciones..repository..*}
 */
        @Aspect
        @Component
        public class RepoDmlAspect {

            private static final Logger log = LoggerFactory.getLogger(RepoDmlAspect.class);

            // save*, delete*, deleteAll*, flush()
            @AfterReturning("execution(* dev.angel.relaciones..repository..*.save*(..)) || " +
                    "execution(* dev.angel.relaciones..repository..*.delete*(..)) || " +
                    "execution(* dev.angel.relaciones..repository..*.deleteAll*(..)) || " +
                    "execution(* dev.angel.relaciones..repository..*.flush(..))")
            public void logDml(JoinPoint jp) {
                String sig = jp.getSignature().toShortString();
                Object[] args = jp.getArgs();
                // No imprimimos entidades completas para no ensuciar logs, solo tamaños aproximados
                int argc = (args == null) ? 0 : args.length;
                log.info("REPO DML {}", sig + " args=" + argc);
            }
        }
