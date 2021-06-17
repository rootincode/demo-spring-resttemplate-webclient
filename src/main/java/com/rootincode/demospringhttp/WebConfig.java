package com.rootincode.demospringhttp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/todos";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .basicAuthentication("username", "password")
                .rootUri(BASE_URL)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .interceptors((request, body, execution) -> {
                    LOGGER.warn("Request URI : {}", request.getURI());
                    ClientHttpResponse response = execution.execute(request, body);
                    LOGGER.warn("Response status code : {}",response.getStatusCode());
                    return response;
                })
                .build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter((request, next) -> {
                    LOGGER.warn("Request URI : {}", request.url());
                    return next.exchange(request);
                })
                .baseUrl(BASE_URL)
                .build();
    }
}
