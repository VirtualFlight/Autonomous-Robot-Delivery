/*
 * Author: Jahiem Allen
 * JUnit tests for RestaurantFactory and EntityFactory
 */

package com.robotdelivery.factory;

import com.robotdelivery.model.*;
import com.robotdelivery.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantFactoryTest {

    @Mock
    private PasswordUtil passwordUtil;

    private RestaurantFactory restaurantFactory;

    @BeforeEach
    void setUp() {
        restaurantFactory = new RestaurantFactory(passwordUtil);
    }

    // ==================== Customer Creation Tests ====================

    @Test
    void testCreateCustomer_WithAllFields_ShouldCreateCustomerSuccessfully() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("email", "test@example.com");
        request.put("password", "plainPassword123");
        request.put("firstName", "John");
        request.put("lastName", "Doe");
        request.put("phoneNumber", "555-1234");
        request.put("address", "123 Main St");
        request.put("city", "Toronto");
        request.put("postalCode", "M5H 2N2");

        when(passwordUtil.hashPassword("plainPassword123")).thenReturn("hashedPassword123");

        // Act
        Customer customer = restaurantFactory.createCustomer(request);

        // Assert
        assertNotNull(customer);
        assertEquals("test@example.com", customer.getEmail());
        assertEquals("hashedPassword123", customer.getPasswordHash());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("555-1234", customer.getPhoneNumber());
        assertEquals("123 Main St", customer.getAddress());
        assertEquals("Toronto", customer.getCity());
        assertEquals("M5H 2N2", customer.getPostalCode());

        verify(passwordUtil, times(1)).hashPassword("plainPassword123");
    }

    @Test
    void testCreateCustomer_WithMinimalFields_ShouldCreateCustomerWithNullOptionalFields() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("email", "minimal@example.com");
        request.put("password", "password");

        when(passwordUtil.hashPassword("password")).thenReturn("hashedPwd");

        // Act
        Customer customer = restaurantFactory.createCustomer(request);

        // Assert
        assertNotNull(customer);
        assertEquals("minimal@example.com", customer.getEmail());
        assertEquals("hashedPwd", customer.getPasswordHash());
        assertNull(customer.getFirstName());
        assertNull(customer.getLastName());
        assertNull(customer.getPhoneNumber());
        assertNull(customer.getAddress());
        assertNull(customer.getCity());
        assertNull(customer.getPostalCode());
    }

    @Test
    void testCreateCustomer_ShouldHashPasswordCorrectly() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("email", "user@test.com");
        request.put("password", "mySecurePassword");

        when(passwordUtil.hashPassword(anyString())).thenReturn("$2a$10$hashedValue");

        // Act
        Customer customer = restaurantFactory.createCustomer(request);

        // Assert
        assertEquals("$2a$10$hashedValue", customer.getPasswordHash());
        verify(passwordUtil).hashPassword("mySecurePassword");
    }

    // ==================== MenuItem Creation Tests ====================

    @Test
    void testCreateMenuItem_WithAllFields_ShouldCreateMenuItemSuccessfully() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("name", "Cheeseburger");
        request.put("description", "Delicious burger with cheese");
        request.put("price", "12.99");
        request.put("category", "Burgers");
        request.put("available", true);
        request.put("imageUrl", "https://example.com/burger.jpg");

        // Act
        MenuItem menuItem = restaurantFactory.createMenuItem(request);

        // Assert
        assertNotNull(menuItem);
        assertEquals("Cheeseburger", menuItem.getName());
        assertEquals("Delicious burger with cheese", menuItem.getDescription());
        assertEquals(new BigDecimal("12.99"), menuItem.getPrice());
        assertEquals("Burgers", menuItem.getCategory());
        assertTrue(menuItem.getAvailable());
        assertEquals("https://example.com/burger.jpg", menuItem.getImageUrl());
    }

    @Test
    void testCreateMenuItem_WithDefaultAvailable_ShouldSetAvailableToTrue() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("name", "Pizza");
        request.put("description", "Cheese pizza");
        request.put("price", "15.50");
        request.put("category", "Italian");
        // available not provided, should default to true

        // Act
        MenuItem menuItem = restaurantFactory.createMenuItem(request);

        // Assert
        assertTrue(menuItem.getAvailable());
    }

    @Test
    void testCreateMenuItem_WithAvailableFalse_ShouldSetAvailableToFalse() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("name", "Seasonal Item");
        request.put("description", "Out of season");
        request.put("price", "20.00");
        request.put("category", "Specials");
        request.put("available", false);

        // Act
        MenuItem menuItem = restaurantFactory.createMenuItem(request);

        // Assert
        assertFalse(menuItem.getAvailable());
    }

    @Test
    void testCreateMenuItem_WithIntegerPrice_ShouldConvertToBigDecimal() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("name", "Soda");
        request.put("description", "Cold drink");
        request.put("price", 3);
        request.put("category", "Beverages");

        // Act
        MenuItem menuItem = restaurantFactory.createMenuItem(request);

        // Assert
        assertEquals(new BigDecimal("3"), menuItem.getPrice());
    }

    @Test
    void testCreateMenuItem_WithDoublePrice_ShouldConvertToBigDecimal() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("name", "Salad");
        request.put("description", "Fresh garden salad");
        request.put("price", 8.75);
        request.put("category", "Salads");

        // Act
        MenuItem menuItem = restaurantFactory.createMenuItem(request);

        // Assert
        assertEquals(new BigDecimal("8.75"), menuItem.getPrice());
    }

    // ==================== Order Creation Tests ====================

    @Test
    void testCreateOrder_ShouldCreateOrderWithCorrectDefaults() {
        // Arrange
        Customer customer = new Customer.CustomerBuilder()
                .email("customer@test.com")
                .passwordHash("hash")
                .build();
        String deliveryAddress = "456 Oak Avenue";

        // Act
        Order order = restaurantFactory.createOrder(customer, deliveryAddress);

        // Assert
        assertNotNull(order);
        assertEquals(customer, order.getCustomer());
        assertEquals("456 Oak Avenue", order.getDeliveryAddress());
        assertEquals(BigDecimal.ZERO, order.getTotalPrice());
        assertEquals(Order.OrderStatus.PENDING, order.getStatus());
        assertNull(order.getDelivery());
    }

    @Test
    void testCreateOrder_WithNullDeliveryAddress_ShouldCreateOrderWithNullAddress() {
        // Arrange
        Customer customer = new Customer.CustomerBuilder()
                .email("user@test.com")
                .passwordHash("hash")
                .build();

        // Act
        Order order = restaurantFactory.createOrder(customer, null);

        // Assert
        assertNotNull(order);
        assertNull(order.getDeliveryAddress());
        assertEquals(customer, order.getCustomer());
    }

    // ==================== OrderItem Creation Tests ====================

    @Test
    void testCreateOrderItem_ShouldCreateOrderItemSuccessfully() {
        // Arrange
        Customer customer = new Customer.CustomerBuilder()
                .email("test@example.com")
                .passwordHash("hash")
                .build();

        Order order = new Order.OrderBuilder()
                .customer(customer)
                .totalPrice(BigDecimal.ZERO)
                .status(Order.OrderStatus.PENDING)
                .build();

        MenuItem menuItem = new MenuItem.MenuItemBuilder()
                .name("Burger")
                .price(new BigDecimal("10.99"))
                .build();

        Integer quantity = 2;

        // Act
        OrderItem orderItem = restaurantFactory.createOrderItem(order, menuItem, quantity);

        // Assert
        assertNotNull(orderItem);
        assertEquals(order, orderItem.getOrder());
        assertEquals(menuItem, orderItem.getMenuItem());
        assertEquals(2, orderItem.getQuantity());
        assertEquals(new BigDecimal("10.99"), orderItem.getPriceAtOrder());
    }

    @Test
    void testCreateOrderItem_ShouldCaptureMenuItemPriceAtOrderTime() {
        // Arrange
        Customer customer = new Customer.CustomerBuilder()
                .email("customer@test.com")
                .passwordHash("hash")
                .build();

        Order order = new Order.OrderBuilder()
                .customer(customer)
                .totalPrice(BigDecimal.ZERO)
                .status(Order.OrderStatus.PENDING)
                .build();

        MenuItem menuItem = new MenuItem.MenuItemBuilder()
                .name("Pizza")
                .price(new BigDecimal("18.50"))
                .build();

        // Act
        OrderItem orderItem = restaurantFactory.createOrderItem(order, menuItem, 1);

        // Modify menu item price after order creation
        menuItem.setPrice(new BigDecimal("20.00"));

        // Assert - OrderItem should retain the original price
        assertEquals(new BigDecimal("18.50"), orderItem.getPriceAtOrder());
        assertEquals(new BigDecimal("20.00"), menuItem.getPrice());
    }

    @Test
    void testCreateOrderItem_WithQuantityOfOne_ShouldSetQuantityCorrectly() {
        // Arrange
        Customer customer = new Customer.CustomerBuilder()
                .email("test@test.com")
                .passwordHash("hash")
                .build();

        Order order = new Order.OrderBuilder()
                .customer(customer)
                .totalPrice(BigDecimal.ZERO)
                .status(Order.OrderStatus.PENDING)
                .build();

        MenuItem menuItem = new MenuItem.MenuItemBuilder()
                .name("Coffee")
                .price(new BigDecimal("4.50"))
                .build();

        // Act
        OrderItem orderItem = restaurantFactory.createOrderItem(order, menuItem, 1);

        // Assert
        assertEquals(1, orderItem.getQuantity());
    }

    @Test
    void testCreateOrderItem_WithLargeQuantity_ShouldHandleCorrectly() {
        // Arrange
        Customer customer = new Customer.CustomerBuilder()
                .email("bulk@test.com")
                .passwordHash("hash")
                .build();

        Order order = new Order.OrderBuilder()
                .customer(customer)
                .totalPrice(BigDecimal.ZERO)
                .status(Order.OrderStatus.PENDING)
                .build();

        MenuItem menuItem = new MenuItem.MenuItemBuilder()
                .name("Water Bottle")
                .price(new BigDecimal("2.00"))
                .build();

        // Act
        OrderItem orderItem = restaurantFactory.createOrderItem(order, menuItem, 100);

        // Assert
        assertEquals(100, orderItem.getQuantity());
        assertEquals(new BigDecimal("2.00"), orderItem.getPriceAtOrder());
    }
}

