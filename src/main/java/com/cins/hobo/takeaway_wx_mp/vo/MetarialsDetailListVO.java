package com.cins.hobo.takeaway_wx_mp.vo;

import com.cins.hobo.takeaway_wx_mp.entry.MaterialsDetail;
import lombok.Data;

import java.util.List;

/**
 * @author : hobo
 * @className : MetarialsDetailListVO
 * @date: 2021/1/23
 * @description: TODO
 */
@Data
public class MetarialsDetailListVO {

    private Integer materialTypeId;

    private String metarialsTypeName;

    private Integer supUserId;

    private String supUsername;

    private String tradeName;

    private List<MaterialsDetail> detailList;

}
