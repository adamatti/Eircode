package adamatti.app.web

import adamatti.Config
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import spock.lang.Specification
import spock.lang.Unroll

class PostcoderWebTest extends Specification{
    @Unroll
    def "test endpoints (#url)"(){
        when:
            def result = call(url)
        then:
            result != null
            result.getStatus() == statusCode
        where:
            url                            | statusCode | query
            "/address/ie/D02X285"          | 200        | [:]
            "/addressgeo/ie/D02X285"       | 200        | [:]
            "/position/ie/D02X285"         | 200        | [:]
            "/rgeo/ie/53.332067/-6.255492" | 200        | [distance:50]
    }

    private HttpResponseDecorator call(String url){
        int port = Config.cfg.port

        RESTClient client = new RESTClient("http://localhost:${port}")
        client.get(path: url)
    }
}
