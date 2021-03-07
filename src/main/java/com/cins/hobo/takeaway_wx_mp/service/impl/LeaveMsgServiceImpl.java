package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.LeaveMsgDao;
import com.cins.hobo.takeaway_wx_mp.entry.LeaveMsg;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.service.LeaveMsgService;
import com.cins.hobo.takeaway_wx_mp.util.TimeUtils;
import com.cins.hobo.takeaway_wx_mp.vo.LeaveMsgListVO;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hobo
 * @className : LeaveMsgServiceImpl
 * @date: 2021/3/6
 * @description: TODO
 */
@Service
public class LeaveMsgServiceImpl implements LeaveMsgService {

    @Autowired
    private LeaveMsgDao msgDao;

    @Override
    public ResultVO insertLeaveMsg(InsertLeaveMsgForm msgForm) {
        LeaveMsg msg = new LeaveMsg();
        BeanUtils.copyProperties(msgForm, msg);
        msg.setCreateTime(TimeUtils.getNowTime());
        if (msgDao.insertSelective(msg) == 1) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO adminSelectAllLeaveMsg() {
        List<LeaveMsgListVO> res = msgDao.selectAllLeaveMsg();
        for (LeaveMsgListVO vo : res) {
            List<LeaveMsg> replyList = msgDao.selectAllLeaveMsgByPid(vo.getId());
            vo.setReplyList(replyList);
        }
        return ResultVO.success(res);
    }

    @Override
    public ResultVO consumerSelectAllLeaveMsg(String openId) {
        List<LeaveMsgListVO> res = msgDao.selectAllLeaveMsgByOpenId(openId);
        for (LeaveMsgListVO vo : res) {
            List<LeaveMsg> replyList = msgDao.selectAllLeaveMsgByPid(vo.getId());
            vo.setReplyList(replyList);
        }
        return ResultVO.success(res);
    }

    @Override
    public ResultVO deleteLeaveMsg(Integer id) {
        if (msgDao.deleteByPrimaryKey(id)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }


}
