package com.lucky.newyear.config;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class CookieInterceptor implements HandlerInterceptor {

    // 컨트롤러를 경유(접근)하기 직전에 실행되는 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userUUID = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userUUID".equals(cookie.getName())) {
                    userUUID = cookie.getValue();
                    break;
                }
            }
        }

        // 쿠키 값을 ThreadLocal에 저장
        if (userUUID != null) {
            UserContext.setUserUUID(userUUID);
        } else {
            UserContext.setUserUUID(null);
        }

        return true;  // 계속해서 요청을 처리하도록 함
    }

    // 컨트롤러를 경유(접근) 한 후, 즉 화면(View)으로 결과를 전달하기 전에 실행되는 메서드
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    }
}
