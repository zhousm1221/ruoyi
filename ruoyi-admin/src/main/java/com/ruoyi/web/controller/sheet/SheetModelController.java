package com.ruoyi.web.controller.sheet;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.sheet.SheetModel;
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
 * 报模板Controller
 * 
 * @author ruoyi
 * @date 2022-06-23
 */
@RestController
@RequestMapping("/system/model")
public class SheetModelController extends BaseController
{
    @Autowired
    private ISheetModelService sheetModelService;

    /**
     * 查询报模板列表
     */
    @PreAuthorize("@ss.hasPermi('system:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(SheetModel sheetModel)
    {
        startPage();
        List<SheetModel> list = sheetModelService.selectSheetModelList(sheetModel);
        return getDataTable(list);
    }

    /**
     * 导出报模板列表
     */
    @PreAuthorize("@ss.hasPermi('system:model:export')")
    @Log(title = "报模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SheetModel sheetModel)
    {
        List<SheetModel> list = sheetModelService.selectSheetModelList(sheetModel);
        ExcelUtil<SheetModel> util = new ExcelUtil<SheetModel>(SheetModel.class);
        util.exportExcel(response, list, "报模板数据");
    }

    /**
     * 获取报模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sheetModelService.selectSheetModelById(id));
    }

    /**
     * 新增报模板
     */
    @PreAuthorize("@ss.hasPermi('system:model:add')")
    @Log(title = "报模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SheetModel sheetModel)
    {
        return toAjax(sheetModelService.insertSheetModel(sheetModel));
    }

    /**
     * 修改报模板
     */
    @PreAuthorize("@ss.hasPermi('system:model:edit')")
    @Log(title = "报模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SheetModel sheetModel)
    {
        return toAjax(sheetModelService.updateSheetModel(sheetModel));
    }

    /**
     * 删除报模板
     */
    @PreAuthorize("@ss.hasPermi('system:model:remove')")
    @Log(title = "报模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sheetModelService.deleteSheetModelByIds(ids));
    }
}
