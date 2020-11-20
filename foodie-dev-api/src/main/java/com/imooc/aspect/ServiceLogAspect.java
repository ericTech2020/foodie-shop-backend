package com.imooc.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ServiceLogAspect {



    public static final Logger log= LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * AOP 通知
     * 1.前置通知：在方法调用之前执行
     * 2.后置通知：在方法正常调用之后执行
     * 3.环绕通知：在方法调用之前和之后，都分别可以执行的通知
     * 4.异常通知：在方法正常调用过程发生异常，则通知
     * 5.最终通知：在方法正常调用之后执行
     */
    /**
     * 切面表达式
     * execution 代表索要执行的表达式主体
     * 第一处 * 代表方法返回类型 * 代表所有类型
     * 第二处 包名代表aop监控的类的所有的包
     * 第三处 .. 代表该包以及其子包下的所有类方法
     * 第四处 * 代表类名，*代表所有类
     * 第五处 *(..) * 代表类中的方法名,(..)代表方法中的任何参数
     * @Aspect:作用是把当前类标识为一个切面供容器读取
     * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
     * @Around：环绕增强，相当于MethodInterceptor
     * @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
     * @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
     * @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
     * @After: final增强，不管是抛出异常或者正常退出都会执
     */

    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("=======start execute {}.{}=======", joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());


        //记录开始时间
        long begin=System.currentTimeMillis();
        //执行目标方法
        Object result=joinPoint.proceed();
        //记录结束时间
        long end =System.currentTimeMillis();
        long takeTime=end-begin;

        if(takeTime>3000){
            log.error("=======execute end, time used:{} mile second",takeTime);
        } else if(takeTime>2000){
            log.warn("=======execute end, time used:{} mile second",takeTime);
        }else {
            log.info("=======execute end, time used:{} mile second",takeTime);
        }
        return result;
    }

}
