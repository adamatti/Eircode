package adamatti.app.web

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import spark.Request
import spark.Response
import spark.Spark

import javax.annotation.PostConstruct

@Slf4j
@Component
class HealthCheckWeb {
    @PostConstruct
    void registerEndpoint(){
        Spark.get("/healthCheck"){ Request req, Response res ->
            '{"status":"ok"}'
        }
    }
}
