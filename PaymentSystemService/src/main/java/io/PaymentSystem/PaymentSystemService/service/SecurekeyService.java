package io.PaymentSystem.PaymentSystemService.service;

import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

@Service
public class SecurekeyService
{
    public static SecretKey getSecretEncryptionKey() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256, new SecureRandom()); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();
        return secKey;
    }
}
