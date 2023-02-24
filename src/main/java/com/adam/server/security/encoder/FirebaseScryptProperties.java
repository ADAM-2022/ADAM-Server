package com.adam.server.security.encoder;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "firebase.scrypt")
public class FirebaseScryptProperties {
  private final String signerKey;
  private final String saltSeparator;
  private final int rounds;
  private final int memCost;
  private final int saltLength;
  private final int dkLen;

  @ConstructorBinding
  public FirebaseScryptProperties(
      String signerKey, String saltSeparator, int rounds, int memCost, int saltLength, int dkLen) {
    this.signerKey = signerKey;
    this.saltSeparator = saltSeparator;
    this.rounds = rounds;
    this.memCost = memCost;
    this.saltLength = saltLength;
    this.dkLen = dkLen;
  }

  public String getSignerKey() {
    return signerKey;
  }

  public String getSaltSeparator() {
    return saltSeparator;
  }

  public int getRounds() {
    return rounds;
  }

  public int getMemCost() {
    return memCost;
  }

  public int getSaltLength() {
    return saltLength;
  }

  public int getDkLen() {
    return dkLen;
  }
}
