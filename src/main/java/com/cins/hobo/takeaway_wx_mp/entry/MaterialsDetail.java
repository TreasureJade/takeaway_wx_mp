package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import lombok.Data;

/**
 * metarials_detail
 * @author 
 */
@Data
public class MaterialsDetail implements Serializable {
    private Integer id;

    private String metarialsName;

    private Integer metarialsTypeId;

    private static final long serialVersionUID = 1L;
}