package com.ruoyi.system.domain.sheet;

import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务中心对象 sys_task_center
 * 
 * @author ruoyi
 * @date 2022-06-22
 */
@Data
public class SysTaskInstance extends BaseEntity
{
    private static final long serialVersionUID = 1L;



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

    /**********************************************************************************/

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务绑定(作业实例id) */
    @Excel(name = "任务绑定")
    private String taskBind;

    /** 执行人 */
    @Excel(name = "执行人")
    private String executor;

    /** 发起人 */
    @Excel(name = "发起人")
    private String originator;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private String type;

    /** 任务状态（0-待办，1-已办） */
    @Excel(name = "任务状态", readConverterExp = "0=-待办，1-已办")
    private Integer status;

    /** 完成时间 */
    @Excel(name = "完成时间")
    private String doneTime;

}
