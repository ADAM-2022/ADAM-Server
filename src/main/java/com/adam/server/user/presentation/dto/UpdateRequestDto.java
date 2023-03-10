package com.adam.server.user.presentation.dto;

import com.adam.server.user.domain.Name;
import com.adam.server.user.domain.SessionTime;
import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateRequestDto {

  @Schema(type = "string")
  private Name name;

  private SessionTime sessionTime;

  public Name getName() {
    return name;
  }

  public SessionTime getSessionTime() {
    return sessionTime;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public void setSessionTime(SessionTime sessionTime) {
    this.sessionTime = sessionTime;
  }
}
