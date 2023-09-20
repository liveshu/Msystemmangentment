package com.example.aspect;

import com.example.annotation.AutoFill;
import com.example.annotation.AutoFill;
import com.example.constant.AutoFillConstant;
import com.example.context.BaseContext;
import com.example.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
/*自定义切面，AOP*/
@Component
@Slf4j
public class AutoFillAspect {
    /*切入点*/
    @Pointcut("execution(* com.example.mapper.*.*(..))&&@annotation(com.example.annotation.AutoFill)")
    public void autoFillPointCut(){
    }

    /*前置通知*/
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行填充");
        /*获取当前被拦截方法上数据库的操作类型*/
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        /*获取当前被拦截的方法的参数（实体对象）*/
        Object[] args = joinPoint.getArgs();
        if(args==null ||args.length==0){
            return;
        }

        Object entity = args[0];

        /*准备赋值的数据*/
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        /*根据不同操作类型，为对应的属性通过反射来赋值*/
        if(operationType==OperationType.INSERT){
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                /*通过反射给对象属性赋值*/
                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if(operationType==OperationType.UPDATE){
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                /*通过反射给对象属性赋值*/
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
