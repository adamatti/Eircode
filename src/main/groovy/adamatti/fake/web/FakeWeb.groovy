package adamatti.fake.web

import adamatti.Config
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import spark.Request
import spark.Response
import spark.Spark

import javax.annotation.PostConstruct

@Slf4j
@Component
class FakeWeb {
    private static final String FOLDER = "src/main/resources/fake"
    private final def cfg = Config.cfg

    @PostConstruct
    void registerFakeRoutes(){
        if (cfg.fakeEnabled as boolean) {
            Spark.get("/fake/*") { Request req, Response res ->
                String path = req.splat().join("/")

                File file = new File("${FOLDER}/${path}.json")
                if (file.exists()) {
                    res.header("Content-Type", "application/json")
                    return file.getText()
                }
                Spark.halt(404)
            }

            log.info("Fake enabled")
        }
    }
}
