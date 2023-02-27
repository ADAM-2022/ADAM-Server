package com.adam.server.security.jwt;

import static java.util.Objects.nonNull;

import com.adam.server.common.model.Id;
import com.adam.server.user.domain.Role;
import com.adam.server.user.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

  private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

  private final Jwt jwt;

  private final JwtProperties jwtProperties;

  public JwtAuthenticationTokenFilter(Jwt jwt, JwtProperties jwtProperties) {
    this.jwt = jwt;
    this.jwtProperties = jwtProperties;
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      String accessToken = obtainAccessToken(request);

      if (accessToken != null) {
        Jwt.Claims claims = jwt.verify(accessToken);

        Id<User, Long> id = claims.id;
        Role role = claims.role;

        if (nonNull(id) && nonNull(role)) {
          JwtAuthentication jwtAuthentication = new JwtAuthentication(id, role);
          List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role.getRole());

          JwtAuthenticationToken jwtAuthenticationToken =
              new JwtAuthenticationToken(jwtAuthentication, null, authorities);
          jwtAuthenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
        }
      }
    }

    chain.doFilter(request, response);
  }

  private String obtainAccessToken(HttpServletRequest request) {
    String accessToken = request.getHeader(jwtProperties.getHeaderKey());
    if (accessToken == null) {
      return null;
    }

    String decodedAccessToken = URLDecoder.decode(accessToken, StandardCharsets.UTF_8);
    String[] decodedAccessTokenParts = decodedAccessToken.split(" ");
    if (decodedAccessTokenParts.length != 2) {
      return null;
    }

    String scheme = decodedAccessTokenParts[0];
    String obtainedAccessToken = decodedAccessTokenParts[1];
    return BEARER.matcher(scheme).matches() ? obtainedAccessToken : null;
  }
}
