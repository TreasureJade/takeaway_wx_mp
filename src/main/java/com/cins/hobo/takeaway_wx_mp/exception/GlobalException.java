package com.cins.hobo.takeaway_wx_mp.exception;

import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import lombok.Data;
import lombok.Getter;

/**
 * @author : hobo
 * @className : GlobalException
 * @date: 2021/1/12
 * @description: TODO
 */
@Data
public class GlobalException extends RuntimeException{

    private ResultEnum resultEnum;

    public GlobalException(ResultEnum resultEnum){
        super();
        this.resultEnum = resultEnum;
    }

}
