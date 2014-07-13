import org.apache.camel.*
import org.apache.camel.impl.*
import org.apache.camel.builder.*

public class Demo{
    public static void main(String... args) {
        def camelContext = new DefaultCamelContext()

        camelContext.addRoutes(new RouteBuilder() {
            def void configure() {
                from("jetty:http://localhost:11337/test")
                    .to("stream:out")
            }
        })

        camelContext.start()
        addShutdownHook{ camelContext.stop() }
        synchronized(this){ this.wait() }
    }
}

