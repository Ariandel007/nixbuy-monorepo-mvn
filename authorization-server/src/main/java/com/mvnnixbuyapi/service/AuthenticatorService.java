package com.mvnnixbuyapi.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

@Service
public class AuthenticatorService {

    private final BytesEncryptor bytesEncryptor;

    public AuthenticatorService(BytesEncryptor bytesEncryptor) {
        this.bytesEncryptor = bytesEncryptor;
    }

    public boolean check(String key, String code) {
        try {
            String secret = new String(this.bytesEncryptor.decrypt(Hex.decode(key)), StandardCharsets.UTF_8);
            return TimeBasedOneTimePasswordUtil.validateCurrentNumber(secret, Integer.parseInt(code), 10000);
        }
        catch (IllegalArgumentException ex) {
            return false;
        }
        catch (GeneralSecurityException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public String generateSecret() {
        return TimeBasedOneTimePasswordUtil.generateBase32Secret();
    }

    public String generateQrImageUrl(String keyId, String base32Secret) {
        if (keyId == null || keyId.isEmpty()) {
            throw new IllegalArgumentException("El keyId no puede ser nulo o vacío.");
        }
        if (base32Secret == null || base32Secret.isEmpty()) {
            throw new IllegalArgumentException("El base32Secret no puede ser nulo o vacío.");
        }

        String otpAuthUrl = "otpauth://totp/" + keyId + "?secret=" + base32Secret + "&digits=6";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthUrl, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error al generar el código QR", e);
        }
    }

    public String getCode(String base32Secret) throws GeneralSecurityException {
        return TimeBasedOneTimePasswordUtil.generateCurrentNumberString(base32Secret);
    }
}
