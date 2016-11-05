package adamatti.app.repositories

import adamatti.Config
import adamatti.fake.web.FakeWeb
import groovy.util.logging.Slf4j
import spark.Spark
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class PostcoderRepositoryTest extends Specification {
    def setupSpec(){
        log.info("Start Fake")
        def cfg = Config.cfg
        assert cfg.fakeEnabled
        Spark.port(cfg.port as int)
        new FakeWeb().registerFakeRoutes()
    }

    def cleanupSpec() {
        log.info("Stop spark")
        Spark.stop()
    }

    @Unroll
    def "test address [#url/#code, #lines]"(){
        given:
            PostcoderRepository repository = new PostcoderRepository()
        when:
            query << [lines:lines]
            Object result = repository.get("$url/$code",query)
        then:
            result != null
            result.first().county != null
        where:
            url             | code               | lines  | query
            "address/ie"    | "D02X285"          | 3      | [:]
            "address/uk"    | "NR147PZ"          | 1      | [:]
            "address/uk"    | "manor farm barns" | 1      | [:]
            "addressgeo/ie" | "Adelaide Road"    | 1      | [:]
            "addressgeo/ie" | "Adelaide Road"    | 1      | [addtags:"w3w"]
    }

    @Unroll
    def "test positions (#code)"(){
        given:
            PostcoderRepository repository = new PostcoderRepository()
        when:
            Object result = repository.get("$url/$code",[:])
        then:
            result != null
            result.first().latitude != null
        where:
            url           | code
            "position/ie" | "D02X285"
     }

    @Unroll
    def "test reverse geo (#latitude, #longitude)" (){
        given:
            PostcoderRepository repository = new PostcoderRepository()
        when:
            Object result = repository.get("$url/$latitude/$longitude",[distance:distance])
        then:
            result != null
            result.first().organisation != null
        where:
            url       | latitude  | longitude | distance
            "rgeo/ie" | 53.332067 | -6.255492 | 50
    }
}
