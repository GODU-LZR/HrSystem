package com.example.salarymanage.config;


import com.example.salarymanage.annotations.RequiresPermissions;
import com.example.salarymanage.feign.UserInfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final UserInfoClient userInfoClient;

    @Autowired
    public AuthenticationInterceptor(@Lazy UserInfoClient userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        System.out.println("请求路径：" + uri);

        // 放行公共资源和登录、注册接口
        if (uri.equals("/") || uri.startsWith("/public/") ||
                uri.equals("/login") || uri.startsWith("/register") ||
                uri.equals("/api/**") || uri.equals("/user/logout")) {  // 放行 /api/userinfo 接口
            return true;
        }

        // 从 Session 获取当前用户的用户名
        String username = (String) request.getSession().getAttribute("user");
        // 如果不是 HandlerMethod，允许访问（例如静态资源）
        if (!(handler instanceof HandlerMethod)) {
            // 检查用户是否已登录
            if (username == null) {
                // 未登录，重定向到登录页面
                response.sendRedirect("http://121.43.159.232:8080/public/login.html"); // 请根据实际情况调整路径
                return false;
            }
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();



        // 检查用户是否已登录
        if (username == null) {
            // 未登录，重定向到登录页面
            response.sendRedirect("http://121.43.159.232:8080/public/login.html"); // 请根据实际情况调整路径
            return false;
        }

        // 使用Feign客户端获取用户权限
        if (userInfoClient == null) {
            // Feign client不可用
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("权限不足");
            return false;
        }

        Map<String, Object> userInfo = userInfoClient.getUserInfo();

        if (userInfo == null || !userInfo.containsKey("permissions")) {
            // 无法获取用户权限，返回403
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("权限不足");
            return false;
        }

        List<Integer> permissionIds = (List<Integer>) userInfo.get("permissions");
        for (Integer permissionId : permissionIds) {
            System.out.println("用户权限ID: " + permissionId);
        }

        // 获取目标方法的权限要求
        RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);

        if (annotation != null) {
            // 获取接口所需的权限ID
            int[] requiredPermissions = annotation.value();
            boolean hasPermission = false;
            for (int requiredPermission : requiredPermissions) {
                if (permissionIds.contains(requiredPermission)) {
                    hasPermission = true;
                    break;
                }
            }
            if (!hasPermission) {
                // 用户没有对应权限
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("权限不足");
                return false;
            }
        }

        // 没有 @RequiresPermissions 注解，允许访问
        return true;
    }
}
