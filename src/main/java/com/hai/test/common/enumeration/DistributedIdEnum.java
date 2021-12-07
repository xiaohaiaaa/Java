package com.hai.test.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName DistributedIdEnum
 * @Description
 * @Author ZXH
 * @Date 2021/12/7 17:08
 * @Version 1.0
 **/
@Getter
@AllArgsConstructor
public enum DistributedIdEnum {

    TheOrder("the_order", "d");

    private String dbTableName;
    private String prefix;

}
