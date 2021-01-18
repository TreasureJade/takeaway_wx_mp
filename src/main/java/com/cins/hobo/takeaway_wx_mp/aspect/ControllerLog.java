package com.cins.hobo.takeaway_wx_mp.aspect;

import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author : hobo
 * @className : ServiceLog
 * @date: 2021/1/12
 * @description: service层日志
 */
@Slf4j
@Component
@Aspect
public class ControllerLog {

    @Pointcut("execution(public * com.cins.hobo.takeaway_wx_mp.controller.*.*(..))")
    public void controller() {

    }

    @Before("controller()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "." + signature.getName();
        log.info("-----------------------------------");
        log.info("当前执行的controller方法： " + method);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("参数：" + arg);
        }
    }

    @AfterReturning(pointcut = "controller()", returning = "ret")
    public void after(Object ret) {
        if (!ret.getClass().getName().equals(ResultVO.class.getName())) {
            log.info("controller返回参数" + ret);
            log.info("-----------------------------------");
            return;
        }
        ResultVO result = (ResultVO) ret;
        if (result.getCode()!=0){
            log.error(result.getMsg());
        }
        log.info("controller返回参数" + ret);
        log.info("-----------------------------------");
    }


}
