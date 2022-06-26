package com.ruoyi.system.service.sheet;

import com.ruoyi.system.domain.sheet.SheetModel;
import com.ruoyi.system.domain.sheet.SheetModel;

import java.util.List;


/**
 * 报模板Service接口
 * 
 * @author ruoyi
 * @date 2022-06-23
 */
public interface ISheetModelService 
{
    /**
     * 查询报模板
     * 
     * @param id 报模板主键
     * @return 报模板
     */
    public SheetModel selectSheetModelById(String id);

    /**
     * 查询报模板列表
     * 
     * @param sheetModel 报模板
     * @return 报模板集合
     */
    public List<SheetModel> selectSheetModelList(SheetModel sheetModel);

    /**
     * 新增报模板
     * 
     * @param sheetModel 报模板
     * @return 结果
     */
    public int insertSheetModel(SheetModel sheetModel);

    /**
     * 修改报模板
     * 
     * @param sheetModel 报模板
     * @return 结果
     */
    public int updateSheetModel(SheetModel sheetModel);

    /**
     * 批量删除报模板
     * 
     * @param ids 需要删除的报模板主键集合
     * @return 结果
     */
    public int deleteSheetModelByIds(Long[] ids);

    /**
     * 删除报模板信息
     * 
     * @param id 报模板主键
     * @return 结果
     */
    public int deleteSheetModelById(Long id);
}
