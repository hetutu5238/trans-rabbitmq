package com.megalith.module.controller;

import com.megalith.entity.CommodityVO;
import com.megalith.module.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-22
 * @Time: 15:33
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IStockService stockService;

    @PostMapping("/buy")
    public String buy(@RequestBody CommodityVO commodityVO){
        stockService.buy(commodityVO);
        return "ok";
    }
}
