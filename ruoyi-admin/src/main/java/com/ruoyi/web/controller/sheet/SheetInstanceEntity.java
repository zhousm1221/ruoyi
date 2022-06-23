package com.ruoyi.web.controller.sheet;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @作者: Roker
 * @时间: 2022年6月21日00:15:25
 * @Copyright: Don`t be the same,be better!
 * @Description:
 */

@Data
@NoArgsConstructor
public class SheetInstanceEntity {
    private String sheetInstId;
    private String sheetModelId;
    private Object sheetContent;
    private String createUser;
    private List<LimitInfo> limitInfo;

    @Data
    @NoArgsConstructor
    //目前只考虑背对背填报，限制信息只涉及填报部门/人限制区域的权限
    class LimitInfo {


        private String user;
        private String sheetRow;
        //All-完全权限 AR-完全可读 R-只读权限 W-可写权限 L-不可见
        private String operation;//暂时可不用
    }

}

