package com.mos.global.auth.config;

import com.mos.domain.member.dto.MemberDto;
import com.mos.global.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final HttpSession session;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean isLoginUserAnno = parameter.getParameterAnnotation(LoginUser.class) != null;
    boolean isUserClass = MemberDto.class.equals(parameter.getParameterType());
    return isLoginUserAnno && isUserClass;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    return session.getAttribute("loginUser");
  }
}
