package com.example.demo.service;

import com.example.demo.model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private static final String CART_SESSION_KEY = "CART";

    @SuppressWarnings("unchecked")
    private Map<String, CartItem> getCartMap(HttpSession session) {
        Object obj = session.getAttribute(CART_SESSION_KEY);
        if (obj instanceof Map) return (Map<String, CartItem>) obj;
        Map<String, CartItem> map = new LinkedHashMap<>();
        session.setAttribute(CART_SESSION_KEY, map);
        return map;
    }

    public List<CartItem> getItems(HttpSession session) {
        return new ArrayList<>(getCartMap(session).values());
    }

    public void addItem(HttpSession session, CartItem item) {
        Map<String, CartItem> map = getCartMap(session);
        CartItem existing = map.get(item.getKey());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            map.put(item.getKey(), item);
        }
        session.setAttribute(CART_SESSION_KEY, map);
    }

    public void increment(HttpSession session, String key) {
        Map<String, CartItem> map = getCartMap(session);
        CartItem it = map.get(key);
        if (it != null) {
            it.setQuantity(it.getQuantity() + 1);
        }
    }

    public void decrement(HttpSession session, String key) {
        Map<String, CartItem> map = getCartMap(session);
        CartItem it = map.get(key);
        if (it != null) {
            it.setQuantity(it.getQuantity() - 1);
            if (it.getQuantity() <= 0) map.remove(key);
        }
    }

    public void remove(HttpSession session, String key) {
        Map<String, CartItem> map = getCartMap(session);
        map.remove(key);
    }

    public void clear(HttpSession session) {
        Map<String, CartItem> map = getCartMap(session);
        map.clear();
    }

    public long getTotal(HttpSession session) {
        return getCartMap(session).values().stream().mapToLong(CartItem::getSubtotal).sum();
    }
}

