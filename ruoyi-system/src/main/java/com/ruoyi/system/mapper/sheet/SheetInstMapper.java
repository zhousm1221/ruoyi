package com.ruoyi.system.mapper.sheet;

import com.ruoyi.system.domain.sheet.SheetInst;

import java.util.List;


/**
 * 报实例Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-24
 */
public interface SheetInstMapper 
{
    /**
     * 查询报实例
     * 
     * @param id 报实例主键
     * @return 报实例
     */
    public SheetInst selectSheetInstById(Long id);

    /**
     * 查询报实例列表
     * 
     * @param sheetInst 报实例
     * @return 报实例集合
     */
    public List<SheetInst> selectSheetInstList(SheetInst sheetInst);

    /**
     * 新增报实例
     * 
     * @param sheetInst 报实例
     * @return 结果
     */
    public int insertSheetInst(SheetInst sheetInst);

    /**
     * 修改报实例
     * 
     * @param sheetInst 报实例
     * @return 结果
     */
    public int updateSheetInst(SheetInst sheetInst);

    /**
     * 删除报实例
     * 
     * @param id 报实例主键
     * @return 结果
     */
    public int deleteSheetInstById(Long id);

    /**
     * 批量删除报实例
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSheetInstByIds(Long[] ids);
}
