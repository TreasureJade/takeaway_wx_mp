package com.cins.hobo.takeaway_wx_mp.vo;

import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import lombok.Data;

/**
 * @author : hobo
 * @className : ResultVO
 * @date: 2021/1/12
 * @description: RESTFUL 格式类
 */
@Data
public class ResultVO<T> {

  private Integer code;
  private String msg;
  private T data;

    /**
     * 无参成功结果
     */
  public static ResultVO success(){
      return success(null);
  }

    /**
     * 有参成功结果
     */
  public static ResultVO success(Object obj){
      ResultVO vo = new ResultVO();
      vo.setCode(0);
      vo.setMsg("成功");
      vo.setData(obj);
      return vo;
  }

    /**
     * 失败结果
     */
  public static ResultVO error(ResultEnum resuletnum){
      ResultVO vo = new ResultVO();
      vo.setCode(resuletnum.getCode());
      vo.setMsg(resuletnum.getMsg());
      return vo;
  }

}
