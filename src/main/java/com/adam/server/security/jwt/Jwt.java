package com.adam.server.security.jwt;

import com.adam.server.common.model.Id;
import com.adam.server.user.domain.Role;
import com.adam.server.user.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public final class Jwt {

  private final JwtProperties jwtProperties;
  private final Algorithm algorithm;
  private final JWTVerifier jwtVerifier;

  public Jwt(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
    this.algorithm = Algorithm.HMAC512(jwtProperties.getClientSecret());
    this.jwtVerifier = JWT.require(algorithm).withIssuer(jwtProperties.getIssuer()).build();
  }

  public String accessToken(Claims claims) {
    String issuer = jwtProperties.getIssuer();
    int expirySeconds = jwtProperties.getExpirySeconds();
    Date issuedAt = new Date();
    Date expiresAt = new Date(issuedAt.getTime() + (expirySeconds * 1_000L));
    return JWT.create()
        .withIssuer(issuer)
        .withIssuedAt(issuedAt)
        .withExpiresAt(expiresAt)
        .withClaim("id", claims.id.value())
        .withClaim("role", claims.role.name())
        .sign(algorithm);
  }

  public Claims verify(String token) throws JWTVerificationException {
    return new Claims(jwtVerifier.verify(token));
  }

  public static class Claims {
    Id<User, Long> id;
    Role role;
    Date iat;
    Date exp;

    private Claims(Id<User, Long> id, Role role) {
      this.id = id;
      this.role = role;
    }

    Claims(DecodedJWT decodedJWT) {
      Claim id = decodedJWT.getClaim("id");
      this.id = Id.of(User.class, id.asLong());

      Claim role = decodedJWT.getClaim("role");
      this.role = Role.valueOf(role.asString());

      this.iat = decodedJWT.getIssuedAt();
      this.exp = decodedJWT.getExpiresAt();
    }

    public static Claims of(Id<User, Long> id, Role role) {
      return new Claims(id, role);
    }
  }
}
