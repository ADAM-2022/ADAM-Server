package com.adam.server.security.encoder;

import com.lambdaworks.crypto.SCrypt;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FirebaseScryptEncoder implements PasswordEncoder {
  private final FirebaseScryptProperties properties;

  private final Charset CHARSET = StandardCharsets.US_ASCII;

  private final String CIPHER = "AES/CTR/NoPadding";

  @Override
  public String encode(CharSequence rawPassword) {
    try {
      String salt = RandomStringUtils.randomAlphanumeric(properties.getSaltLength());
      byte[] cipherTextBytes = getCipherTextBytes(rawPassword, salt);
      return salt + Base64.encodeBase64String(cipherTextBytes);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    try {
      String salt = getSaltByEncodedPassword(encodedPassword);
      byte[] cipherTextBytes = getCipherTextBytes(rawPassword, salt);

      String password = getPasswordByEncodedPassword(encodedPassword);
      byte[] decodedEncodedPasswordBytes = getDecodedBytes(password);

      return MessageDigest.isEqual(cipherTextBytes, decodedEncodedPasswordBytes);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String getPasswordByEncodedPassword(String encodedPassword) {
    return encodedPassword.substring(properties.getSaltLength());
  }

  private byte[] getCipherTextBytes(CharSequence rawPassword, String salt)
      throws GeneralSecurityException {
    byte[] hashedRawPasswordBytes = hash(rawPassword.toString(), salt);
    byte[] decodedSignerKeyBytes = getDecodedBytes(properties.getSignerKey());
    return encrypt(decodedSignerKeyBytes, hashedRawPasswordBytes);
  }

  private byte[] encrypt(byte[] signerKeyBytes, byte[] hashedRawPasswordBytes)
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidAlgorithmParameterException,
          InvalidKeyException,
          IllegalBlockSizeException,
          BadPaddingException {
    Cipher c = Cipher.getInstance(CIPHER);
    Key key = generateKeyFromString(hashedRawPasswordBytes);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);
    c.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
    return c.doFinal(signerKeyBytes);
  }

  private byte[] hash(String rawPassword, String salt) throws GeneralSecurityException {
    String saltSeparator = properties.getSaltSeparator();

    byte[] rawPasswordBytes = rawPassword.getBytes(CHARSET);
    byte[] saltConcatenation = getDecodedSaltConcatenation(salt, saltSeparator);
    int N = 1 << properties.getMemCost();
    int rounds = properties.getRounds();
    int p = 1;
    int dkLen = properties.getDkLen();
    return SCrypt.scrypt(rawPasswordBytes, saltConcatenation, N, rounds, p, dkLen);
  }

  private String getSaltByEncodedPassword(String password) {
    return password.substring(0, properties.getSaltLength());
  }

  private byte[] getDecodedSaltConcatenation(String salt, String saltSeparator) {
    byte[] decodedSaltBytes = getDecodedBytes(salt);
    byte[] decodedSaltSeparatorBytes = getDecodedBytes(saltSeparator);

    int decodedSaltBytesLength = decodedSaltBytes.length;
    int decodedSaltSeparatorBytesLength = decodedSaltSeparatorBytes.length;
    byte[] saltConcat = new byte[decodedSaltBytesLength + decodedSaltSeparatorBytesLength];
    System.arraycopy(decodedSaltBytes, 0, saltConcat, 0, decodedSaltBytesLength);
    System.arraycopy(
        decodedSaltSeparatorBytes,
        0,
        saltConcat,
        decodedSaltBytesLength,
        decodedSaltSeparatorBytesLength);
    return saltConcat;
  }

  private byte[] getDecodedBytes(String str) {
    byte[] strBytes = str.getBytes(CHARSET);
    return Base64.decodeBase64(strBytes);
  }

  private Key generateKeyFromString(byte[] keyVal) {
    return new SecretKeySpec(keyVal, 0, 32, "AES");
  }
}
