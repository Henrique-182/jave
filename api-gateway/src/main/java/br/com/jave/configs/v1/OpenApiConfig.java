package br.com.jave.configs.v1;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class OpenApiConfig {

	@Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(
            SwaggerUiConfigParameters config,
            RouteDefinitionLocator locator) {

        var definitions = locator.getRouteDefinitions().collectList().block();
        List<GroupedOpenApi> groupedOpenApis = new ArrayList<>();

        definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId() != null && !routeDefinition.getId().isEmpty())
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId();
                    config.addGroup(name);
                    GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**")
                            .group(name)
                            .displayName(name)  // Use displayName to set a friendly name in the UI
                            .build();
                    groupedOpenApis.add(groupedOpenApi);
                });

        return groupedOpenApis;
    }
}
