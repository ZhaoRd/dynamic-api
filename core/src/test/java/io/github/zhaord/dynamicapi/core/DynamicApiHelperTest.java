package io.github.zhaord.dynamicapi.core;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicApiHelperTest {

    @ParameterizedTest
    @MethodSource("testServiceNameSource")
    void testServiceName(String name,String eq) {
        assertThat(DynamicApiHelper.serviceName(name)).isEqualTo(eq);

    }

    public static Stream<Arguments> testServiceNameSource() {
        return Stream.of(
                Arguments.arguments("demo", "demo"),
                Arguments.arguments("demoService", "demo"),
                Arguments.arguments("demoAppService", "demo"),
                Arguments.arguments("demoApplicationService", "demo"),
                Arguments.arguments("demoApplication", "demo")

        );
    }

    @Test
    void testUrl() {
        assertThat(DynamicApiHelper.url("api/services","demo","getName")).isEqualTo(
                "/api/services/demo/getName"
        );
    }

    @ParameterizedTest
    @MethodSource("testGetRequestMethodSource")
    void testGetRequestMethod(String methodName,RequestMethod requestMethod) {
        assertThat(DynamicApiHelper.getRequestMethod(methodName))
                .isEqualTo(requestMethod);
    }

    public static Stream<Arguments> testGetRequestMethodSource() {
        return Stream.of(
                Arguments.arguments("demo", RequestMethod.POST),
                Arguments.arguments("getName", RequestMethod.GET),
                Arguments.arguments("findName", RequestMethod.GET),
                Arguments.arguments("deleteById", RequestMethod.DELETE),
                Arguments.arguments("delById", RequestMethod.DELETE),
                Arguments.arguments("removeById", RequestMethod.DELETE),
                Arguments.arguments("addName", RequestMethod.POST),
                Arguments.arguments("insertName", RequestMethod.POST),
                Arguments.arguments("postName", RequestMethod.POST),
                Arguments.arguments("updateName", RequestMethod.PUT),
                Arguments.arguments("putName", RequestMethod.PUT)
        );
    }

    @Test
    void testDefaultHttpMethod() {
        assertThat(DynamicApiHelper.defaultHttpMethod()).isEqualTo(RequestMethod.POST);
    }

    @Test
    void getMethodsOfType() {
        List<Method> methodList = DynamicApiHelper.getMethodsOfType(DemoService.class);
        assertThat(methodList.size()).isEqualTo(1);
        assertThat(methodList.get(0).getName()).isEqualTo("hello");
    }

    @Test
    void buildControllerInfo() {
        DemoService demoService = new DemoService();
        DynamicApiControllerInfo ctrlInfo = DynamicApiHelper.buildControllerInfo(demoService, "demoService");

        assertThat(ctrlInfo).isNotNull();
        assertThat(ctrlInfo.getBeanName()).isEqualTo("demoService");
        assertThat(ctrlInfo.getBeanClass()).isEqualTo(DemoService.class);
        Map<String, DynamicApiMethodInfo> methodMap = ctrlInfo.getMethodMap();
        assertThat(methodMap.size()).isEqualTo(1);
        assertThat(methodMap.containsKey("hello")).isTrue();
        DynamicApiMethodInfo helloMethod = methodMap.getOrDefault("hello", null);
        assertThat(helloMethod).isNotNull();
        assertThat(helloMethod.getMethodName()).isEqualTo("hello");
    }

    @Test
    void buildMethodInfo() throws NoSuchMethodException {
        Method hello = DemoService.class.getMethod("hello");
        DynamicApiMethodInfo methodInfo = DynamicApiHelper.buildMethodInfo(hello);

        assertThat(methodInfo).isNotNull();
        assertThat(methodInfo.getMethodName()).isEqualTo("hello");
        assertThat(methodInfo.getMethod()).isEqualTo(hello);
    }

    @Test
    void toCamelCase() {
        assertThat(DynamicApiHelper.toCamelCase("DemoService"))
                .isEqualTo("demoService");
        assertThat(DynamicApiHelper.toCamelCase("testService"))
                .isEqualTo("testService");
    }

    public static class DemoService{
        public static void add(){}
        public String hello(){return "";}
        void test(){}
        protected void test2(){}
        private void test3(){}
    }
}