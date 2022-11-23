package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AESCryptoServiceTest {

    CryptoService cryptoService;

    @BeforeEach
    void setUp() throws GeneralSecurityException {
        this.cryptoService = new AESCryptoService();
    }

    @Test
    void testEncryptingAndDecryptingResultsInSamePlainText() {
        // Arrange
        String associatedData = UUID.randomUUID().toString();
        String plainText = "plainText";

        // Act
        String encryptedString = cryptoService.encrypt(plainText, associatedData);
        String decryptedString = cryptoService.decrypt(encryptedString, associatedData);

        // Assert
        Assertions.assertAll("Assert encryption and decryption methods",
                () -> Assertions.assertNotEquals(plainText, encryptedString),
                () -> Assertions.assertEquals(plainText, decryptedString)
        );
    }

    @Test
    void testEncryptingAndDecryptingWithDifferentAssociatedDataFails() {
        // Arrange
        String associatedData = UUID.randomUUID().toString();
        String fakeAssociatedData = UUID.randomUUID().toString();
        String plainText = "plainText";

        // Act
        String encryptedString = cryptoService.encrypt(plainText, associatedData);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            cryptoService.decrypt(encryptedString, fakeAssociatedData);
        });

        // Assert
        Assertions.assertAll("Assert decrypting with different associated Data fails",
                () -> assertEquals("java.security.GeneralSecurityException: decryption failed", exception.getMessage())
        );
    }

    @Test
    void testEncryptionDecryptionWithGetterSetterApproach() {
        // Arrange
        var user = new User();
        user.setAssociatedData("associatedData");
        user.setSensitiveInformation(cryptoService, "sensitive");

        // Act
        String sensitiveInformation = user.getSensitiveInformation(cryptoService);

        // Assert
        Assertions.assertAll("Assert Encryption & Decryption with Getter/Setter Approach",
                () -> assertEquals(sensitiveInformation, "sensitive")
        );
    }
}
