package adamatti.app.service

import adamatti.app.repositories.PostcoderRepository
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component

import javax.annotation.Resource

@Slf4j
@Component
class PostcoderService {
    @Autowired
    private PostcoderRepository repository

    @Resource(name="redisTemplate")
    private ValueOperations cache

    String get(String path, Map query = [:]){
        assert !path.startsWith("/")

        String key = this.buildKey(path, query)
        String cachedValue = cache.get(key)

        if (cachedValue){
            log.trace("Returning cached value")
            return cachedValue
        }

        Object result = repository.get(path,query)
        String json = query.callback ? result : toJson(result)
        cache.set(key, json)

        json
    }

    private String toJson(Object obj){
        new JsonBuilder(obj).toPrettyString()
    }

    protected String buildKey(String path, Map query) {
        Map validQuery = this.removeInvalidKeys(query)

        String key = path + "," + validQuery
            .sort{it.key} //sort by key to reuse cache
            .collect {k,v -> "${k}=${v}"}
            .join(",")

        log.info("Key: ${key}")

        key
    }

    protected Map removeInvalidKeys(Map query){
        List validKeys = [
            "lines",
            "include",
            "exclude",
            //"format" - it is invalid, we only accept json
            "identifier", //not sure I got what it does
            "callback",
            "page"
        ]

        query.findAll{k, v -> validKeys.contains(k)}
    }
}
