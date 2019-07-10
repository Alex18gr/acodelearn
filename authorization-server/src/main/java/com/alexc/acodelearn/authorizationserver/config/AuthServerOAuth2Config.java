package com.alexc.acodelearn.authorizationserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@PropertySource({ "classpath:persistence.properties" })
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(AuthServerOAuth2Config.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*
    The 3 configurations
     */
    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                // .checkTokenAccess("isAuthenticated()");
                .checkTokenAccess("permitAll()");
    }

    /**
     * Configuration of the clients in memory
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception { // @formatter:off
        clients.inMemory()
                .withClient("sampleClientId")
                .authorizedGrantTypes("implicit")
                .scopes("read", "write", "foo", "bar")
                .autoApprove(false)
                .accessTokenValiditySeconds(3600)
                .redirectUris("http://localhost:8083/","http://localhost:8086/")
                .and()
                .withClient("fooClientIdPassword")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
                .scopes("foo", "read", "write")
                .accessTokenValiditySeconds(3600)       // 1 hour
                .refreshTokenValiditySeconds(2592000)  // 30 days
                .redirectUris("http://www.example.com","http://localhost:8089/","http://localhost:8080/login/oauth2/code/custom","http://localhost:8080/ui-thymeleaf/login/oauth2/code/custom", "http://localhost:8080/authorize/oauth2/code/bael", "http://localhost:8080/login/oauth2/code/bael")
                .and()
                .withClient("barClientIdPassword")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("bar", "read", "write")
                .accessTokenValiditySeconds(3600)       // 1 hour
                .refreshTokenValiditySeconds(2592000)  // 30 days
                .and()
                .withClient("testImplicitClientId")
                .authorizedGrantTypes("implicit")
                .scopes("read", "write", "foo", "bar")
                .autoApprove(true)
                .redirectUris("http://www.example.com");
    } // @formatter:on

/**
 * This method is useful for configuration of clients via a database datasource
 * */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients)
//            throws Exception {
//        clients.jdbc(dataSource()).passwordEncoder(passwordEncoder);
//    }

    @Override
    public void configure(
            AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {

        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter()) // This was the mistake the last time!!!
                .authenticationManager(authenticationManager);
    }


    // The token store bean, jdbc token store based on the given data source
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource());
//    }

    // for jwt token implementation
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        // final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        // converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;
    }


    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    // ----------------     Data Source Configuration
    // -----------------------------------------------------------------------------------


}
