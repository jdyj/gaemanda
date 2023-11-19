package com.seoultech.gaemanda.login;

import com.seoultech.gaemanda.exception.NotExistMemberException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
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
      throw new NotExistMemberException();
    }
    return Long.valueOf(memberId.toString());
  }
}
