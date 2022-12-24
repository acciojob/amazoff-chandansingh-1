package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    Map<String, Order> orders=new HashMap<>();
    Map<String, DeliveryPartner> partners = new HashMap<>();
    Map<String, List<String>> deliveryOrder = new HashMap<>();
    Map<String,String> orderPartner = new HashMap<>();

    public void addOrderToDB(Order order) {
        orders.put(order.getId(), order);
    }

    public void addPartnerToDB(String partnerId) {
        partners.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void assignOrderToPartner(String orderId, String partnerId) {
        if(!deliveryOrder.containsKey(partnerId)){
            deliveryOrder.put(partnerId, new ArrayList<>());
        }
        deliveryOrder.get(partnerId).add(orderId);
        orderPartner.put(orderId, partnerId);

        DeliveryPartner partner = partners.get(partnerId);
        int numOfOrders = partner.getNumberOfOrders();
        partner.setNumberOfOrders(numOfOrders+1);
    }

    public Order getOrderFromDB(String orderId) {
        return orders.get(orderId);
    }

    public DeliveryPartner getPartnerFromDB(String partnerId) {
        return partners.get(partnerId);
    }

    public int getNumberOfOrderByPartner(String partnerId) {
        DeliveryPartner partner = partners.get(partnerId);
        return partner.getNumberOfOrders();
    }

    public List<String> getAllOrdersByPartner(String partnerId) {
        return deliveryOrder.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();
        for(String orderId : orders.keySet()) {
            list.add(orderId);
        }
        return list;
    }

    public int getCountOfUnAssignedOrder() {
        return orders.size()-orderPartner.size();
    }

    public int unDeliveredOrderByTime(String time, String partnerId) {

        String[] str = time.split(":");
        int hr = Integer.parseInt(str[0]);
        int min = Integer.parseInt(str[1]);
        int totalTime = (60*hr)+min;

        int count = 0;

        for(String orderId : deliveryOrder.get(partnerId)) {
            Order order = orders.get(orderId);
            if(totalTime < order.getDeliveryTime())
                count++;
        }

        return count;
    }

    public String getLastDeliveredTimeByPartner(String partnerId) {
        List<String> list = deliveryOrder.get(partnerId);
        int time = Integer.MIN_VALUE;
        for(String orderId : deliveryOrder.get(partnerId)) {
            Order order = orders.get(orderId);
            time = Math.max(time, order.getDeliveryTime());
        }

        int hr = time/60;
        int min = time%60;

        return (hr + ":" + min);
    }

    public void deletePartnerAndUnassignedTheirOrders(String partnerId) {
        for(String orderId : deliveryOrder.get(partnerId)) {
            orderPartner.remove(orderId);
        }
        deliveryOrder.remove(partnerId);
        partners.remove(partnerId);
    }

    public void deleteOrderAndUnassignedTheirPartner(String orderId) {
        String partnerId = orderPartner.get(orderId);
        for(String getOrder : deliveryOrder.get(partnerId)) {
            if(orderId.equals(getOrder))
                deliveryOrder.get(partnerId).remove(getOrder);
        }
        orders.remove(orderId);
        orderPartner.remove(orderId);

        DeliveryPartner partner = partners.get(partnerId);
        int numOfOrders = partner.getNumberOfOrders();
        partner.setNumberOfOrders(numOfOrders-1);
    }
}
