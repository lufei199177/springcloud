package com.springcloud.demo.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lufei
 * @date 2020/9/18
 * @desc
 */
@Data
public class Test {

    private String name;
    private int age;

    public static void main(String[] args) {
        List<Test> list=test();
        /*Map<String,Test> map=list.stream().collect(Collectors.toMap(Test::getName,a->a,(k1,k2)->k1));
        map.forEach((k,v)->{
            System.out.println(k);
            System.out.println(v.age);
        });*/
        /*List<Test> tests=list.stream().filter(item->item.getAge()>5).collect(Collectors.toList());
        tests.forEach(item->{
            System.out.println(item.getName());
        });*/

    }

    private static List<Test> test(){
        List<Test> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Test test=new Test();
            test.setAge(i);
            test.setName("lilei"+i);
            list.add(test);
        }
        return list;
    }

}
