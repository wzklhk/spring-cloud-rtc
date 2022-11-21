package com.example.access.auth.config;

import com.example.access.auth.constant.CommonConstant;
import com.example.access.auth.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;

@EnableAuthorizationServer   //开启验证服务器
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 这个方法是对客户端进行配置，一个验证服务器可以预设多个客户端，
     * 之后这些指定的客户端就可以按照下面指定的方式进行验证
     *
     * @param clients 客户端配置工具
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(CommonConstant.CLIENT_ID)
                .secret(encoder.encode(CommonConstant.CLIENT_SECRET))
                .scopes(CommonConstant.SCOPE)
                .authorizedGrantTypes(CommonConstant.AUTHORIZED_GRANT_TYPES)
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(86400)
                .autoApprove(false)  // 关闭自动审批
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .passwordEncoder(encoder)  // 编码器设定为BCryptPasswordEncoder
                .allowFormAuthenticationForClients()  // 允许客户端使用表单验证
                .checkTokenAccess("permitAll()")  // 允许所有的Token查询请求
                .tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
//                .tokenServices(serverTokenServices())  //设定为刚刚配置好的AuthorizationServerTokenServices
                .tokenEnhancer(jwtAccessTokenConverter())
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(userService)  // 配置加载用户信息的服务
        ;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /*@Bean
    public TokenStore tokenStore(JwtAccessTokenConverter converter) {  //Token存储方式现在改为JWT存储
        return new JwtTokenStore(converter);  //传入刚刚定义好的转换器
    }

    private AuthorizationServerTokenServices serverTokenServices() {  //这里对AuthorizationServerTokenServices进行一下配置
        DefaultTokenServices services = new DefaultTokenServices();
        services.setSupportRefreshToken(true);   //允许Token刷新
        services.setTokenStore(tokenStore(jwtAccessTokenConverter()));   //添加刚刚的TokenStore
        services.setTokenEnhancer(jwtAccessTokenConverter());   //添加Token增强，其实就是JwtAccessTokenConverter，增强是添加一些自定义的数据到JWT中
        return services;
    }*/

    @Bean
    public KeyPair keyPair() {
        // 从证书中获取秘钥对
        // keytool -genkey -alias key -keyalg RSA -keystore key.jks
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new PathResource("key.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("key", "123456".toCharArray());
    }
}
