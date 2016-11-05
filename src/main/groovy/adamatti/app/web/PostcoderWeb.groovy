package adamatti.app.web

import adamatti.app.repositories.PostcoderRepository
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import spark.Request
import spark.Response
import spark.Spark

import javax.annotation.PostConstruct

@Slf4j
@Component
class PostcoderWeb {
    private static String DEFAULT_CONTENT_TYPE = "application/json"

    @Autowired
    private PostcoderRepository repository

    @PostConstruct
    void registerEndpoint(){
        Closure handleCode = { Request req, Response res ->
            String code = req.params("code")

            res.header("Content-Type", DEFAULT_CONTENT_TYPE)
            Map query = req.queryParams().collectEntries{[it,req.queryParams(it)]}
            Object result = repository.get("address/ie/$code",query)
            toJson(result)
        }

        //Sample http://localhost:8080/address/ie/D02X285
        Spark.get("/address/ie/:code", handleCode)
        Spark.get("/addressgeo/ie/:code", handleCode)
        Spark.get("/position/ie/:code", handleCode)
    }

    private String toJson(Object obj){
        new JsonBuilder(obj).toPrettyString()
    }
}
