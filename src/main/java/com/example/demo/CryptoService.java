package com.example.demo;

public interface CryptoService {

    String encrypt(String plainText, String associatedData);

    String decrypt(String cipherText, String associatedData);
}
