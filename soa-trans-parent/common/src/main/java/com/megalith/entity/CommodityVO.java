package com.megalith.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-22
 * @Time: 15:36
 */
@Data
public class CommodityVO implements Serializable {
    private String id;

    private String name;

    private BigDecimal price;
}
