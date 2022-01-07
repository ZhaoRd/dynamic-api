package io.github.zhaord.dynamicapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiDemoServiceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private DemoService demoService;

    @Test
    void testServiceNotNull(){
        assertThat(demoService).isNotNull();
    }

    @Test
    void testGetName() throws Exception {
        String url = "/api/service/demo/getName";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                        .accept(MediaType.APPLICATION_JSON)) // 断言返回结果是json
                .andReturn();// 得到返回结果

        MockHttpServletResponse response = mvcResult.getResponse();
        //拿到请求返回码
        int status = response.getStatus();
        //拿到结果
        String contentAsString = response.getContentAsString();

        assertThat(status).isEqualTo(200);
        assertThat(contentAsString).isEqualTo("ha");
    }

    @Test
    void testGetHello() throws Exception {
        String url = "/api/service/demo/getHello?name=dd";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                        .accept(MediaType.APPLICATION_JSON)) // 断言返回结果是json
                .andReturn();// 得到返回结果

        MockHttpServletResponse response = mvcResult.getResponse();
        //拿到请求返回码
        int status = response.getStatus();
        //拿到结果
        String contentAsString = response.getContentAsString();

        assertThat(status).isEqualTo(200);
        assertThat(contentAsString).isEqualTo("hi,dd");
    }

    @Test
    void test_getAdd() throws Exception {
        String url = "/api/service/demo/getAdd?a=1&b=2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                        .accept(MediaType.APPLICATION_JSON)) // 断言返回结果是json
                .andReturn();// 得到返回结果

        MockHttpServletResponse response = mvcResult.getResponse();
        //拿到请求返回码
        int status = response.getStatus();
        //拿到结果
        String contentAsString = response.getContentAsString();

        assertThat(status).isEqualTo(200);
        assertThat(contentAsString).isEqualTo("3");
    }

    @Test
    void test_getAddWithDto() throws Exception {
        String url = "/api/service/demo/getAddWithDto?num1=1&num2=2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                        .accept(MediaType.APPLICATION_JSON)) // 断言返回结果是json
                .andReturn();// 得到返回结果

        MockHttpServletResponse response = mvcResult.getResponse();
        //拿到请求返回码
        int status = response.getStatus();
        //拿到结果
        String contentAsString = response.getContentAsString();

        assertThat(status).isEqualTo(200);
        assertThat(contentAsString).isEqualTo("1+2=3");
    }

    @Test
    void test_getAddWithDtoAndResultAddVo() throws Exception {
        String url = "/api/service/demo/getAddWithDtoAndResultAddVo?num1=1&num2=2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                        .accept(MediaType.APPLICATION_JSON)) // 断言返回结果是json
                .andReturn();// 得到返回结果

        MockHttpServletResponse response = mvcResult.getResponse();
        //拿到请求返回码
        int status = response.getStatus();
        //拿到结果
        String contentAsString = response.getContentAsString();
        assertThat(status).isEqualTo(200);
        ObjectMapper jsonMap = new ObjectMapper();
        DemoService.AddVo addVo = jsonMap.readValue(contentAsString, DemoService.AddVo.class);
        assertThat(addVo).isNotNull();
        assertThat(addVo.getResult()).isEqualTo("1+2=3");

    }

    @Test
    void test_addName() throws Exception {
        ObjectMapper jsonMap = new ObjectMapper();
        String url = "/api/service/demo/addName";
        DemoService.NameDto nameDto = new DemoService.NameDto();
        nameDto.setName("haha");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMap.writeValueAsString(nameDto))
                        .accept(MediaType.APPLICATION_JSON)) // 断言返回结果是json
                .andReturn();// 得到返回结果

        MockHttpServletResponse response = mvcResult.getResponse();
        //拿到请求返回码
        int status = response.getStatus();
        //拿到结果
        String contentAsString = response.getContentAsString();
        assertThat(status).isEqualTo(200);

        DemoService.AddNameVo addVo = jsonMap.readValue(contentAsString, DemoService.AddNameVo.class);
        assertThat(addVo).isNotNull();
        assertThat(addVo.getResult()).isEqualTo("add success haha");

    }


}
