package com.adam.server.common.model;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Id<R, V> {

  private final Class<R> reference;

  private final V value;

  private Id(Class<R> reference, V value) {
    this.reference = reference;
    this.value = value;
  }

  public static <R, V> Id<R, V> of(Class<R> reference, V value) {
    checkArgument(reference != null, "reference must be provided.");
    checkArgument(value != null, "value must be provided.");

    return new Id<>(reference, value);
  }

  public V value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Id<?, ?> id)) {
      return false;
    }
    return reference.equals(id.reference) && value.equals(id.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, value);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("reference", reference)
        .append("value", value)
        .toString();
  }
}
