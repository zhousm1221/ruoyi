package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysTaskCenter;
import com.ruoyi.system.service.ISysTaskCenterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务中心Controller
 * 
 * @author ruoyi
 * @date 2022-06-22
 */
@RestController
@RequestMapping("/system/center")
public class SysTaskCenterController extends BaseController
{
    @Autowired
    private ISysTaskCenterService sysTaskCenterService;

    /**
     * 查询任务中心列表
     */
    @PreAuthorize("@ss.hasPermi('system:center:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTaskCenter sysTaskCenter)
    {
        startPage();
        List<SysTaskCenter> list = sysTaskCenterService.selectSysTaskCenterList(sysTaskCenter);
        return getDataTable(list);
    }

    /**
     * 导出任务中心列表
     */
    @PreAuthorize("@ss.hasPermi('system:center:export')")
    @Log(title = "任务中心", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTaskCenter sysTaskCenter)
    {
        List<SysTaskCenter> list = sysTaskCenterService.selectSysTaskCenterList(sysTaskCenter);
        ExcelUtil<SysTaskCenter> util = new ExcelUtil<SysTaskCenter>(SysTaskCenter.class);
        util.exportExcel(response, list, "任务中心数据");
    }

    /**
     * 获取任务中心详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:center:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysTaskCenterService.selectSysTaskCenterById(id));
    }

    /**
     * 新增任务中心
     */
    @PreAuthorize("@ss.hasPermi('system:center:add')")
    @Log(title = "任务中心", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysTaskCenter sysTaskCenter)
    {
        return toAjax(sysTaskCenterService.insertSysTaskCenter(sysTaskCenter));
    }

    /**
     * 修改任务中心
     */
    @PreAuthorize("@ss.hasPermi('system:center:edit')")
    @Log(title = "任务中心", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysTaskCenter sysTaskCenter)
    {
        return toAjax(sysTaskCenterService.updateSysTaskCenter(sysTaskCenter));
    }

    /**
     * 删除任务中心
     */
    @PreAuthorize("@ss.hasPermi('system:center:remove')")
    @Log(title = "任务中心", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysTaskCenterService.deleteSysTaskCenterByIds(ids));
    }
}