// ==================== EntityFactory Tests ====================

@ExtendWith(MockitoExtension.class)
class EntityFactoryTest {

    @Mock
    private RestaurantFactory restaurantFactory;

    private EntityFactory entityFactory;
    private Robot mockRobot;

    @BeforeEach
    void setUp() {
        entityFactory = new EntityFactory(restaurantFactory);

        mockRobot = new Robot.RobotBuilder()
                .robotId("R1")
                .name("TestBot")
                .status(Robot.RobotStatus.IDLE)
                .position(5, 10)
                .batteryLevel(95)
                .build();
    }

    // ==================== Robot Creation Tests ====================

    @Test
    void testCreateRobot_ShouldInitializeWithDefaults() {
        // Act
        Robot robot = entityFactory.createRobot("R100", "NewBot");

        // Assert
        assertNotNull(robot);
        assertEquals("R100", robot.getRobotId());
        assertEquals("NewBot", robot.getName());
        assertEquals(Robot.RobotStatus.IDLE, robot.getStatus());
        assertEquals(100, robot.getBatteryLevel());
        assertEquals(0, robot.getCurrentX());
        assertEquals(0, robot.getCurrentY());
    }

    @Test
    void testCreateRobot_WithDifferentIds_ShouldCreateMultipleRobots() {
        // Act
        Robot robot1 = entityFactory.createRobot("R1", "Bot1");
        Robot robot2 = entityFactory.createRobot("R2", "Bot2");

        // Assert
        assertNotEquals(robot1.getRobotId(), robot2.getRobotId());
        assertEquals("R1", robot1.getRobotId());
        assertEquals("R2", robot2.getRobotId());
    }

