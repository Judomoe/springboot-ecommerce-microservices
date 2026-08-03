package com.hamada.shopservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hamada.shopservice.entity.Cart;
import com.hamada.shopservice.entity.CartItem;
import com.hamada.shopservice.entity.Category;
import com.hamada.shopservice.entity.OrderItem;
import com.hamada.shopservice.entity.OrderStatus;
import com.hamada.shopservice.entity.Payment;
import com.hamada.shopservice.entity.PaymentMethod;
import com.hamada.shopservice.entity.PaymentStatus;
import com.hamada.shopservice.entity.Product;
import com.hamada.shopservice.entity.ProductGender;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ShopServiceApplicationTests {
	static Long productId;
	static Long cartId;
	static Long cartItemId;
	static Long orderId;
	static Long orderItemId;
	static Long paymentId;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	static Long categoryId;

	@JsonIgnoreProperties(value = {"orderItems", "cartItems", "products"}, ignoreUnknown = true)
	interface IgnoreListsMixIn {}

	@org.junit.jupiter.api.BeforeEach
	void setupJackson() {
		objectMapper.addMixIn(Product.class, IgnoreListsMixIn.class);
		objectMapper.addMixIn(com.hamada.shopservice.entity.Order.class, IgnoreListsMixIn.class);
		objectMapper.addMixIn(Cart.class, IgnoreListsMixIn.class);
		objectMapper.addMixIn(Category.class, IgnoreListsMixIn.class);
	}

	@BeforeAll
	static void start() {
		System.out.println();
		System.out.println("=========================================");
		System.out.println("SHOP SERVICE INTEGRATION TESTS");
		System.out.println("=========================================");
		System.out.println();
	}

	@AfterAll
	static void finish() {
		System.out.println();
		System.out.println("=========================================");
		System.out.println("ALL PART 1 TESTS PASSED");
		System.out.println("=========================================");
		System.out.println();
	}

	@Test
	@Order(1)
	void createCategory() throws Exception {
		System.out.println("Creating Category...");
		Category category = new Category();
		category.setName("Shoes");
		category.setDescription("Running Shoes");
		category.setImageUrl("shoe.jpg");

		String response = mockMvc.perform(post("/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(category)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Shoes"))
				.andReturn()
				.getResponse()
				.getContentAsString();

		Category saved = objectMapper.readValue(response, Category.class);
		categoryId = saved.getId();
		assertNotNull(categoryId);
		System.out.println("✓ Category Created");
	}

	@Test
	@Order(2)
	void getAllCategories() throws Exception {
		System.out.println("Getting All Categories...");
		mockMvc.perform(get("/categories"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
		System.out.println("✓ Get All Passed");
	}

	@Test
	@Order(3)
	void getCategoryById() throws Exception {
		System.out.println("Finding Category By Id...");
		mockMvc.perform(get("/categories/" + categoryId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(categoryId))
				.andExpect(jsonPath("$.name").value("Shoes"));
		System.out.println("✓ Find By Id Passed");
	}

	@Test
	@Order(4)
	void searchCategory() throws Exception {
		System.out.println("Searching Category...");
		mockMvc.perform(get("/categories/search")
						.param("name", "Sho"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Shoes"));
		System.out.println("✓ Search Passed");
	}

	@Test
	@Order(5)
	void updateCategory() throws Exception {
		System.out.println("Updating Category...");
		Category category = new Category();
		category.setName("Sports Shoes");
		category.setDescription("Updated Description");
		category.setImageUrl("updated.jpg");

		mockMvc.perform(put("/categories/" + categoryId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(category)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Sports Shoes"))
				.andExpect(jsonPath("$.description")
						.value("Updated Description"));
		System.out.println("✓ Update Passed");
	}

	@Test
	@Order(6)
	void verifyUpdatedCategory() throws Exception {
		System.out.println("Verifying Update...");
		mockMvc.perform(get("/categories/" + categoryId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name")
						.value("Sports Shoes"));
		System.out.println("✓ Verification Passed");
	}

	@Test
	@Order(7)
	void searchNotFound() throws Exception {
		System.out.println("Searching Missing Category...");
		mockMvc.perform(get("/categories/search")
						.param("name", "abcdefgh"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
		System.out.println("✓ Empty Search Passed");
	}

	@Test
	@Order(8)
	void invalidCategoryId() throws Exception {
		System.out.println("Testing Invalid Category...");
		// Typically looking up a bad ID should return 404, not 500.
		mockMvc.perform(get("/categories/999999"))
				.andExpect(status().isNotFound());
		System.out.println("✓ Invalid Id Exception Passed");
	}

	@Test
	@Order(11)
	void createProduct() throws Exception {
		System.out.println("Creating Product...");
		Product product = new Product();
		product.setName("Nike Air");
		product.setDescription("Running Shoes");
		product.setPrice(2500.0);
		product.setDiscount(15.0);
		product.setGender(ProductGender.MALE);

		Category category = new Category();
		category.setId(categoryId);
		product.setCategory(category);

		product.setImageUrl("nike.jpg");
		product.setRating(4.8);

		String response =
				mockMvc.perform(post("/products")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(product)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").exists())
						.andReturn()
						.getResponse()
						.getContentAsString();

		Product saved = objectMapper.readValue(response, Product.class);
		productId = saved.getId();
		assertNotNull(productId);
		System.out.println("✓ Product Created");
	}

	@Test
	@Order(12)
	void getAllProducts() throws Exception {
		System.out.println("Getting All Products...");
		mockMvc.perform(get("/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
		System.out.println("✓ Get All Products");
	}

	@Test
	@Order(13)
	void getProductById() throws Exception {
		System.out.println("Getting Product By Id...");
		mockMvc.perform(get("/products/" + productId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(productId))
				.andExpect(jsonPath("$.name").value("Nike Air"));
		System.out.println("✓ Product Found");
	}

	@Test
	@Order(14)
	void searchProduct() throws Exception {
		System.out.println("Searching Product...");
		mockMvc.perform(get("/products/search")
						.param("name","Nike"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Nike Air"));
		System.out.println("✓ Search Passed");
	}

	@Test
	@Order(15)
	void filterByCategory() throws Exception {
		System.out.println("Filtering By Category...");
		mockMvc.perform(get("/products/category/" + categoryId))
				.andExpect(status().isOk())
				// Check your JSON output. If the response embeds the category object, it's $.category.id
				.andExpect(jsonPath("$[0].category.id").value(categoryId));
		System.out.println("✓ Category Filter Passed");
	}

	@Test
	@Order(16)
	void filterByGender() throws Exception {
		System.out.println("Filtering By Gender...");
		mockMvc.perform(get("/products/gender/MALE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].gender").value("MALE"));
		System.out.println("✓ Gender Filter Passed");
	}

	@Test
	@Order(17)
	void discountedProducts() throws Exception {
		System.out.println("Finding Discounted Products...");
		mockMvc.perform(get("/products/discounted"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].discount").value(15.0));
		System.out.println("✓ Discount Filter Passed");
	}

	@Test
	@Order(18)
	void newestProducts() throws Exception {
		System.out.println("Sorting Products...");
		mockMvc.perform(get("/products/newest"))
				.andExpect(status().isOk());
		System.out.println("✓ Sorting Passed");
	}

	@Test
	@Order(19)
	void updateProduct() throws Exception {
		System.out.println("Updating Product...");
		Product product = new Product();
		product.setName("Nike Air Max");
		product.setDescription("Updated");
		product.setPrice(3000.0);
		product.setDiscount(20.0);
		product.setGender(ProductGender.UNISEX);

		Category category = new Category();
		category.setId(categoryId);
		product.setCategory(category);

		product.setImageUrl("updated.jpg");
		product.setRating(5.0);

		mockMvc.perform(put("/products/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(product)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Nike Air Max"))
				.andExpect(jsonPath("$.price").value(3000.0));
		System.out.println("✓ Update Passed");
	}

	@Test
	@Order(20)
	void verifyUpdatedProduct() throws Exception {
		mockMvc.perform(get("/products/" + productId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Nike Air Max"))
				.andExpect(jsonPath("$.discount").value(20.0))
				.andExpect(jsonPath("$.rating").value(5.0));
		System.out.println("✓ Verification Passed");
	}

	@Test
	@Order(21)
	void searchMissingProduct() throws Exception {
		mockMvc.perform(get("/products/search")
						.param("name","abcdefgh"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
		System.out.println("✓ Missing Search Passed");
	}

	@Test
	@Order(22)
	void invalidProductId() throws Exception {
		mockMvc.perform(get("/products/999999"))
				.andExpect(status().isNotFound());
		System.out.println("✓ Invalid Product Exception");
	}

	@Test
	@Order(23)
	void invalidGender() throws Exception {
		// Typically an invalid enum string in a URL might trigger a 400 Bad Request
		mockMvc.perform(get("/products/gender/ALIEN"))
				.andExpect(status().is4xxClientError());
		System.out.println("✓ Invalid Gender Exception");
	}

	@Test
	@Order(24)
	void createCart() throws Exception {
		System.out.println("Creating Cart...");
		Cart cart = new Cart();
		cart.setUserId(1L);
		cart.setTotalCost(3000.0);

		String response =
				mockMvc.perform(post("/carts")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(cart)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").exists())
						.andReturn()
						.getResponse()
						.getContentAsString();

		Cart saved = objectMapper.readValue(response, Cart.class);
		cartId = saved.getId();
		assertNotNull(cartId);
		System.out.println("✓ Cart Created");
	}

	@Test
	@Order(25)
	void getCart() throws Exception {
		mockMvc.perform(get("/carts/" + cartId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(cartId));
		System.out.println("✓ Cart Retrieved");
	}

	@Test
	@Order(26)
	void getAllCarts() throws Exception {
		mockMvc.perform(get("/carts"))
				.andExpect(status().isOk());
		System.out.println("✓ All Carts Retrieved");
	}

	@Test
	@Order(27)
	void findCartByUser() throws Exception {
		mockMvc.perform(get("/carts/user/1"))
				.andExpect(status().isOk());
		System.out.println("✓ Find By User Passed");
	}

	@Test
	@Order(28)
	void updateCart() throws Exception {
		Cart cart = new Cart();
		cart.setTotalCost(5000.0);
		cart.setUserId(1L);

		mockMvc.perform(put("/carts/" + cartId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cart)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalCost").value(5000.0));
		System.out.println("✓ Cart Updated");
	}

	@Test
	@Order(29)
	void invalidCart() throws Exception {
		mockMvc.perform(get("/carts/999999"))
				.andExpect(status().isNotFound());
		System.out.println("✓ Invalid Cart Exception");
	}

	@Test
	@Order(30)
	void createCartItem() throws Exception {
		System.out.println("Creating Cart Item...");
		CartItem item = new CartItem();
		Cart cart = new Cart();
		cart.setId(cartId);

		Product product = new Product();
		product.setId(productId);

		item.setCart(cart);
		item.setProduct(product);
		item.setQuantity(3);
		item.setUnitPrice(3000.0);

		String response =
				mockMvc.perform(post("/cart-items")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(item)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").exists())
						.andReturn()
						.getResponse()
						.getContentAsString();

		CartItem saved = objectMapper.readValue(response, CartItem.class);
		cartItemId = saved.getId();
		assertNotNull(cartItemId);
		System.out.println("✓ Cart Item Created");
	}

	@Test
	@Order(31)
	void getCartItem() throws Exception {
		mockMvc.perform(get("/cart-items/" + cartItemId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.quantity").value(3));
		System.out.println("✓ Cart Item Retrieved");
	}

	@Test
	@Order(32)
	void getAllCartItems() throws Exception {
		mockMvc.perform(get("/cart-items"))
				.andExpect(status().isOk());
		System.out.println("✓ All Cart Items");
	}

	@Test
	@Order(33)
	void getItemsByCart() throws Exception {
		mockMvc.perform(get("/cart-items/cart/" + cartId))
				.andExpect(status().isOk());
		System.out.println("✓ Cart Relationship Verified");
	}

//	@Test
//	@Order(34)
//	void getCartItemsByProduct() throws Exception {
//		mockMvc.perform(get("/cart-items/product/" + productId))
//				.andExpect(status().isOk());
//		System.out.println("✓ Product Relationship Verified");
//	}

	@Test
	@Order(35)
	void updateCartItem() throws Exception {
		Cart cart = new Cart();
		cart.setId(cartId);

		Product product = new Product();
		product.setId(productId);

		CartItem item = new CartItem();
		item.setCart(cart);
		item.setProduct(product);
		item.setQuantity(5);
		item.setUnitPrice(3200.0);

		mockMvc.perform(put("/cart-items/" + cartItemId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(item)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.quantity").value(5));
		System.out.println("✓ Cart Item Updated");
	}

	@Test
	@Order(36)
	void invalidCartItem() throws Exception {
		mockMvc.perform(get("/cart-items/999999"))
				.andExpect(status().isNotFound());
		System.out.println("✓ Invalid Cart Item Exception");
	}

	@Test
	@Order(37)
	void createOrder() throws Exception {
		System.out.println("Creating Order...");
		com.hamada.shopservice.entity.Order order = new com.hamada.shopservice.entity.Order();
		order.setUserId(1L);
		order.setStatus(OrderStatus.PENDING);
		order.setTotalPrice(5000.0);

		String response =
				mockMvc.perform(post("/orders")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(order)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").exists())
						.andReturn()
						.getResponse()
						.getContentAsString();

		com.hamada.shopservice.entity.Order saved =
				objectMapper.readValue(response, com.hamada.shopservice.entity.Order.class);
		orderId = saved.getId();
		assertNotNull(orderId);
		System.out.println("✓ Order Created");
	}

	@Test
	@Order(38)
	void getOrder() throws Exception {
		mockMvc.perform(get("/orders/" + orderId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(orderId));
		System.out.println("✓ Order Retrieved");
	}

	@Test
	@Order(39)
	void getAllOrders() throws Exception {
		mockMvc.perform(get("/orders"))
				.andExpect(status().isOk());
		System.out.println("✓ All Orders");
	}

	@Test
	@Order(40)
	void getOrdersByUser() throws Exception {
		mockMvc.perform(get("/orders/user/1"))
				.andExpect(status().isOk());
		System.out.println("✓ Orders By User");
	}

	@Test
	@Order(41)
	void getOrdersByStatus() throws Exception {
		mockMvc.perform(get("/orders/status/PENDING"))
				.andExpect(status().isOk());
		System.out.println("✓ Orders By Status");
	}

	@Test
	@Order(42)
	void updateOrder() throws Exception {
		com.hamada.shopservice.entity.Order order = new com.hamada.shopservice.entity.Order();
		order.setStatus(OrderStatus.SHIPPED);
		order.setTotalPrice(5500.0);
		order.setUserId(1L);

		mockMvc.perform(put("/orders/" + orderId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(order)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("SHIPPED"));
		System.out.println("✓ Order Updated");
	}

	@Test
	@Order(43)
	void verifyUpdatedOrder() throws Exception {
		mockMvc.perform(get("/orders/" + orderId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("SHIPPED"));
		System.out.println("✓ Order Verification");
	}

	@Test
	@Order(44)
	void invalidOrder() throws Exception {
		mockMvc.perform(get("/orders/999999"))
				.andExpect(status().isNotFound());
		System.out.println("✓ Invalid Order Exception");
	}

	@Test
	@Order(45)
	void createOrderItem() throws Exception {
		System.out.println("Creating Order Item...");
		OrderItem item = new OrderItem();

		com.hamada.shopservice.entity.Order order = new com.hamada.shopservice.entity.Order();
		order.setId(orderId);

		Product product = new Product();
		product.setId(productId);

		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(2);
		item.setPrice(2750.0);

		String response =
				mockMvc.perform(post("/order-items")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(item)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").exists())
						.andReturn()
						.getResponse()
						.getContentAsString();

		OrderItem saved = objectMapper.readValue(response, OrderItem.class);
		orderItemId = saved.getId();
		assertNotNull(orderItemId);
		System.out.println("✓ Order Item Created");
	}

	@Test
	@Order(46)
	void getOrderItem() throws Exception {
		mockMvc.perform(get("/order-items/" + orderItemId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.quantity").value(2));
		System.out.println("✓ Order Item Retrieved");
	}

	@Test
	@Order(47)
	void getAllOrderItems() throws Exception {
		mockMvc.perform(get("/order-items"))
				.andExpect(status().isOk());
		System.out.println("✓ All Order Items");
	}

	@Test
	@Order(48)
	void getItemsByOrder() throws Exception {
		mockMvc.perform(get("/order-items/order/" + orderId))
				.andExpect(status().isOk());
		System.out.println("✓ Order Relationship");
	}

	@Test
	@Order(49)
	void getOrderItemsByProduct() throws Exception {
		mockMvc.perform(get("/order-items/product/" + productId))
				.andExpect(status().isOk());
		System.out.println("✓ Product Relationship");
	}

	@Test
	@Order(50)
	void updateOrderItem() throws Exception {
		com.hamada.shopservice.entity.Order order = new com.hamada.shopservice.entity.Order();
		order.setId(orderId);

		Product product = new Product();
		product.setId(productId);

		OrderItem item = new OrderItem();
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(4);
		item.setPrice(3000.0);

		mockMvc.perform(put("/order-items/" + orderItemId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(item)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.quantity").value(4));
		System.out.println("✓ Order Item Updated");
	}

	@Test
	@Order(51)
	void invalidOrderItem() throws Exception {
		mockMvc.perform(get("/order-items/999999"))
				.andExpect(status().isNotFound());
		System.out.println("✓ Invalid Order Item Exception");
	}

	@Test
	@Order(52)
	void createPayment() throws Exception {
		System.out.println("Creating Payment...");
		Payment payment = new Payment();
		payment.setUserId(1L);
		payment.setAmount(5500.0);
		payment.setMethod(PaymentMethod.CARD);
		payment.setStatus(PaymentStatus.SUCCESS);

		com.hamada.shopservice.entity.Order order = new com.hamada.shopservice.entity.Order();
		order.setId(orderId);
		payment.setOrder(order);

		String response =
				mockMvc.perform(post("/payments")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(payment)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").exists())
						.andReturn()
						.getResponse()
						.getContentAsString();

		Payment saved = objectMapper.readValue(response, Payment.class);
		paymentId = saved.getId();
		assertNotNull(paymentId);
		System.out.println("✓ Payment Created");
	}

	@Test
	@Order(53)
	void getPayment() throws Exception {
		mockMvc.perform(get("/payments/" + paymentId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(paymentId));
		System.out.println("✓ Payment Retrieved");
	}

	@Test
	@Order(54)
	void getAllPayments() throws Exception {
		mockMvc.perform(get("/payments"))
				.andExpect(status().isOk());
		System.out.println("✓ All Payments");
	}

	@Test
	@Order(55)
	void getPaymentsByUser() throws Exception {
		mockMvc.perform(get("/payments/user/1"))
				.andExpect(status().isOk());
		System.out.println("✓ Payments By User");
	}

	@Test
	@Order(56)
	void getPaymentsByStatus() throws Exception {
		mockMvc.perform(get("/payments/status/SUCCESS"))
				.andExpect(status().isOk());
		System.out.println("✓ Payments By Status");
	}

	@Test
	@Order(57)
	void getPaymentsByMethod() throws Exception {
		mockMvc.perform(get("/payments/method/CARD"))
				.andExpect(status().isOk());
		System.out.println("✓ Payments By Method");
	}

	@Test
	@Order(58)
	void updatePayment() throws Exception {
		Payment payment = new Payment();
		payment.setAmount(6000.0);
		payment.setMethod(PaymentMethod.CASH);
		payment.setStatus(PaymentStatus.SUCCESS);
		payment.setUserId(1L);

		com.hamada.shopservice.entity.Order order = new com.hamada.shopservice.entity.Order();
		order.setId(orderId);
		payment.setOrder(order);

		mockMvc.perform(put("/payments/" + paymentId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payment)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.amount").value(6000.0))
				.andExpect(jsonPath("$.method").value("CASH"));
		System.out.println("✓ Payment Updated");
	}

	@Test
	@Order(59)
	void invalidPayment() throws Exception {
		mockMvc.perform(get("/payments/999999"))
				.andExpect(status().isNotFound());
		System.out.println("✓ Invalid Payment Exception");
	}

	@Test
	@Order(60)
	void processPayment() throws Exception {
		mockMvc.perform(post("/payments/process/" + orderId)
						.param("method", "CARD"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("SUCCESS"))
				.andExpect(jsonPath("$.method").value("CARD"))
				.andExpect(jsonPath("$.amount").exists());
		System.out.println("✓ Payment Processing");
	}

	@Test
	@Order(61)
	void verifyOrderContainsPayment() throws Exception {
		mockMvc.perform(get("/orders/" + orderId))
				.andExpect(status().isOk())
				// Check if payment block exists, might need adjustment based on your exact JSON shape
				.andExpect(jsonPath("$.payment").exists());
		System.out.println("✓ Order Linked To Payment");
	}

	@Test
	@Order(990)
	void deletePayment() throws Exception {
		mockMvc.perform(delete("/payments/" + paymentId))
				.andExpect(status().isOk());
		System.out.println("✓ Payment Deleted");
	}

	@Test
	@Order(991)
	void verifyPaymentDeleted() throws Exception {
		mockMvc.perform(get("/payments/" + paymentId))
				.andExpect(status().isNotFound());
		System.out.println("✓ Payment Delete Verified");
	}

	@Test
	@Order(992)
	void deleteOrderItem() throws Exception {
		mockMvc.perform(delete("/order-items/" + orderItemId))
				.andExpect(status().isOk());
		System.out.println("✓ Order Item Deleted");
	}

	@Test
	@Order(993)
	void deleteOrder() throws Exception {
		mockMvc.perform(delete("/orders/" + orderId))
				.andExpect(status().isOk());
		System.out.println("✓ Order Deleted");
	}

	@Test
	@Order(994)
	void deleteCartItem() throws Exception {
		mockMvc.perform(delete("/cart-items/" + cartItemId))
				.andExpect(status().isOk());
		System.out.println("✓ Cart Item Deleted");
	}

	@Test
	@Order(995)
	void deleteCart() throws Exception {
		mockMvc.perform(delete("/carts/" + cartId))
				.andExpect(status().isOk());
		System.out.println("✓ Cart Deleted");
	}

	@Test
	@Order(996)
	void deleteProduct() throws Exception {
		mockMvc.perform(delete("/products/" + productId))
				.andExpect(status().isOk());
		System.out.println("✓ Product Deleted");
	}

	@Test
	@Order(997)
	void verifyProductDeleted() throws Exception {
		mockMvc.perform(get("/products/" + productId))
				.andExpect(status().isNotFound());
		System.out.println("✓ Product Delete Verified");
	}

	// Moved from earlier to avoid breaking foreign key constraints on Product
	@Test
	@Order(998)
	void deleteCategory() throws Exception {
		System.out.println("Deleting Category...");
		mockMvc.perform(delete("/categories/" + categoryId))
				.andExpect(status().isOk());
		System.out.println("✓ Delete Passed");
	}

	@Test
	@Order(999)
	void verifyDelete() throws Exception {
		System.out.println("Verifying Delete...");
		mockMvc.perform(get("/categories/" + categoryId))
				.andExpect(status().isNotFound());
		System.out.println("✓ Delete Verified");
	}
}