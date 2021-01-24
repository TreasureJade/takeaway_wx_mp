package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.form.material_form.CreateMaterialsOrderForm;
import com.cins.hobo.takeaway_wx_mp.form.material_form.UpdateMaterialOrderPriceForm;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

import java.util.List;

/**
 * @author : hobo
 * @interfaceName : SupMaterialsOrderService
 * @date: 2021/1/23
 * @description: 供货商订单相关接口
 */
public interface SupMaterialsOrderService {


    /**
     * 店员创建新的供货订单并通知给供货商
     * @param materialsOrders
     * @return
     */
    ResultVO createNewMaterialsOrder(List<CreateMaterialsOrderForm> materialsOrders);

    /**
     * 供货商修改价格并回传给店家
     * @param priceForms
     * @return
     */
    ResultVO supUserUpdateMaterialOrder(List<UpdateMaterialOrderPriceForm> priceForms);

    /**
     *
     * @return
     */
    ResultVO getSupUserAllNewOrder();
}
