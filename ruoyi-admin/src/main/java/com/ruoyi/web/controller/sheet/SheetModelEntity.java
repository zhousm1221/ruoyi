package com.ruoyi.web.controller.sheet;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者: Roker
 * @时间: 2022年6月21日00:15:25
 * @Copyright: Don`t be the same,be better!
 * @Description:
 */

@Data
@NoArgsConstructor
public class SheetModelEntity {
    private String sheetModelId;
    private String sheetModelName;
    private Object sheetContent;//
    private String createUser;
    private String createTime;

}
