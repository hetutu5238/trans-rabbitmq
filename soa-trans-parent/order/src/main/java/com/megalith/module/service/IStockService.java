package com.megalith.module.service;

import com.megalith.entity.CommodityVO;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-22
 * @Time: 15:39
 */
public interface IStockService {
    /**
     * 模拟购买商品
     * @param commodityVO
     */
    CommodityVO buy(CommodityVO commodityVO);
}
