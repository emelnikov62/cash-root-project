package ru.cash.web.config

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import ru.cash.web.config.SecurityWebConstants.REMOVE_DESKTOP_REGEX
import ru.cash.web.config.SecurityWebConstants.ROLE
import ru.cash.web.config.SecurityWebConstants.SECURED_AREA
import ru.cash.web.config.SecurityWebConstants.ZK_RESOURCES
import ru.cash.web.config.SecurityWebConstants.ZUL_FILES
import java.io.FileInputStream
import java.util.*

/**
 * Web security configuration.
 * @author Sergey Valiev
 */
@Configuration
@EnableWebSecurity
@Profile("basic-auth")
@Suppress("LateinitUsage")
class CashBasicSecurityWebConfig : WebSecurityConfigurerAdapter() {

    companion object {
        val LOG = LoggerFactory.getLogger(CashBasicSecurityWebConfig::class.java)
    }

    @Value("\${spring.security.user.file-based-repository-location}")
    private lateinit var usersRepositoryLocation: String

    @Suppress("SpreadOperator")
    override fun configure(http: HttpSecurity) {
        with(http) {
            csrf().disable()

            with(headers()) {
                frameOptions().sameOrigin()
                cacheControl().disable()
            }

            with(authorizeRequests()) {
                antMatchers(ZUL_FILES).permitAll() // block direct access to zul files
                        .antMatchers(HttpMethod.GET, *ZK_RESOURCES).permitAll() // allow zk resources
                        .regexMatchers(HttpMethod.GET, REMOVE_DESKTOP_REGEX).permitAll()

                        .regexMatchers(SECURED_AREA).hasRole(ROLE).anyRequest().authenticated().and()
                        .httpBasic()
            }
        }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(DaoAuthenticationProvider().apply {
            setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder())
            setUserDetailsService(userDetailsService())
        })
        auth.authenticationProvider(RunAsImplAuthenticationProvider().apply { key = "cash_run_as_key" })
    }

    @SuppressFBWarnings("OBL")
    override fun userDetailsService(): UserDetailsService {
        val usersProperties = Properties()

        try {
            FileInputStream(usersRepositoryLocation).use { usersProperties.load(it) }
        } catch (e: Exception) {
            this::class.java.getResourceAsStream("users.properties").use { usersProperties.load(it) }
        }
        return InMemoryUserDetailsManager(usersProperties)
    }
}
