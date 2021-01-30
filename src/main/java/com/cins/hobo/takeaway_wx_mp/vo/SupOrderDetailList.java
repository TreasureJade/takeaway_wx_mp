package com.cins.hobo.takeaway_wx_mp.vo;

import com.cins.hobo.takeaway_wx_mp.dto.SupOrderDetailListDTO;
import lombok.Data;

import java.util.List;

/**
 * @author : hobo
 * @className : SupOrderDetailList
 * @date: 2021/1/30
 * @description: TODO
 */
@Data
public class SupOrderDetailList {

   private String orderId;

   private List<SupOrderDetailListDTO> dtoList;

}
