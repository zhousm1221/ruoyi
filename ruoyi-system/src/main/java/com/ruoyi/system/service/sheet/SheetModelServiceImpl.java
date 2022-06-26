package com.ruoyi.system.service.sheet;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.util.UUIDUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.domain.sheet.SheetModel;
import com.ruoyi.system.domain.sheet.SheetModel;
import com.ruoyi.system.mapper.sheet.SheetModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 报模板Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-23
 */
@Service
public class SheetModelServiceImpl implements ISheetModelService 
{
    @Autowired
    private SheetModelMapper sheetModelMapper;

    /**
     * 查询报模板
     * 
     * @param id 报模板主键
     * @return 报模板
     */
    @Override
    public SheetModel selectSheetModelById(String modelId)
    {
        return sheetModelMapper.selectSheetModelById(modelId);
    }

    /**
     * 查询报模板列表
     * 
     * @param sheetModel 报模板
     * @return 报模板
     */
    @Override
    public List<SheetModel> selectSheetModelList(SheetModel sheetModel)
    {
        return sheetModelMapper.selectSheetModelList(sheetModel);
    }


    /**
     * 新增报模板
     * 
     * @param sheetModel 报模板
     * @return 结果
     */
    @Override
    public int insertSheetModel(SheetModel sheetModel)
    {
        sheetModel.setModelId(UUID.fastUUID().toString());
        sheetModel.setJsonContent(JSON.toJSONString(sheetModel.getContent()));
        sheetModel.setCreateTime(DateUtils.getNowDate());
        return sheetModelMapper.insertSheetModel(sheetModel);
    }

    /**
     * 修改报模板
     * 
     * @param sheetModel 报模板
     * @return 结果
     */
    @Override
    public int updateSheetModel(SheetModel sheetModel)
    {
        return sheetModelMapper.updateSheetModel(sheetModel);
    }

    /**
     * 批量删除报模板
     * 
     * @param ids 需要删除的报模板主键
     * @return 结果
     */
    @Override
    public int deleteSheetModelByIds(Long[] ids)
    {
        return sheetModelMapper.deleteSheetModelByIds(ids);
    }

    /**
     * 删除报模板信息
     * 
     * @param id 报模板主键
     * @return 结果
     */
    @Override
    public int deleteSheetModelById(Long id)
    {
        return sheetModelMapper.deleteSheetModelById(id);
    }
}
