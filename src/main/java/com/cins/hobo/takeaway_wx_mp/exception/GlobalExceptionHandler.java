package com.cins.hobo.takeaway_wx_mp.exception;

import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.List;

/**
 * @author : hobo
 * @className : GlobalExceptionHandler
 * @date: 2021/1/12
 * @description: TODO
 */
@Slf4j
public class GlobalExceptionHandler {
    public ResultVO exceptionHandler(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return ResultVO.error(globalException.getResultEnum());
        }
        if (e instanceof BindException) {
            org.springframework.validation.BindException bindException = (org.springframework.validation.BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            String msg = null;
            for (ObjectError error : errors) {
                if (msg == null) {
                    msg = error.getDefaultMessage();
                } else {
                    msg += "," + error.getDefaultMessage();
                }
            }
            log.error(msg);
            ResultVO<String> resultVO = ResultVO.error(ResultEnum.BIND_ERROR);
            resultVO.setMsg(String.format(resultVO.getMsg(), msg));
            return resultVO;
        }
        if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
            log.error("请求方式错误,不支持:{}", exception.getMethod());
            ResultVO<String> resultVO = ResultVO.error(ResultEnum.REQUEST_METHOD_ERROR);
            resultVO.setMsg(String.format(resultVO.getMsg(), e.getMessage()));
            return resultVO;
        } else {
            log.error(e.getMessage());
            e.printStackTrace();
            String requestURI = request.getRequestURI();
            log.info("请求异常的接口:{}", requestURI);
            ResultVO<String> error = ResultVO.error(ResultEnum.SERVER_ERROR);
            error.setMsg(String.format(error.getMsg(), e.getMessage()));
            return error;
        }
    }
}
