package com.mbsystems.reactivebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
public class WebConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials( true );
        corsConfiguration.addAllowedOrigin( "http://localhost:4200" );
        corsConfiguration.addAllowedHeader( "*" );
        corsConfiguration.addAllowedMethod( "*" );

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration( "/**", corsConfiguration );

        return new CorsWebFilter( source );
    }
}