    // ==================== SensorData Creation Tests ====================

    @Test
    void testCreateSensorData_WithAllFields_ShouldMapCorrectly() {
        // Arrange
        Map<String, Object> sensorRequest = new HashMap<>();
        sensorRequest.put("positionX", 50);
        sensorRequest.put("positionY", 60);
        sensorRequest.put("leftObstacle", true);
        sensorRequest.put("frontObstacle", false);
        sensorRequest.put("rightObstacle", true);
        sensorRequest.put("batteryLevel", 85);

        // Act
        SensorData sensorData = entityFactory.createSensorData(mockRobot, sensorRequest);

        // Assert
        assertNotNull(sensorData);
        assertEquals(mockRobot, sensorData.getRobot());
        assertEquals(50, sensorData.getPositionX());
        assertEquals(60, sensorData.getPositionY());
        assertTrue(sensorData.getLeftObstacle());
        assertFalse(sensorData.getFrontObstacle());
        assertTrue(sensorData.getRightObstacle());
        assertEquals(85, sensorData.getBatteryLevel());
    }

    @Test
    void testCreateSensorData_WithDistanceSensors_ShouldMapCorrectly() {
        // Arrange
        Map<String, Object> sensorRequest = new HashMap<>();
        sensorRequest.put("leftObstacle", false);
        sensorRequest.put("frontObstacle", false);
        sensorRequest.put("rightObstacle", false);
        sensorRequest.put("distanceLeft", 25.5);
        sensorRequest.put("distanceMiddle", 30.0);
        sensorRequest.put("distanceRight", 20.3);

        // Act
        SensorData sensorData = entityFactory.createSensorData(mockRobot, sensorRequest);

        // Assert
        assertNotNull(sensorData);
        assertEquals(25.5, sensorData.getDistanceLeft());
        assertEquals(30.0, sensorData.getDistanceMiddle());
        assertEquals(20.3, sensorData.getDistanceRight());
    }

