package com.spring.mummus.aop;

import com.spring.mummus.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private static final String TRX_NO = "trxNo";

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController(){}


    @Before("restController()")
    public void beforeAPI(JoinPoint joinPoint)  {
        String apiName = joinPoint.getSignature().getName();
        String trxNo = LogUtils.generateTrxNo();

        // MDC: 스레드-세이프한 로깅 컨텍스트
        MDC.put(TRX_NO, trxNo);
        log.info("[{}] ========== {} START ==========", trxNo, apiName);
    }


    @After("restController()")
    public void AfterAPI(JoinPoint joinPoint) {
        String apiName = joinPoint.getSignature().getName();
        String trxNo = MDC.get(TRX_NO);

        log.info("[{}] ========== {} END ==========", trxNo, apiName);
        log.info("");

        MDC.remove(TRX_NO);
    }

}
