package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrderToDB(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartnerToDB(partnerId);
    }

    public void addOrderToPartner(String orderId, String partnerId) {
        orderRepository.assignOrderToPartner(orderId, partnerId);
    }

    public Order getOrder( String orderId) {
        return orderRepository.getOrderFromDB(orderId);
    }

    public DeliveryPartner getPartner(String partnerId) {
        return orderRepository.getPartnerFromDB(partnerId);
    }

    public int getOrderCount(String partnerId) {
        return orderRepository.getNumberOfOrderByPartner(partnerId);
    }

    public List<String> getAllOrdersByPartner(String partnerId) {
        return orderRepository.getAllOrdersByPartner(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public int getCountUnassignedOrders() {
        return orderRepository.getCountOfUnAssignedOrder();
    }




    public String lastDeliveryTimeByPartner(String partnerId) {
        return orderRepository.getLastDeliveredTimeByPartner(partnerId);
    }

    public void deletePartnerAndUnassignedTheirOrder(String partnerId) {
        orderRepository.deletePartnerAndUnassignedTheirOrders(partnerId);
    }

    public void deleteOrderAndUnassignedItsPartener(String orderId) {
        orderRepository.deleteOrderAndUnassignedTheirPartner(orderId);
    }
}
