package com.dev.EcommerceUserService.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.PasswordLookup;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
public class JksConfig {
    @Value("${jks.key.file}")
    private String keyFile;
    @Value("${jks.alias}")
    private String alias;
    @Value("${jks.password}")
    private String password;

    private JWKSet buildJWKSet() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(keyFile)) {
            keyStore.load(inputStream, alias.toCharArray());
            return JWKSet.load(keyStore, new PasswordLookup() {
                @Override
                public char[] lookupPassword(String s) {
                    return password.toCharArray();
                }
            });
        }
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        JWKSet jwkSet = buildJWKSet();
        return (jwkSelector, securityContext) -> {
            return jwkSelector.select(jwkSet);
        };
    }
}
