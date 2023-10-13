package com.seoultech.gaemanda.login;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final HttpServletRequest httpServletRequest;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {

    boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
    boolean isLongClass = Long.class.equals(parameter.getParameterType());

    return isLoginUserAnnotation && isLongClass;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Object memberId = httpServletRequest.getAttribute("memberId");
    if (memberId == null) {
      throw new RuntimeException();
    }
    return Long.valueOf(memberId.toString());
  }
}
