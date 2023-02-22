package com.adam.server.user.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Name {

  @Column(length = 20, nullable = false)
  private String name;

  private static final Pattern namePattern = Pattern.compile("[\\p{L}|\\p{N}]+");

  protected Name() {}

  public Name(String name) {
    checkArgument(isNotEmpty(name), "name must be provided.");
    checkArgument(
        name.length() >= 2 && name.length() <= 20,
        "name length must be between 2 and 20 characters.");
    checkArgument(checkName(name), "Invalid name: " + name);

    this.name = name;
  }

  private static boolean checkName(String name) {
    Matcher nameMatcher = namePattern.matcher(name);
    return nameMatcher.matches();
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Name name1)) {
      return false;
    }
    return name.equals(name1.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("name", name).toString();
  }
}
