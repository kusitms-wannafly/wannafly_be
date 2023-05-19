package com.kusitms.wannafly.command.grammar.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class GrammarClientConfig {

    @Value("${grammar.origin}")
    private String grammarOrigin;

    @Bean
    public GrammarClient grammarClient() {
        WebClient client = WebClient.builder()
                .baseUrl(grammarOrigin)
                .build();
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .build()
                .createClient(GrammarClient.class);
    }
}
