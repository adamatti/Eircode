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
        Spark.get("/address/ie/:code") { Request req, Response res ->
            String code = req.params("code")
            handleRequest("address/ie/$code", req, res)
        }
        Spark.get("/addressgeo/ie/:code"){ Request req, Response res ->
            String code = req.params("code")
            handleRequest("addressgeo/ie/$code", req, res)
        }
        Spark.get("/position/ie/:code"){ Request req, Response res ->
            String code = req.params("code")
            handleRequest("position/ie/$code", req, res)
        }

        Spark.get("/rgeo/ie/:latitude/:longitude"){ Request req, Response res ->
            String latitude = req.params("latitude")
            String longitude = req.params("longitude")

            handleRequest("rgeo/ie/$latitude/$longitude", req, res)
        }
    }

    private String handleRequest(String path, Request req, Response res){
        assert !path.startsWith("/")

        res.header("Content-Type", DEFAULT_CONTENT_TYPE)
        Map query = req.queryParams().collectEntries{[it,req.queryParams(it)]}
        Object result = repository.get(path,query)
        toJson(result)
    }

    private String toJson(Object obj){
        new JsonBuilder(obj).toPrettyString()
    }
}
