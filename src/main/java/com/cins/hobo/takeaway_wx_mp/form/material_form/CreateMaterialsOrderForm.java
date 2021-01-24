package com.cins.hobo.takeaway_wx_mp.form.material_form;

import com.cins.hobo.takeaway_wx_mp.dto.CreateMaterialsOrderDTO;
import lombok.Data;

import java.util.List;


/**
 * @author : hobo
 * @className : CreateMaterialsOrderForm
 * @date: 2021/1/23
 * @description: TODO
 */
@Data
public class CreateMaterialsOrderForm {

    private Integer subUserId;

    private List<CreateMaterialsOrderDTO> orderDTOList;

}
