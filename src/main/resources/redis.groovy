beans {
    xmlns([context: "http://www.springframework.org/schema/context"])

    jedisConnFactory(org.springframework.data.redis.connection.jedis.JedisConnectionFactory){
        usePool = true
        hostName = '${redis.host}'
        port = '${redis.port}'
        password = '${redis.pass}'
    }

    redisTemplate(org.springframework.data.redis.core.RedisTemplate){
        connectionFactory = ref('jedisConnFactory')
    }
}
