package ru.cash.core.config

import oracle.jdbc.driver.OracleDriver
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

/**
 * Datasource configuration.
 * @author Sergey Valiev
 */
@Configuration
class CashDataSourceConfig {

    /**
     * Data source config.
     */
    @Bean
    @ConfigurationProperties(prefix = "datasource.cash")
    @Primary
    fun cashDataSource(): DataSource = DataSourceBuilder
            .create()
            .driverClassName(org.postgresql.Driver::class.qualifiedName)
            .build()
}
