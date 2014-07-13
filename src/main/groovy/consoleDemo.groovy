import org.apache.camel.*
import org.apache.camel.impl.*
import org.apache.camel.builder.*

public class consoleDemo{
    public static void main(String... args) {
        def camelContext = new DefaultCamelContext()

        camelContext.addRoutes(new RouteBuilder() {
            def void configure() {
                from("stream:in?promptMessage=Enter something: ")
                    .transform { it.in.body.reverse() + "  "}
                    .to("stream:out")
            }
        })

        camelContext.start()
        addShutdownHook{ camelContext.stop() }
        synchronized(this){ this.wait() }
    }
}

