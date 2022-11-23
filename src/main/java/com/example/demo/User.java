package com.example.demo;

import java.util.UUID;

public class User {

    private UUID id;
    private String associatedData;
    private String name;
    private String email;
    private SensitiveField sensitiveInformation = new SensitiveField();

    public String getSensitiveInformation(CryptoService cryptoService) {
        return this.sensitiveInformation.getPlainText(cryptoService, this.associatedData);
    }

    public void setSensitiveInformation(CryptoService cryptoService, String sensitiveInformation) {
        this.sensitiveInformation.setPlainText(cryptoService, sensitiveInformation, this.associatedData);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssociatedData() {
        return associatedData;
    }

    public void setAssociatedData(String associatedData) {
        this.associatedData = associatedData;
    }
}
