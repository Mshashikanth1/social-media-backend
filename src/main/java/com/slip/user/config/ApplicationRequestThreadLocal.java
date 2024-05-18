package com.slip.user.config;

import com.slip.user.security.UserContext;

public class ApplicationRequestThreadLocal {

  private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();

  public ApplicationRequestThreadLocal() {}

  public static void setUserContextThreadLocal(UserContext userContext) {
    userContextThreadLocal.set(userContext);
  }

  public static UserContext getUserContextThreadLocal() {
    return userContextThreadLocal.get() != null ? userContextThreadLocal.get() : null;
  }

  public static void clear() {
    userContextThreadLocal.remove();
  }
}
