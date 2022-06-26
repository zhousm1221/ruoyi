package com.ruoyi.system.service.impl;

import java.util.List;

import com.google.gson.Gson;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.util.SheetFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysTaskCenterMapper;
import com.ruoyi.system.domain.SysTaskCenter;
import com.ruoyi.system.service.ISysTaskCenterService;

/**
 * 任务中心Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-22
 */
@Service
public class SysTaskCenterServiceImpl implements ISysTaskCenterService 
{
    @Autowired
    private SysTaskCenterMapper sysTaskCenterMapper;

    /**
     * 查询任务中心
     * 
     * @param id 任务中心主键
     * @param type
     * @return 任务中心
     */
    @Override
    public SysTaskCenter selectSysTaskCenterById(Long id, Integer type)
    {
        SysTaskCenter taskCenter = sysTaskCenterMapper.selectSysTaskCenterById(id,type);
        String contentStr = (String) taskCenter.getContent();
        Gson gson = new Gson();
        Object o = gson.fromJson(contentStr, Object.class);
        //待办，则直接获取模板内容返回
        if (type == 0){
            taskCenter.setContent(o);
        }
        //已办，则进行表头内容合并后返回
        if (type == 1){
            SheetFormat.mergeSheetContent((String)taskCenter.getModelContent(),contentStr,false);
            taskCenter.setContent(o);
        }
        return taskCenter;
    }

    /**
     * 查询任务中心列表
     * 
     * @param sysTaskCenter 任务中心
     * @return 任务中心
     */
    @Override
    public List<SysTaskCenter> selectSysTaskCenterList(SysTaskCenter sysTaskCenter)
    {
        return sysTaskCenterMapper.selectSysTaskCenterList(sysTaskCenter);
    }

    /**
     * 新增任务中心
     * 
     * @param sysTaskCenter 任务中心
     * @return 结果
     */
    @Override
    public int insertSysTaskCenter(SysTaskCenter sysTaskCenter)
    {
        sysTaskCenter.setCreateTime(DateUtils.getNowDate());
        return sysTaskCenterMapper.insertSysTaskCenter(sysTaskCenter);
    }

    /**
     * 修改任务中心
     * 
     * @param sysTaskCenter 任务中心
     * @return 结果
     */
    @Override
    public int updateSysTaskCenter(SysTaskCenter sysTaskCenter)
    {
        return sysTaskCenterMapper.updateSysTaskCenter(sysTaskCenter);
    }

    /**
     * 批量删除任务中心
     * 
     * @param ids 需要删除的任务中心主键
     * @return 结果
     */
    @Override
    public int deleteSysTaskCenterByIds(Long[] ids)
    {
        return sysTaskCenterMapper.deleteSysTaskCenterByIds(ids);
    }

    /**
     * 删除任务中心信息
     * 
     * @param id 任务中心主键
     * @return 结果
     */
    @Override
    public int deleteSysTaskCenterById(Long id)
    {
        return sysTaskCenterMapper.deleteSysTaskCenterById(id);
    }
}
