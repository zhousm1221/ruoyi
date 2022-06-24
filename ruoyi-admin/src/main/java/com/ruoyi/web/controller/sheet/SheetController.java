package com.ruoyi.web.controller.sheet;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.sheet.SheetModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ruoyi
 */
@Api("台账报表管理")
@RestController
@RequestMapping("/sheet")
public class SheetController extends BaseController {

    @ApiOperation("获取模板列表")
    @GetMapping("/list")
    public R<List<SheetModel>> userList()
    {
        //TODO 业务层，持久层
        List<SheetModel> sheetModelList = null;
        return R.ok(sheetModelList);
    }
}


