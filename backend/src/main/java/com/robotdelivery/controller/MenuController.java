/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.controller;

import com.robotdelivery.model.MenuItem;
import com.robotdelivery.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/items")
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody Map<String, Object> request) {
        MenuItem menuItem = menuService.addMenuItem(request);
        return ResponseEntity.ok(menuItem);
    }

    @GetMapping("/items")
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> items = menuService.getAllMenuItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems() {
        List<MenuItem> items = menuService.getAvailableMenuItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/category/{category}")
    public ResponseEntity<List<MenuItem>> getItemsByCategory(@PathVariable String category) {
        List<MenuItem> items = menuService.getMenuItemsByCategory(category);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<MenuItem> updateMenuItem(
            @PathVariable Long itemId,
            @RequestBody Map<String, Object> updates) {
        MenuItem menuItem = menuService.updateMenuItem(itemId, updates);
        return ResponseEntity.ok(menuItem);
    }
}