package io.github.zhaord.dynamicapi.aspect;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import springfox.documentation.service.ResolvedMethodParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaord
 */
@Aspect
@Component
@AllArgsConstructor
public class HandlerMethodResolverAspect {

    @Around("execution(* springfox.documentation.spring.web.readers.operation.HandlerMethodResolver.methodParameters(..))")
    public Object pointCut(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        HandlerMethod methodToResolve = (HandlerMethod) args[0];
        List<ResolvedMethodParameter> proceed = (List<ResolvedMethodParameter>)point.proceed();

        Method method = methodToResolve.getMethod();
        boolean isGet = method.getName().startsWith("get") || method.getName().startsWith("find");
        // 使用DynamicApi注解的类，并且是非get请求，改变参数内容，以便动态支持RequestBody注解
        if (AnnotationUtils.findAnnotation(methodToResolve.getBeanType(), DynamicApi.class)!=null && !isGet){
            List<DynamicResolvedMethodParam> paramList = new ArrayList<>();
            for (ResolvedMethodParameter param:
                 proceed) {
                paramList.add(new DynamicResolvedMethodParam(param));
            }
            return paramList;

        }
        return proceed;
    }

    // DynamicApi 解析完成的方法参数
    public static class DynamicResolvedMethodParam extends ResolvedMethodParameter{
        private final ResolvedMethodParameter parameter;
        public DynamicResolvedMethodParam(ResolvedMethodParameter resolvedMethodParameter) {
            super(resolvedMethodParameter.getParameterIndex(),
                    resolvedMethodParameter.defaultName().orElse("")
                    , resolvedMethodParameter.getAnnotations(), resolvedMethodParameter.getParameterType());
            this.parameter = resolvedMethodParameter;
        }

        @Override
        public boolean hasParameterAnnotation(Class<? extends Annotation> annotation) {
            if (RequestBody.class.equals(annotation)){
                if(parameter.hasParameterAnnotation(annotation)){
                    return true;
                }
                // 参数类型是 是指定包下的bean
                if(parameter.getParameterType().getTypeName().startsWith("io.github.zhaord.dynamicapi")){
                    return true;
                }
            }
            return super.hasParameterAnnotation(annotation);
        }

        /*@Override
        public <T extends Annotation> Optional<T> findAnnotation(final Class<T> annotation) {
            if (RequestBody.class.equals(annotation)){
                if(parameter.hasParameterAnnotation(annotation)){
                    super.findAnnotation(annotation);
                }
                if(parameter.getParameterType().getTypeName().startsWith("io.github.zhaord.dynamicapi")){
                    Annotation required = AnnotationParser.annotationForMap(
                            RequestBody.class, Collections.singletonMap("required", true));
                    return Optional.ofNullable(required);
                }
            }
            return super.findAnnotation(annotation);
        }*/

    }

}
