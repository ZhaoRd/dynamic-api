package io.github.zhaord.dynamicapi.service;


import io.github.zhaord.dynamicapi.annotation.DynamicApi;
import io.github.zhaord.dynamicapi.annotation.DontApiResultWrapper;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@DynamicApi
public class DemoService {

    public String getName(){
        return "ha";
    }

    public String getHello(String name){
        return "hi,"+name;
    }

    public int getAdd(int a, int b){
        return a+b;
    }

    public String getAddWithDto(AddDto input){
        return input.getNum1()+"+"+input.getNum2()+"="+ (input.getNum1()+input.getNum2());
    }

    public AddVo getAddWithDtoAndResultAddVo(AddDto input){
        AddVo vo = new AddVo();
        vo.setResult(input.getNum1()+"+"+input.getNum2()+"="+ (input.getNum1()+input.getNum2()));
        return vo;
    }

    public AddNameVo addName(@RequestBody NameDto input){
        AddNameVo vo = new AddNameVo();
        vo.setResult("add success "+ input.getName());
        return vo;
    }

    @Data
    public static class NameDto{
        private String name;
    }

    @Data
    public static class AddNameVo{
        private String result;
    }

    @Data
    public static class AddDto{
        private Integer num1;
        private Integer num2;
    }

    @Data
    public static class AddVo{
        private String result;
    }
}
