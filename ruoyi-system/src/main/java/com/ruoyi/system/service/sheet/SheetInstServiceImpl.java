package com.ruoyi.system.service.sheet;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.domain.sheet.SheetInst;
import com.ruoyi.system.mapper.sheet.SheetInstMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 报实例Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-24
 */
@Service
public class SheetInstServiceImpl implements ISheetInstService 
{
    @Autowired
    private SheetInstMapper sheetInstMapper;

    /**
     * 查询报实例
     * 
     * @param id 报实例主键
     * @return 报实例
     */
    @Override
    public SheetInst selectSheetInstById(Long id)
    {
        return sheetInstMapper.selectSheetInstById(id);
    }

    /**
     * 查询报实例列表
     * 
     * @param sheetInst 报实例
     * @return 报实例
     */
    @Override
    public List<SheetInst> selectSheetInstList(SheetInst sheetInst)
    {
        return sheetInstMapper.selectSheetInstList(sheetInst);
    }

    /**
     * 新增报实例
     * 
     * @param sheetInst 报实例
     * @return 结果
     */
    @Override
    public int insertSheetInst(SheetInst sheetInst)
    {
        sheetInst.setCreateTime(DateUtils.getNowDate());
        sheetInst.setInstId(UUID.fastUUID().toString());
        sheetInst.setJsonContent(JSON.toJSONString(sheetInst.getContent()));
        return sheetInstMapper.insertSheetInst(sheetInst);
    }

    /**
     * 修改报实例
     * 
     * @param sheetInst 报实例
     * @return 结果
     */
    @Override
    public int updateSheetInst(SheetInst sheetInst)
    {
        return sheetInstMapper.updateSheetInst(sheetInst);
    }

    /**
     * 批量删除报实例
     * 
     * @param ids 需要删除的报实例主键
     * @return 结果
     */
    @Override
    public int deleteSheetInstByIds(Long[] ids)
    {
        return sheetInstMapper.deleteSheetInstByIds(ids);
    }

    /**
     * 删除报实例信息
     * 
     * @param id 报实例主键
     * @return 结果
     */
    @Override
    public int deleteSheetInstById(Long id)
    {
        return sheetInstMapper.deleteSheetInstById(id);
    }
}
