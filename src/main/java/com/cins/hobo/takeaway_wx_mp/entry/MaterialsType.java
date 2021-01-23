package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import lombok.Data;

/**
 * metarials_type
 * @author 
 */
@Data
public class MaterialsType implements Serializable {
    private Integer id;

    private String metarialsTypeName;

    private Integer supUserId;

    private static final long serialVersionUID = 1L;
}