/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.service;

import com.robotdelivery.factory.RestaurantFactory;
import com.robotdelivery.model.MenuItem;
import com.robotdelivery.observer.EventPublisher;
import com.robotdelivery.repositorys.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantFactory restaurantFactory;
    private final EventPublisher eventPublisher;

    public MenuService(MenuItemRepository menuItemRepository,
                       RestaurantFactory restaurantFactory,
                       EventPublisher eventPublisher) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantFactory = restaurantFactory;
        this.eventPublisher = eventPublisher;
    }


    public MenuItem addMenuItem(Map<String, Object> request) {
        MenuItem menuItem = restaurantFactory.createMenuItem(request);
        menuItem = menuItemRepository.save(menuItem);

        eventPublisher.notifyMenuItemAdded(menuItem.getName());

        return menuItem;
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getAvailableMenuItems() {
        return menuItemRepository.findByAvailable(true);
    }


    public List<MenuItem> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category);
    }

    public MenuItem updateMenuItem(Long id, Map<String, Object> updates) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (updates.containsKey("name")) {
            menuItem.setName((String) updates.get("name"));
        }
        if (updates.containsKey("description")) {
            menuItem.setDescription((String) updates.get("description"));
        }
        if (updates.containsKey("available")) {
            menuItem.setAvailable((Boolean) updates.get("available"));
        }

        return menuItemRepository.save(menuItem);
    }
}