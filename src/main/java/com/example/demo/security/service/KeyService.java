package com.example.demo.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
@Slf4j
@Service
public class KeyService {
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();

            return kp;
        } catch (NoSuchAlgorithmException e) {
            log.error("This should not happen: ", e);
            throw new RuntimeException(e);
        }
    }

    public KeyPair getOrGenerateKeyPair() {
        return generateKeyPair();
    }

}
