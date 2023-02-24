package com.adam.server.security.jwt;

import static com.google.common.base.Preconditions.checkArgument;

import com.adam.server.common.model.Id;
import com.adam.server.user.domain.Role;
import com.adam.server.user.domain.User;

public record JwtAuthentication(Id<User, Long> id, Role role) {

  public JwtAuthentication {
    checkArgument(id != null, "id must be provided");
    checkArgument(role != null, "role must be provided");
  }
}
