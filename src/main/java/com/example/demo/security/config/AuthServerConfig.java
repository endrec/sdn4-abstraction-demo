package com.example.demo.security.config;

import com.example.demo.security.service.AuthUserDetailsService;
import com.example.demo.security.service.KeyService;
import lombok.extern.slf4j.Slf4j;
import org.parboiled.common.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final int MINUTE_IN_SECONDS = 60;
    private static final int HOUR_IN_SECONDS = MINUTE_IN_SECONDS * 60;
    private static final int DAY_IN_SECONDS = HOUR_IN_SECONDS * 24;
    private static final int MONTH_IN_SECONDS = DAY_IN_SECONDS * 30;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Autowired
    private KeyService keyService;

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(getTokenEnhancer())
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients
            .inMemory()
                .withClient("client")
                .secret("password")
                .scopes("read", "write")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials", "implicit")
                .refreshTokenValiditySeconds(MONTH_IN_SECONDS);
        // @formatter:on
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        log.debug("Configuring token services.");
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(this.tokenStore());
        tokenServices.setTokenEnhancer(this.getTokenEnhancer());
        return tokenServices;
    }

    private TokenEnhancer getTokenEnhancer() {
        final TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(ImmutableList.of(
                accessTokenConverter()
        ));
        return chain;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        try {
            log.debug("Configuring JWT Token Converter.");
            final JwtAccessTokenConverter enhancer = new JwtAccessTokenConverter();
            enhancer.setKeyPair(keyService.getOrGenerateKeyPair());

            enhancer.afterPropertiesSet();
            return enhancer;
        } catch (final Exception e) {
            log.error("Unable to initialize JwtAccessTokenConverter, terminating.");
            throw new RuntimeException(e);
        }
    }

    @Bean
    public JwtTokenStore tokenStore() {
        log.debug("Configuring JWT Token Store.");
        return new JwtTokenStore(accessTokenConverter());
    }
}
