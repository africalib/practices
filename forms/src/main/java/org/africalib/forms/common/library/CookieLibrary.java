package org.africalib.forms.common.library;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.africalib.forms.common.enumeration.CookieName;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CookieLibrary {

    // 쿠키 입력
    public void setCookie(CookieName name, String value) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletResponse response = attributes.getResponse();

            if (response != null) {
                Cookie cookie = new Cookie(name.name(), value);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }
    }

    // 쿠키 조회
    public String getCookie(CookieName name) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name.name())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
