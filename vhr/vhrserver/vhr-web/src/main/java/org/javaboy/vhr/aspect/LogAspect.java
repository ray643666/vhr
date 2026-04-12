package org.javaboy.vhr.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.javaboy.vhr.annotation.Log;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.service.AsyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private AsyncLogService asyncLogService;

    @Pointcut("@annotation(org.javaboy.vhr.annotation.Log)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前登录用户
        Hr currentHr = null;
        try {
            currentHr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            // 未登录
        }

        String username = currentHr != null ? currentHr.getName() : "匿名";

        // 获取注解描述
        Log logAnnotation = null;
        try {
            logAnnotation = joinPoint.getTarget().getClass()
                    .getMethod(joinPoint.getSignature().getName(),
                            ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterTypes())
                    .getAnnotation(Log.class);
        } catch (NoSuchMethodException e) {
            // 忽略
        }

        String operation = logAnnotation != null ? logAnnotation.value() : joinPoint.getSignature().getName();

        // 获取请求
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            // 忽略
        }

        // 异步记录
        asyncLogService.recordOperation(username, operation, request);

        // 执行原方法
        return joinPoint.proceed();
    }
}
