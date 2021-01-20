package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import lombok.Data;

/**
 * supplier_user
 * @author 
 */
@Data
public class SupplierUser implements Serializable {
    private Integer id;

    private String username;

    private String password;

    /**
     * 店名
     */
    private String tradeName;

    /**
     * 备用联系方式
     */
    private String remark;

    private Integer role;

    private static final long serialVersionUID = 1L;
}