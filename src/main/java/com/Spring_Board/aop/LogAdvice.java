package com.Spring_Board.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Aspect
@Component
public class LogAdvice 
{
	@Before("execution(* com.Spring_Board.service.SampleService*.*(..))")
	public void logBefore()
	{
		log.info("=================");
	}
	
	
	@Before("execution(* com.Spring_Board.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2)
	{
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	
	
	@AfterThrowing(pointcut = "execution(* com.Spring_Board.service.SampleService*.*(..))", 
				   throwing = "exception")
	public void logException(Exception exception)
	{
		log.info("예외 발생");
		log.info("예외 : " + exception);
	}
	
	
	@Around("execution(* com.Spring_Board.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp)
	{
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		
		Object result = null;
		
		log.info("Target : " + pjp.getTarget());
		log.info("Param : " + Arrays.deepToString(pjp.getArgs()));
		
		
		try
		{
			result = pjp.proceed();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		
		log.info("시간 : " + (end - start));
		
		return result;
	}
}
