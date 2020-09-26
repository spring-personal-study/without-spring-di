package com.example.demo.order;

import com.example.demo.order.Order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
