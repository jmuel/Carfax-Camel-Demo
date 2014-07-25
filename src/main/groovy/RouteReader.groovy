import org.apache.camel.builder.PredicateBuilder
import org.apache.camel.model.ChoiceDefinition

class RouteReader extends org.apache.camel.builder.BuilderSupport {
    String service, environment
    ChoiceDefinition router
    List routes = []

    def read(ChoiceDefinition router, routFile, String environment) {
        this.router = router
        this.environment = environment
        routFile.metaClass.methodMissing = { String name, args ->
            service = name
            args[0].delegate = this
            args[0].resolveStrategy = Closure.DELEGATE_ONLY
            args[0]()
        }
        routFile.run()
        createRoutes()
    }

    def route(String env, String to, String from = null) {
        addRoute(env + ':' + service, from, to)
        if(env == environment)
            addRoute(service, from, to)
    }

    private addRoute(String service, String from, String to) {
        def route = [service: service, from: from, to: to]
        if(from)
            routes.add(0, route)
        else
            routes << route
    }

    private createRoutes() {
        routes.each {
            createRoute(it.service, it.from, it.to)
        }
    }

    private createRoute(String service, String from, String to) {
        if(from == null) {
            router.when(header('service').isEqualTo(service)).to("http4://" + to + '?bridgeEndpoint=true')
        } else {
            router.when(PredicateBuilder.and(
                    header('service').isEqualTo(service),
                    header('caller').isEqualTo(from)
            )).to("http4://" + to + '?bridgeEndpoint=true')
        }
    }
}