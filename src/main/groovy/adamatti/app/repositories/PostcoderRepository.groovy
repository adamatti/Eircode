package adamatti.app.repositories

import adamatti.Config
import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient
import org.springframework.stereotype.Component

@Slf4j
@Component
class PostcoderRepository {
    private def cfg = Config.cfg

    Object get(String path, Map query = [:]){
        assert !path.startsWith("/")

        query.format = "json" //format should be aways json

        log.trace("Calling ${cfg.postcoder.baseUrl}${path}, query: ${query}")

        Object result = getClient().get(
            path: path,
            query: query
        ).data

        if (log.isTraceEnabled()){
            log.trace("Response: ${result}")
        }

        result
    }

    private RESTClient getClient(){
        String url = cfg.postcoder.baseUrl as String
        RESTClient client = new RESTClient(url)
        client.ignoreSSLIssues()
        client
    }
}
