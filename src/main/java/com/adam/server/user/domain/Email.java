package com.adam.server.user.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static io.micrometer.common.util.StringUtils.isNotEmpty;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Email {

  private final String address;

  private static final Pattern addressPattern =
      Pattern.compile("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+");

  public Email(String address) {
    checkArgument(isNotEmpty(address), "address must be provided");
    checkArgument(
        address.length() >= 4 && address.length() <= 50,
        "address length must be between 4 and 50 characters.");
    checkArgument(checkAddress(address), "Invalid email address: " + address);

    this.address = address;
  }

  private static boolean checkAddress(String address) {
    Matcher addressMatcher = addressPattern.matcher(address);
    return addressMatcher.matches();
  }

  public String getName() {
    String[] tokens = address.split("@");
    return tokens[0];
  }

  public String getDomain() {
    String[] tokens = address.split("@");
    return tokens[1];
  }

  public String getAddress() {
    return address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Email email)) {
      return false;
    }
    return getAddress().equals(email.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAddress());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("address", address).toString();
  }
}
