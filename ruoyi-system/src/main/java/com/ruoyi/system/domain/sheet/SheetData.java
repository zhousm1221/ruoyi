package com.ruoyi.system.domain.sheet;

import lombok.Data;

import java.util.*;

/**
 * @作者: Roker
 * @时间: 2022年6月26日12:57:09
 * @Copyright: Don`t be the same,be better!
 * @Description:
 */

@Data
public class SheetData {
    private String name;   //工作表名称
    private String color;  //颜色
    private int status;     //状态
    private int order;     //排序
    private String index;      //索引
    private int hide;       //是否隐藏
    private List<HashMap> celldata = new LinkedList<>();      //数据

}
