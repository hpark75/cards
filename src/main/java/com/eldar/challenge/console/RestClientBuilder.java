package com.eldar.challenge.console;

import org.springframework.web.client.RestClient;

public interface RestClientBuilder {
    RestClient build(String baseUrl);
}
