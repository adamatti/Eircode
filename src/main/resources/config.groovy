port = (System.env.PORT ?: "8080").toInteger()
fakeEnabled = "true".equalsIgnoreCase(System.env.FAKE_ENABLED ?: "true")

String searchKey = System.env.SEACH_KEY ?: "PCW45-12345-12345-1234X"
postcoder = [
    baseUrl : fakeEnabled
        ? "http://localhost:${port}/fake/"
        : "http://ws.postcoder.com/pcw/${searchKey}/"
    ,
    searchKey : searchKey
]

test = [
    baseUrl : "http://${System.env.SERVER_TO_TEST ?:'localhost'}:$port"
]


