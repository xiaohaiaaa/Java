package com.hai.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hai.test.TestApplication;

/**
 * @ClassName interviewTest
 * @Description 笔试题测试
 * @Author ZXH
 * @Date 2021/12/3 11:34
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public class interviewTest {

    /**
     * 倒序输出没有重复的元素
     */
    @Test
    public void reverseOrder() {
        int[] array = {7, 8, 3, 1, 3, 2, 1, 8, 6};
        int num1;
        int num2;
        StringBuffer numList1 = new StringBuffer();
        for(int i=0; i<=array.length-1; i++) {
            boolean isExsist = false;
            for(int j=0; j<=array.length-1; j++) {
                if(i == j) {
                    continue;
                }
                num1 = array[i];
                num2 = array[j];
                if(num2 == num1) {
                    isExsist = true;
                    break;
                }
            }
            if(!isExsist) {
                numList1.append(array[i]);
            }
        }
        numList1.reverse();
        System.out.println(numList1);
    }
}
