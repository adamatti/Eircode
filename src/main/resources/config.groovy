port = (System.env.PORT ?: "8080").toInteger()
fakeEnabled = "true".equalsIgnoreCase(System.env.FAKE_ENABLED ?: "true")

postcoder {
    searchKey = System.env.SEACH_KEY ?: "PCW45-12345-12345-1234X"

    baseUrl = fakeEnabled
        ? "http://localhost:${port}/fake/"
        : "http://ws.postcoder.com/pcw/${searchKey}/"
}

test {
    baseUrl = "http://${System.env.SERVER_TO_TEST ?: 'localhost'}:$port"
}

redis  {
    URI redisUri = System.env.REDISCLOUD_URL ? new URI(System.env.REDISCLOUD_URL) : null
    host = redisUri?.host ?: "localhost"
    port = redisUri?.port?: 6379
    pass = redisUri?.userInfo? redisUri.userInfo.split(":", 2)[1] : ''
}
