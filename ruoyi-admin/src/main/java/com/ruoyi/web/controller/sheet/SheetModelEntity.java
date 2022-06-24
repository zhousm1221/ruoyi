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
    //模板ID
    private String sheetModelId;
    //模板名称
    private String sheetModelName;
    //模板Sheet内容
    private SheetOption sheetContent;
    //创建人
    private String createUser;
    //创建时间
    private String createTime;

}
