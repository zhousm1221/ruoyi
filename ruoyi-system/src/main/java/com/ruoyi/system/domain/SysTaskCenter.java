package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务中心对象 sys_task_center
 * 
 * @author ruoyi
 * @date 2022-06-22
 */
public class SysTaskCenter extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务绑定 */
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setTaskBind(String taskBind) 
    {
        this.taskBind = taskBind;
    }

    public String getTaskBind() 
    {
        return taskBind;
    }
    public void setExecutor(String executor) 
    {
        this.executor = executor;
    }

    public String getExecutor() 
    {
        return executor;
    }
    public void setOriginator(String originator) 
    {
        this.originator = originator;
    }

    public String getOriginator() 
    {
        return originator;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setDoneTime(String doneTime) 
    {
        this.doneTime = doneTime;
    }

    public String getDoneTime() 
    {
        return doneTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("taskBind", getTaskBind())
            .append("executor", getExecutor())
            .append("originator", getOriginator())
            .append("type", getType())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("doneTime", getDoneTime())
            .toString();
    }
}
