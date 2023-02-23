package com.adam.server.user.presentation;

import com.adam.server.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;
}
