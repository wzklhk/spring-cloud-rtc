package com.example.access.auth.config;

import com.example.access.auth.properties.JWTProperties;
import com.example.access.auth.properties.OAuth2Properties;
import com.example.access.auth.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * 注解@EnableAuthorizationServer   //开启验证服务器
 *
 * @author wzklhk
 */
@EnableAuthorizationServer
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl userService;

    private final OAuth2Properties oAuth2Properties;

    private final JWTProperties jwtProperties;

    public OAuth2Config(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserServiceImpl userService, OAuth2Properties oAuth2Properties, JWTProperties jwtProperties) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.oAuth2Properties = oAuth2Properties;
        this.jwtProperties = jwtProperties;
    }


    /**
     * 这个方法是对客户端进行配置，一个验证服务器可以预设多个客户端，
     * 之后这些指定的客户端就可以按照下面指定的方式进行验证
     *
     * @param clients 客户端配置工具
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(oAuth2Properties.getClientId())
                .secret(passwordEncoder.encode(oAuth2Properties.getClientSecret()))
                .scopes(oAuth2Properties.getScopes())
                .authorizedGrantTypes(oAuth2Properties.getAuthorizedGrantTypes())
                .accessTokenValiditySeconds(oAuth2Properties.getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(oAuth2Properties.getRefreshTokenValiditySeconds())
        // 关闭自动审批
//                .autoApprove(false)
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // 编码器设定为BCryptPasswordEncoder
                .passwordEncoder(passwordEncoder)
                // 允许客户端使用表单验证
                .allowFormAuthenticationForClients()
                // 允许所有的Token查询请求
                .checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenEnhancer(jwtAccessTokenConverter())
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                // 配置加载用户信息的服务
                .userDetailsService(userService)
        ;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    @Bean
    public KeyPair keyPair() {
        // 从证书中获取秘钥对
        // keytool -genkey -alias key -keyalg RSA -keystore key.jks
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new PathResource(jwtProperties.getKeyPath()),
                jwtProperties.getKeyPassword().toCharArray()
        );

        return keyStoreKeyFactory.getKeyPair(
                jwtProperties.getKeyAlias(),
                jwtProperties.getKeyPassword().toCharArray()
        );
    }
}
