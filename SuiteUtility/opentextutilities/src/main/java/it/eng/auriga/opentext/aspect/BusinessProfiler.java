package it.eng.auriga.opentext.aspect;


//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;




@Configuration
public class BusinessProfiler {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BusinessProfiler.class);

//	@Pointcut("execution(* it.eng.auriga.opentext.delegate.*.*(..)) || execution(* it.eng.auriga.opentext.config.DWOAuthenticationProvider.authenticate(..)) ")
	protected void audit() {	}

//	@Around("audit()")
//	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
//		long start = System.currentTimeMillis();
//		logger.info("Going to call the method.");
////		pjp.getArgs()
//		Object output = pjp.proceed();
//		logger.info("Method execution completed. " + output);
//		long elapsedTime = System.currentTimeMillis() - start;
//		logger.info("Method execution time: " + elapsedTime + " milliseconds.");
//		return output;
//	}

}