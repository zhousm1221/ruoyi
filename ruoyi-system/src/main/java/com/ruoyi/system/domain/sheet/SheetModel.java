package com.ruoyi.system.domain.sheet;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 报模板对象 sheet_model
 * 
 * @author ruoyi
 * @date 2022-06-23
 */

@Data
public class SheetModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模板id */
    @Excel(name = "模板id")
    private String modelId;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String modelName;

    /** 模板正文 */
    @Excel(name = "模板正文")
    private Object content;

    private String jsonContent;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /** 状态（0-失效，1-生效） */
    @Excel(name = "状态", readConverterExp = "0=-失效，1-生效")
    private Integer status;


}
