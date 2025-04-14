package com.example.test.demos.interceptor;

import com.example.test.demos.annotations.RequiresPermissions;
import com.example.test.demos.servicer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    // 合并后的 preHandle 方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        System.out.println("请求路径：" + uri);

        // 放行公共资源和登录、注册接口
        if (uri.equals("/") || uri.startsWith("/public/") ||
                uri.equals("/login") || uri.equals("/register") ||
                uri.equals("/api/userinfo")||uri.equals("/user/logout")) {  // 放行 /api/userinfo 接口
            return true;
        }

        // 从 Session 获取当前用户的用户名
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");

        // 如果 handler 是 HandlerMethod（控制器方法）
        if (handler instanceof HandlerMethod) {
            // 检查用户是否已登录
            if (username == null) {
                // 未登录，重定向到登录页面
                response.sendRedirect("/public/login.html"); // 请根据实际情况调整路径
                return false;
            }

            // 获取当前用户的权限ID列表
            List<Integer> permissionIds = userService.getPermissionsByUsername(username);


            // 获取目标方法的权限要求
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);

            if (annotation != null) {
                // 获取接口所需的权限ID
                int[] requiredPermissions = annotation.value();
                for (int requiredPermission : requiredPermissions) {
                    if (permissionIds.contains(requiredPermission)) {
                        return true; // 用户拥有权限，允许访问
                    }
                }
                // 用户没有对应权限
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 无权限
                response.getWriter().write("权限不足");
                return false;
            }

            // 没有 @RequiresPermissions 注解，允许访问
            return true;
        }

        // 如果不是 HandlerMethod（例如静态资源），并且请求路径以 /private/ 开头
        if (uri.startsWith("/private/")) {
            // 检查用户是否已登录
            if (username == null) {
                // 未登录，重定向到登录页面
                response.sendRedirect("/public/login.html");
                return false;
            }
            // 获取当前用户的权限ID列表
            List<Integer> permissionIds = userService.getPermissionsByUsername(username);
            // 如果用户没有系统管理权限（权限ID 18），则阻止访问某些管理页面
            if (!permissionIds.contains(18)) {
                if (uri.startsWith("/private/position-management.html") ||
                        uri.startsWith("/private/user-management.html") ||
                        uri.startsWith("/private/admin-functions.html")) {
                    // 重定向到系统管理页面
                    response.sendRedirect("/private/system-admin.html");
                    return false;
                }
            }

            // 已登录，允许访问
            return true;
        }

        // 其他请求，继续处理
        return true;
    }
}
