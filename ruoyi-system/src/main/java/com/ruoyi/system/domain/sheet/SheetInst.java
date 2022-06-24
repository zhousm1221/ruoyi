package com.ruoyi.system.domain.sheet;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报实例对象 sheet_inst
 * 
 * @author ruoyi
 * @date 2022-06-24
 */
@Data
public class SheetInst extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 作业id */
    @Excel(name = "作业id")
    private String instId;

    /** 主题 */
    @Excel(name = "主题")
    private String topic;

    /** 关联模板id */
    @Excel(name = "关联模板id")
    private String modelId;

    /** 内容 */
    @Excel(name = "内容")
    private Object content;

    /** 内容 */
    @Excel(name = "内容")
    private String jsonContent;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

}
