package com.example.demo;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeyTemplates;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class AESCryptoService implements CryptoService {

    Aead aead;
    Base64.Encoder encoder = Base64.getEncoder();
    Base64.Decoder decoder = Base64.getDecoder();

    /**
     * Note: In a real project, an external Key Management System with key rotation should be used.
     */
    public AESCryptoService() throws GeneralSecurityException {
        AeadConfig.register();
        KeysetHandle keysetHandle = KeysetHandle.generateNew(
                KeyTemplates.get("AES256_GCM"));
        this.aead = keysetHandle.getPrimitive(Aead.class);
    }

    public String encrypt(String plainText, String associatedData) {
        byte[] ciphertext;
        try {
            ciphertext = aead.encrypt(plainText.getBytes(), associatedData.getBytes());
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
        return this.encoder.encodeToString(ciphertext);
    }

    public String decrypt(String cipherText, String associatedData) {
        byte[] decodedCipherText = this.decoder.decode(cipherText);
        byte[] plainText;
        try {
            plainText = aead.decrypt(decodedCipherText, associatedData.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
        return new String(plainText);
    }
}
