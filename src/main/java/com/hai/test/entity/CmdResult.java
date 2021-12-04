package com.hai.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CmdResult
 * @Description cmd命令执行后结果类
 * @Author ZXH
 * @Date 2021/12/4 16:07
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmdResult {

    private boolean success;

    private String msg;
}