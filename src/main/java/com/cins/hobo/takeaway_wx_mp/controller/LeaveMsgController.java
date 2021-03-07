package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.service.LeaveMsgService;
import com.cins.hobo.takeaway_wx_mp.service.impl.InsertLeaveMsgForm;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hobo
 * @className : LeaveMsgController
 * @date: 2021/3/7
 * @description: TODO
 */
@RestController
@CrossOrigin
@RequestMapping("/leave_msg")
@Api(tags = "留言建议接口")
public class LeaveMsgController {


    @Autowired
    private LeaveMsgService leaveMsgService;

    @ApiOperation("添加留言")
    @PostMapping
    public ResultVO insertLeaveMsg(InsertLeaveMsgForm msgForm){
        return leaveMsgService.insertLeaveMsg(msgForm);
    }

    @ApiOperation("删除留言")
    @DeleteMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "留言id",required = true,dataTypeClass = Integer.class)})
    public ResultVO deleteLeaveMsg(Integer id){
        return leaveMsgService.deleteLeaveMsg(id);
    }

}
