package com.megalith.module.service.impl;

import com.megalith.constant.MsgConstant;
import com.megalith.entity.CommodityVO;
import com.megalith.module.service.IStockService;
import com.trans.anno.TransMessage;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-22
 * @Time: 15:39
 */
@Service
public class StockServiceImpl implements IStockService {
    @Override
    @TransMessage(exchange= MsgConstant.MONEY_EXCHANGE,bindingKey = MsgConstant.MONEY_ROUTE_KEY,bussnessId = "MoneyBy")
    public CommodityVO buy(CommodityVO commodityVO) {
        System.out.println("已购买商品");
        return commodityVO;
    }
}
