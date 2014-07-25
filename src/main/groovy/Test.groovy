import wslite.rest.ContentType
import wslite.rest.RESTClient

class Test {
    static RESTClient proxy = new RESTClient("http://localhost:9090")
    static RESTClient apple = new RESTClient("http://www.apple.com")
    static RESTClient nfl = new RESTClient("http://www.nfl.com")
    static RESTClient tech = new RESTClient("http://www.techmeme.com")
    static RESTClient espn = new RESTClient("http://espn.go.com")

    static void main(String... args) {
        int iterations = 100
        int base = iterations * 4
        long proxyTime = 0, normalTime = 0
        (0..iterations).each {
            def proxy1Time = testProxy()
            def normal1Time = testNormal()
            println proxy1Time/4 + " -> " + normal1Time/4
            proxyTime += proxy1Time
            normalTime += normal1Time
        }
        println "Proxy: " + proxyTime / base
        println "Normal: " + normalTime / base
    }

    static long testNormal() {
        long time = System.currentTimeMillis()
        def c = apple.get(path: '/').contentAsString
        assert c.contains('html')
        c = nfl.get(path: '/').contentAsString
        assert c.contains('html')
        c = tech.get(path: '/').contentAsString
        assert c.contains('html')
        c = espn.get(path: '/').contentAsString
        assert c.contains('html')
        System.currentTimeMillis() - time
    }

    static long testProxy() {
        long time = System.currentTimeMillis()
        def c = proxy.get(path: '/', headers: [caller:'me', service:'apple']).contentAsString
        assert c.contains('html')
        c = proxy.get(path: '/', headers: [caller:'me', service:'nfl']).contentAsString
        assert c.contains('html')
        c = proxy.get(path: '/', headers: [caller:'me', service:'tech']).contentAsString
        assert c.contains('html')
        c = proxy.get(path: '/', headers: [caller:'me', service:'espn']).contentAsString
        assert c.contains('html')
        System.currentTimeMillis() - time
    }
}
