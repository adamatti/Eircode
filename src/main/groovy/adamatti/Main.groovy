package adamatti

import groovy.util.logging.Slf4j
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericGroovyApplicationContext

@Slf4j
class Main {
    static void main (String [] args){
        log.info("Starting")
        buildSpringContext()
        log.info("Started")
    }

    static ApplicationContext buildSpringContext(){
        ApplicationContext ctx = new GenericGroovyApplicationContext("classpath:spring.groovy")
        ctx.registerShutdownHook()
        ctx
    }
}
