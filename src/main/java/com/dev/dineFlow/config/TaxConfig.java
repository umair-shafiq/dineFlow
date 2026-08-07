package com.dev.dineFlow.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.tax")
@Getter
@Setter
public class TaxConfig
{
    private double percentage;
}
