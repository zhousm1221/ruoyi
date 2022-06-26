package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysTaskCenter;
import org.apache.ibatis.annotations.Param;

/**
 * 任务中心Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-22
 */
public interface SysTaskCenterMapper 
{
    /**
     * 查询任务中心
     * 
     * @param id 任务中心主键
     * @return 任务中心
     */
    public SysTaskCenter selectSysTaskCenterById(@Param("id") Long id, @Param("type")Integer type);

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
     * 删除任务中心
     * 
     * @param id 任务中心主键
     * @return 结果
     */
    public int deleteSysTaskCenterById(Long id);

    /**
     * 批量删除任务中心
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTaskCenterByIds(Long[] ids);
}
