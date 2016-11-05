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
        String key = this.buildKey(path, query)
        String cachedValue = cache.get(key)

        if (cachedValue){
            log.trace("Returning cached value")
            return cachedValue
        }

        Object result = repository.get(path,query)
        String json = toJson(result)
        cache.set(key, json)

        json
    }

    private String toJson(Object obj){
        new JsonBuilder(obj).toPrettyString()
    }

    private String buildKey(String path, Map query) {
        String key = path + "," + query.collect {k,v -> "${k}=${v}"}.join(",")

        log.info("Key: ${key}")

        key
    }
}
