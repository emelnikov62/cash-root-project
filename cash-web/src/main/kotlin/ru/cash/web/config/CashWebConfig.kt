package ru.cash.web.config

import org.springframework.beans.factory.config.PropertiesFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource
import org.springframework.core.io.ClassPathResource
import java.nio.charset.StandardCharsets

/**
 * Web configuration.
 * @author emelnikov
 */
@Configuration
@ImportResource(locations = ["classpath:META-INF/spring/cash-overall-config.xml"])
@ComponentScan(basePackages = ["ru.cash.web"])
class CashWebConfig {

    /**
     * Configures pages bean.
     */
    @Bean
    fun pages() = PropertiesFactoryBean().apply { setLocation(ClassPathResource("pages.properties"))
                                                  setFileEncoding(StandardCharsets.UTF_8.name()) }

    /**
     * Configures web config bean.
     */
    @Bean
    fun webConfig() = PropertiesFactoryBean().apply { setLocation(ClassPathResource("web-config.properties"))
                                                      setFileEncoding(StandardCharsets.UTF_8.name()) }
}
