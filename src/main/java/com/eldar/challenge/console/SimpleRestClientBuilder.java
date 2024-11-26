package com.eldar.challenge.console;

import org.springframework.web.client.RestClient;

public class SimpleRestClientBuilder implements RestClientBuilder {
    public SimpleRestClientBuilder() {
    }

    @Override
    public RestClient build(String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
