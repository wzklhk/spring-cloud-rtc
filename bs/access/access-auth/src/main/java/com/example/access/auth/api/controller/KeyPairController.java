package com.example.access.auth.api.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author wzklhk
 */
@Slf4j
@RestController
public class KeyPairController {
    private final KeyPair keyPair;

    public KeyPairController(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @GetMapping("/rsa/publicKey")
    public Map<String, Object> getKey() {
        log.debug("get publicKey");
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
