package com.arvato.core.cache;

import com.arvato.core.service.RedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * AOP实现Redis缓存处理
 */
@Component
@Aspect
public class RedisAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisAspect.class);

    @Autowired
    private RedisService redisService;


    /**
     * 拦截所有元注解RedisCache注解的方法
     */
    @Pointcut("@annotation(com.arvato.core.annotation.RedisCache)")
    public void pointcutMethod(){

    }

    /**
     * 环绕处理，先从Redis里获取缓存,查询不到，就查询MySQL数据库，
     * 然后再保存到Redis缓存里
     * @param joinPoint
     * @return
     */
    @Around("pointcutMethod()")
    public Object around(ProceedingJoinPoint joinPoint){

        //前置：从Redis里获取缓存
        //先获取目标方法参数
        long startTime = System.currentTimeMillis();
        String applId = null;
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            applId = String.valueOf(args[0]);
        }

        //获取目标方法所在类
        String target = joinPoint.getTarget().toString();
        String className = target.split("@")[0];

        //获取目标方法的方法名称
        String methodName = joinPoint.getSignature().getName();

        //redis中key格式：    applId:方法名称
        String redisKey = applId + ":" + className + "." + methodName;
        Object status = ifBooleanStr(redisKey);
        if(status == "del" || status == "update"){
            operationAction(applId + ":" + className + ".findByUserid");
            return "";
        }else if(status == "save"){
            return "";
        }
        /**
         * 1.先查询redis中是否存在缓存
         * 2.如果存在，则直接从redis中获取数据，如果不存在则从先数据库中获取数据，并将数据缓存到redis中
         * **/
        //Object obj = redisCache.getDataFromRedis(redisKey);

        Object obj = redisService.getKeys(redisKey);
        if(obj!=null){
            LOGGER.info("**********从Redis中查到了数据**********");
            LOGGER.info("Redis的KEY值:"+redisKey);
            LOGGER.info("REDIS的VALUE值:"+obj.toString());
            return obj;
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("Redis缓存AOP处理所用时间:"+(endTime-startTime));
        LOGGER.info("**********没有从Redis查到数据**********");
        try{
            obj = joinPoint.proceed();
        }catch(Throwable e){
            e.printStackTrace();
        }
        LOGGER.info("**********开始从MySQL查询数据**********");
        //后置：将数据库查到的数据保存到Redis
        Boolean code = redisService.setkeyTimes(redisKey, obj,60L);
        if(code == true){
            LOGGER.info("**********数据成功保存到Redis缓存!!!**********");
            LOGGER.info("Redis的KEY值:"+redisKey);
            LOGGER.info("REDIS的VALUE值:"+obj.toString());
        }
        return obj;
    }


    @After("pointcutMethod()")
    public Object around(JoinPoint joinPoint){
        LOGGER.info("**********操作**********");
        String applId = null;
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            applId = String.valueOf(args[0]);
        }

        //获取目标方法所在类
        String target = joinPoint.getTarget().toString();
        String className = target.split("@")[0];

        //获取目标方法的方法名称
        String methodName = joinPoint.getSignature().getName();

        //redis中key格式：    applId:方法名称
        String redisKey = applId + ":" + className + "." + methodName;
        boolean haskeycode = redisService.hasKey(redisKey);
        LOGGER.info("**********判断键是否存在**********" + haskeycode);
        Object redisobj = redisService.findKeytoTimes(redisKey);
        LOGGER.info("**********redis过期时间**********" + redisobj);
        Object obj = joinPoint.getStaticPart();
        return  haskeycode;
    }

    /**
     * 判断字符串是否包含某个字段
     * **/
    public  Object ifBooleanStr(String str){
        if(str.contains("del")){
            return  "del";
        }else if(str.contains("update")){
            return  "update";
        }else{
            return  "";
        }
    }

    /**
     * 根据Key删除redis缓存信息
     * @param redisKey 键
     * **/
    public void operationAction(String redisKey){
        boolean haskeycode = redisService.hasKey(redisKey);
        if(haskeycode){
            redisService.del(redisKey);
        }
    }

}
