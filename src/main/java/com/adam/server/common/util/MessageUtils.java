package com.adam.server.common.util;

import static com.google.common.base.Preconditions.checkState;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

  private static MessageSourceAccessor messageSourceAccessor;

  private final MessageSource messageSource;

  public MessageUtils(MessageSource messageSource) {
    this.messageSource = messageSource;
    MessageUtils.setMessageSourceAccessor(new MessageSourceAccessor(messageSource));
  }

  public static String getMessage(String key) {
    checkState(null != messageSourceAccessor, "MessageSourceAccessor is not initialized.");
    return messageSourceAccessor.getMessage(key);
  }

  public static String getMessage(String key, Object... params) {
    checkState(null != messageSourceAccessor, "MessageSourceAccessor is not initialized.");
    return messageSourceAccessor.getMessage(key, params);
  }

  public static void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
    MessageUtils.messageSourceAccessor = messageSourceAccessor;
  }
}