    @Test
    void testCreateSensorData_WithAccelerometer_ShouldMapCorrectly() {
        // Arrange
        Map<String, Object> sensorRequest = new HashMap<>();
        sensorRequest.put("leftObstacle", false);
        sensorRequest.put("frontObstacle", false);
        sensorRequest.put("rightObstacle", false);
        sensorRequest.put("accelX", 0.5);
        sensorRequest.put("accelY", -0.3);
        sensorRequest.put("accelZ", 9.8);

        // Act
        SensorData sensorData = entityFactory.createSensorData(mockRobot, sensorRequest);

        // Assert
        assertNotNull(sensorData);
        assertEquals(0.5, sensorData.getAccelX());
        assertEquals(-0.3, sensorData.getAccelY());
        assertEquals(9.8, sensorData.getAccelZ());
    }

    @Test
    void testCreateSensorData_WithGyroscope_ShouldMapCorrectly() {
        // Arrange
        Map<String, Object> sensorRequest = new HashMap<>();
        sensorRequest.put("leftObstacle", false);
        sensorRequest.put("frontObstacle", false);
        sensorRequest.put("rightObstacle", false);
        sensorRequest.put("gyroX", 1.2);
        sensorRequest.put("gyroY", -0.8);
        sensorRequest.put("gyroZ", 0.0);

        // Act
        SensorData sensorData = entityFactory.createSensorData(mockRobot, sensorRequest);

        // Assert
        assertNotNull(sensorData);
        assertEquals(1.2, sensorData.getGyroX());
        assertEquals(-0.8, sensorData.getGyroY());
        assertEquals(0.0, sensorData.getGyroZ());
    }

