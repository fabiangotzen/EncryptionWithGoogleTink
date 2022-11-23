package com.example.demo;

import org.springframework.util.StringUtils;

public class SensitiveField {

    private transient String plainText;

    private String cipherText;

    public String getPlainText(CryptoService cryptoService, String associatedData) {
        if (!StringUtils.hasText(this.plainText) && StringUtils.hasText(this.cipherText)) {
            this.plainText = cryptoService.decrypt(this.cipherText, associatedData);
        }
        return this.plainText;
    }

    public void setPlainText(CryptoService cryptoService, String plainText, String associatedData) {
        this.cipherText = cryptoService.encrypt(plainText, associatedData);
        this.plainText = plainText;
    }

}
