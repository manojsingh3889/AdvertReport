package org.crealytics.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.stream.Collectors;


@Aspect
@Component
public class LogKeeper {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogKeeper.class);

    @Around("org.crealytics.aspect.PointCutRepository.restLayer()")
    public Object controllerLayerTrail(ProceedingJoinPoint point) throws Throwable{

        MethodSignature signature = (MethodSignature) point.getSignature();
        RequestMapping methodAnnotation =  signature.getMethod().getAnnotation(RequestMapping.class);
        RequestMapping clazzAnnotation = signature.getMethod().getDeclaringClass().getAnnotation(RequestMapping.class);
        String uri = clazzAnnotation.value()[0]+methodAnnotation.value()[0];

        String parameterNames = Arrays.asList(signature.getParameterNames())
                .stream().collect(Collectors.toList()).toString();
        String values = Arrays.asList(point.getArgs())
                .stream().collect(Collectors.toList()).toString();
        //auditing
        long starttime = System.currentTimeMillis();
        LOGGER.info(String.format("URL(%s) Parameters(%s) Values(%s)",uri,parameterNames,values));
        LOGGER.info(String.format("Entering Method(%s)",signature.toShortString()));
        Object obj = point.proceed();
        LOGGER.info(String.format("Leaving Method(%s) TimeTaken(%dms)",point.getSignature().toShortString(),System.currentTimeMillis()-starttime));
        LOGGER.info(String.format("URL(%s) TimeTaken(%dms)",uri,System.currentTimeMillis()-starttime));
        return obj;
    }

    @Around("org.crealytics.aspect.PointCutRepository.serviceLayer()")
    public Object serviceLayerTrails(ProceedingJoinPoint point) throws Throwable{

        MethodSignature signature = (MethodSignature) point.getSignature();

        String parameterNames = Arrays.asList(signature.getParameterNames())
                .stream().collect(Collectors.toList()).toString();
        String values = Arrays.asList(point.getArgs())
                .stream().collect(Collectors.toList()).toString();
        //auditing
        long starttime = System.currentTimeMillis();
        LOGGER.info(String.format("Entering Method(%s)",signature.toShortString()));
        LOGGER.debug(String.format("Arguments(%s) Value(%s)",parameterNames,values));
        Object obj = point.proceed();
        LOGGER.info(String.format("Leaving Method(%s) TimeTaken(%dms)",point.getSignature().toShortString(),System.currentTimeMillis()-starttime));
        return obj;
    }
}
