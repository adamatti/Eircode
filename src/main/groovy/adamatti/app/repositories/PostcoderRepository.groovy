package adamatti.app.repositories

import adamatti.Config
import groovyx.net.http.RESTClient
import org.springframework.stereotype.Component

@Component
class PostcoderRepository {
    private def cfg = Config.cfg

    Object get(String path, Map query){
        query.format = "json" //format should be aways json

        getClient().get(
            path: path,
            query: query
        ).data
    }

    private RESTClient getClient(){
        String url = cfg.postcoder.baseUrl as String
        RESTClient client = new RESTClient(url)
        client.ignoreSSLIssues()
        client
    }
}
