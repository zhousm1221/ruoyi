package com.ruoyi.web.controller.sheet;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SheetOption {
    private String name;   //工作表名称
    private String color;  //颜色
    private int status;     //状态
    private int order;     //排序
    private String index;      //索引
    private int hide;       //是否隐藏
    private List<Celldata> celldata = new ArrayList<Celldata>();      //数据


}

