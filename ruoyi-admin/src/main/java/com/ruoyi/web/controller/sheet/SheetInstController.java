package com.ruoyi.web.controller.sheet;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.SysTaskCenter;
import com.ruoyi.system.domain.sheet.SheetInst;
import com.ruoyi.system.domain.sheet.SheetModel;
import com.ruoyi.system.domain.sheet.SheetModel;
import com.ruoyi.system.domain.sheet.SysTaskInstance;
import com.ruoyi.system.service.ISysTaskCenterService;
import com.ruoyi.system.service.sheet.ISheetInstService;
import com.ruoyi.system.service.sheet.ISheetModelService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报实例Controller
 * 
 * @author ruoyi
 * @date 2022-06-24
 */
@RestController
@RequestMapping("/system/inst")
public class SheetInstController extends BaseController
{
    @Autowired
    private ISheetInstService sheetInstService;

    @Autowired
    private ISysTaskCenterService sysTaskCenterService;

    @Autowired
    private ISheetModelService sheetModelService;

    /**
     * 查询报实例列表
     */
    @PreAuthorize("@ss.hasPermi('system:inst:list')")
    @GetMapping("/list")
    public TableDataInfo list(SheetInst sheetInst)
    {
        startPage();
        List<SheetInst> list = sheetInstService.selectSheetInstList(sheetInst);
        return getDataTable(list);
    }

    /**
     * 导出报实例列表
     */
    @PreAuthorize("@ss.hasPermi('system:inst:export')")
    @Log(title = "报实例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SheetInst sheetInst)
    {
        List<SheetInst> list = sheetInstService.selectSheetInstList(sheetInst);
        ExcelUtil<SheetInst> util = new ExcelUtil<SheetInst>(SheetInst.class);
        util.exportExcel(response, list, "报实例数据");
    }

    /**
     * 获取报实例详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:inst:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sheetInstService.selectSheetInstById(id));
    }

    /**
     * 新增报实例
     */
    @PreAuthorize("@ss.hasPermi('system:inst:add')")
    @Log(title = "报实例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SheetInst sheetInst)
    {
        return toAjax(sheetInstService.insertSheetInst(sheetInst));
    }

    /**
     * 修改报实例
     */
    @PreAuthorize("@ss.hasPermi('system:inst:edit')")
    @Log(title = "报实例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SheetInst sheetInst)
    {
        return toAjax(sheetInstService.updateSheetInst(sheetInst));
    }

    /**
     * 删除报实例
     */
    @PreAuthorize("@ss.hasPermi('system:inst:remove')")
    @Log(title = "报实例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sheetInstService.deleteSheetInstByIds(ids));
    }

    /**
     * 作业实例下发
     */
    @PreAuthorize("@ss.hasPermi('system:inst:issued')")
    @Log(title = "作业实例下发", businessType = BusinessType.INSERT)
    @PostMapping("/issued")
    public AjaxResult issued (@RequestBody SysTaskInstance sysTaskInstance){
        /**作业实例保存**/
        SheetInst sheetInst = new SheetInst();
        BeanUtils.copyBeanProp(sheetInst,sysTaskInstance);
        SheetModel sheetModel = new SheetModel();
        sheetModel.setModelId(sheetInst.getModelId());
        List<SheetModel> sheetModelList = sheetModelService.selectSheetModelList(sheetModel);
        if(sheetModelList.size()==0){
            return AjaxResult.error();
        }
        sheetInst.setContent(sheetModelList.get(0).getContent());
        int flag = sheetInstService.insertSheetInst(sheetInst);
        if(flag!=1){
            return AjaxResult.error();
        }

        /**任务下发**/

        SysTaskCenter sysTaskCenter = new SysTaskCenter();
        BeanUtils.copyBeanProp(sysTaskCenter,sysTaskInstance);
        sysTaskCenter.setTaskBind(sheetInst.getInstId());
        sysTaskCenter.setOriginator(sheetInst.getCreateUser());
        sysTaskCenter.setTaskName(sysTaskInstance.getTopic());
        String[] split = sysTaskCenter.getExecutor().split(",");
        for (String executor :split) {
            sysTaskCenter.setExecutor(executor);
            int i = sysTaskCenterService.insertSysTaskCenter(sysTaskCenter);
            if(i!=1){
               return AjaxResult.error();
            }
            sysTaskCenter.setExecutor(null);
        }
        return AjaxResult.success();
    }
}
