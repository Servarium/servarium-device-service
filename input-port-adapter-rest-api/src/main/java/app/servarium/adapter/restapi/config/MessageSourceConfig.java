package app.servarium.adapter.restapi.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {
    @Bean
    public MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("errors");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.getDefault());

        return messageSource;
    }
}
