package com.wolroys.springstockapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@Configuration
public class ApplicationConfig {

    @Bean
    public InvestApi api(){
        String ssoToken = System.getenv("ssoToken");
        return InvestApi.createSandbox(ssoToken);
    }
}
