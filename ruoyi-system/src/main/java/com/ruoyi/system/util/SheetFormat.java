package com.ruoyi.system.util;

import com.google.gson.Gson;
import com.ruoyi.system.domain.sheet.SheetData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @作者: Roker
 * @时间: 2022/6/26 13:06
 * @Copyright: Don`t be the same,be better!
 * @Description: Sheet内容修正格式处理工具类
 */
public class SheetFormat {
    private static Gson gson = new Gson();

    /**
     * @param header 模板sheet数据
     * @param content 填报sheet数据
     * @param mergeFlag 合并方式 true-修正合并 false-直接合并
     * @return
     */
    public static Object mergeSheetContent(String header,String content,Boolean mergeFlag){
        SheetData sheetData = gson.fromJson(header, SheetData.class);
        List sheetContent = gson.fromJson(content, LinkedList.class);
        List<HashMap> celldata = sheetData.getCelldata();
        //暂时不考虑存在列标题的情况
        int size = celldata.size();
        Map map = celldata.get(size - 1);
        Integer mLastRow = Integer.parseInt((String)map.get("c"));
        for (Object o : sheetContent) {
            if (o instanceof HashMap){
                HashMap eleCell = (HashMap) o;
                if (mergeFlag) {
                    eleCell.put("c",++mLastRow);
                }
                celldata.add(eleCell);
            }
        }
        return sheetData;
    }
}
