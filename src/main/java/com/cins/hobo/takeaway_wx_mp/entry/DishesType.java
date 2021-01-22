package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import lombok.Data;

/**
 * dishes_type
 * @author 
 */
@Data
public class DishesType implements Serializable {
    private Integer id;

    private String typeName;

    private static final long serialVersionUID = 1L;
}