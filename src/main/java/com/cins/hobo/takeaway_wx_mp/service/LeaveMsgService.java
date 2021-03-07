package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.service.impl.InsertLeaveMsgForm;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

/**
 * @author : hobo
 * @interfaceName : LeaveMsgService
 * @date: 2021/3/6
 * @description: TODO
 */
public interface LeaveMsgService {

    ResultVO insertLeaveMsg(InsertLeaveMsgForm msgForm);

    ResultVO adminSelectAllLeaveMsg();

    ResultVO consumerSelectAllLeaveMsg(String openId);

    ResultVO deleteLeaveMsg(Integer id);

}
