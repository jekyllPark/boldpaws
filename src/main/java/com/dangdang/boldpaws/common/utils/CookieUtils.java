package com.dangdang.boldpaws.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class CookieUtils {
    public static void add(HttpServletResponse response, String name, String value, boolean isHttpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(isHttpOnly);
        response.addCookie(cookie);
    }
    public static String get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0 || cookies == null) return null;
        return Arrays.stream(cookies).filter(c -> name.equals(c.getName())).findFirst().map(Cookie::getValue).orElse(null);
    }
    public static void delete(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0 || cookies == null) return;
        Arrays.stream(cookies).filter(c -> name.equals(c.getName())).findFirst().ifPresent(c -> delete(response, c));
    }
    private static void delete(HttpServletResponse response, Cookie cookie) {
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
