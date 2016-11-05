package adamatti.app.service

import adamatti.app.repositories.PostcoderRepository
import org.springframework.data.redis.core.ValueOperations
import spock.lang.Specification
import spock.lang.Unroll

class PostcoderServiceTest extends Specification {
    def "test cache"(){
        given:
            PostcoderRepository repository = Mock(PostcoderRepository)
            ValueOperations cache = Mock(ValueOperations)
            PostcoderService service = new PostcoderService(repository:repository, cache: cache)
            String fakeResult = '{"result":"fake"}'
        when:
            String result = service.get("fakePath")
        then:
            result == fakeResult
            1 * cache.get("fakePath,") >> fakeResult
            0 * repository.get(_ as String, _ as Map)
    }

    def "test without cache"(){
        given:
            PostcoderRepository repository = Mock(PostcoderRepository)
            ValueOperations cache = Mock(ValueOperations)
            PostcoderService service = new PostcoderService(repository:repository, cache: cache)
        when:
            String result = service.get("fakePath")
        then:
            result != null
            0 * cache.get(_ as String, _ as Map) >> null
            1 * repository.get(_ as String, _ as Map) >> [[address: "fakeAddress"]]
    }

    @Unroll
    def "test remove invalid keys (#query)"(){
        given:
            PostcoderService service = new PostcoderService()
        when:
            String key = service.buildKey("fakePath",query)
        then:
            key == "fakePath,${result}"
        where:
            query                      | result
            [invalid : "error"]        | ""
            [include: "a"]             | "include=a"
            [invalid:"e", include:"a"] | "include=a"
            [exclude:"e", include:"a"] | "exclude=e,include=a"
            [include:"a", exclude:"e"] | "exclude=e,include=a"
    }
}