    @Test
    void testCreateSensorData_WithMissingOptionalFields_ShouldHandleGracefully() {
        // Arrange
        Map<String, Object> sensorRequest = new HashMap<>();
        sensorRequest.put("leftObstacle", true);
        sensorRequest.put("frontObstacle", false);
        sensorRequest.put("rightObstacle", false);
        // No position, distances, accelerometer, gyroscope, or battery

        // Act
        SensorData sensorData = entityFactory.createSensorData(mockRobot, sensorRequest);

        // Assert
        assertNotNull(sensorData);
        assertEquals(mockRobot, sensorData.getRobot());
        assertTrue(sensorData.getLeftObstacle());
        assertFalse(sensorData.getFrontObstacle());
        assertFalse(sensorData.getRightObstacle());
    }

    @Test
    void testCreateSensorData_WithNumberTypeConversion_ShouldHandleCorrectly() {
        // Arrange
        Map<String, Object> sensorRequest = new HashMap<>();
        sensorRequest.put("leftObstacle", false);
        sensorRequest.put("frontObstacle", false);
        sensorRequest.put("rightObstacle", false);
        sensorRequest.put("positionX", 100L); // Long instead of Integer
        sensorRequest.put("positionY", 200L);
        sensorRequest.put("batteryLevel", 75.0); // Double instead of Integer

        // Act
        SensorData sensorData = entityFactory.createSensorData(mockRobot, sensorRequest);

        // Assert
        assertNotNull(sensorData);
        assertEquals(100, sensorData.getPositionX());
        assertEquals(200, sensorData.getPositionY());
        assertEquals(75, sensorData.getBatteryLevel());
    }

    // ==================== Delivery Creation Tests ====================

    @Test
    void testCreateDelivery_ShouldInitializeCorrectly() {
        // Arrange
        Integer destX = 100;
        Integer destY = 200;

        // Act
        Delivery delivery = entityFactory.createDelivery(mockRobot, destX, destY);

        // Assert
        assertNotNull(delivery);
        assertEquals(mockRobot, delivery.getRobot());
        assertEquals(destX, delivery.getDestinationX());
        assertEquals(destY, delivery.getDestinationY());
        assertEquals(Delivery.DeliveryStatus.PENDING, delivery.getStatus());
    }

    @Test
    void testCreateDelivery_WithZeroCoordinates_ShouldCreateDelivery() {
        // Arrange
        Integer destX = 0;
        Integer destY = 0;

        // Act
        Delivery delivery = entityFactory.createDelivery(mockRobot, destX, destY);

        // Assert
        assertNotNull(delivery);
        assertEquals(0, delivery.getDestinationX());
        assertEquals(0, delivery.getDestinationY());
    }

    @Test
    void testCreateDelivery_WithNegativeCoordinates_ShouldCreateDelivery() {
        // Arrange
        Integer destX = -50;
        Integer destY = -75;

        // Act
        Delivery delivery = entityFactory.createDelivery(mockRobot, destX, destY);

        // Assert
        assertNotNull(delivery);
        assertEquals(-50, delivery.getDestinationX());
        assertEquals(-75, delivery.getDestinationY());
    }

    @Test
    void testCreateDelivery_WithLargeCoordinates_ShouldCreateDelivery() {
        // Arrange
        Integer destX = 999999;
        Integer destY = 888888;

        // Act
        Delivery delivery = entityFactory.createDelivery(mockRobot, destX, destY);

        // Assert
        assertNotNull(delivery);
        assertEquals(999999, delivery.getDestinationX());
        assertEquals(888888, delivery.getDestinationY());
        assertEquals(mockRobot, delivery.getRobot());
    }
}