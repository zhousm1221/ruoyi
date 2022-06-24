package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysTaskCenter;

/**
 * 任务中心Service接口
 * 
 * @author ruoyi
 * @date 2022-06-22
 */
public interface ISysTaskCenterService 
{
    /**
     * 查询任务中心
     * 
     * @param id 任务中心主键
     * @return 任务中心
     */
    public SysTaskCenter selectSysTaskCenterById(Long id);

    /**
     * 查询任务中心列表
     * 
     * @param sysTaskCenter 任务中心
     * @return 任务中心集合
     */
    public List<SysTaskCenter> selectSysTaskCenterList(SysTaskCenter sysTaskCenter);

    /**
     * 新增任务中心
     * 
     * @param sysTaskCenter 任务中心
     * @return 结果
     */
    public int insertSysTaskCenter(SysTaskCenter sysTaskCenter);

    /**
     * 修改任务中心
     * 
     * @param sysTaskCenter 任务中心
     * @return 结果
     */
    public int updateSysTaskCenter(SysTaskCenter sysTaskCenter);

    /**
     * 批量删除任务中心
     * 
     * @param ids 需要删除的任务中心主键集合
     * @return 结果
     */
    public int deleteSysTaskCenterByIds(Long[] ids);

    /**
     * 删除任务中心信息
     * 
     * @param id 任务中心主键
     * @return 结果
     */
    public int deleteSysTaskCenterById(Long id);
}
