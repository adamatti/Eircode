package adamatti

import groovy.util.logging.Slf4j
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericGroovyApplicationContext
import spark.Spark

@Slf4j
class Main {
    static def cfg = Config.cfg

    static void main (String [] args){
        log.info("Starting")
        startSpark()
        buildSpringContext()
        log.info("Started")
    }

    static void startSpark(){
        Spark.port(cfg.port as int)
    }

    static ApplicationContext buildSpringContext(){
        ApplicationContext ctx = new GenericGroovyApplicationContext("classpath:spring.groovy")
        ctx.registerShutdownHook()
        ctx
    }
}
