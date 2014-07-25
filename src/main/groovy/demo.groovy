import org.apache.camel.*
import org.apache.camel.impl.*
import org.apache.camel.builder.*

import java.util.concurrent.Executors

public class Demo{
    public static void main(String... args) {
        def camelContext = new DefaultCamelContext()

        camelContext.addRoutes(new RouteBuilder() {
            def void configure() {
                from("file:target/inventory?noop=true")
                        .log("Starting to process big file")
                        .split(body().tokenize("\n")).streaming().executorService(Executors.newCachedThreadPool())
//                      .split(body().tokenize("\n")).streaming().executorService(Executors.newFixedThreadPool(20))
                        .bean(InventoryService.class, "csvToObject")
                        .to("direct:update")
                        .end()
                        .log("Done processing big file:")

                from("direct:update")
                        .bean(InventoryService.class, "updateInventory")

                def router = from("netty-http:http://0.0.0.0:9090")
                        .log('Service Call ${header.caller} -> ${header.service}')
                        .choice()
                            .when(header("caller").isNull()).transform().constant('{"error": true, "message": "missing caller header"}')
                            .when(header("service").isNull()).transform().constant('{"error": true, "message": "missing service header"}');
                new RouteReader().read(router, routes.newInstance(), 'test')
                router.endChoice().end()
            }
        })

        camelContext.start()
        addShutdownHook{ camelContext.stop() }
        synchronized(this){ this.wait() }
    }
}

