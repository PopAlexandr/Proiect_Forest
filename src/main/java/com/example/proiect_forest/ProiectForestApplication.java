package com.example.proiect_forest;

import com.example.proiect_forest.model.*;
import com.example.proiect_forest.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
@Transactional
@SpringBootApplication
public class ProiectForestApplication {
	@Autowired
	private ProductServiceImpl productService;
	@Autowired
	private CategoryServiceImpl categoryService;
	@Autowired
	private SupplierServiceImpl supplierService;
	@Autowired
	private CustomerServiceImpl customerService;
	@Autowired
	private OrderServiceImpl orderService;
	@Autowired
	private OrderItemServiceImpl orderItemService;
	@Autowired
	private StockTransactionServiceImpl stockTransactionService;

	public static void main(String[] args) {
		SpringApplication.run(ProiectForestApplication.class, args);

		
	}
	@Bean
	public CommandLineRunner test(ApplicationContext context) {
		return args -> {
			// 1. Save Category and Supplier first
			Category category = new Category(null, "yuri32");
			Supplier supplier = new Supplier(null, "Seven Seas3", "contact@seas.com3", "123-456-78903");

			categoryService.saveCategory(category);
			supplierService.saveSupplier(supplier);

			// 2. Create and save Product with saved Category and Supplier
			Product product1 = new Product(null, "Product A1", "Manga A1", "Description A1", new BigDecimal(100), 10, "hhh");
			productService.addProduct(product1,category,supplier);

			// 3. Create and save Customer
			Customer customer = new Customer(null, "John Doe29", "john.doe30@example.com", "789-101-1152");
			customerService.saveCustomer(customer);

			// 4. Create and save Order with the Customer
			Order order = new Order(null, LocalDate.now().atStartOfDay(),new BigDecimal(2));
			 orderService.saveOrder(order,customer);

			// 5. Create and save OrderItem with Product and Order
			order = new Order(null, LocalDate.now().atStartOfDay(),new BigDecimal(2));
			product1 = new Product(null, "Product A1", "Manga A1", "Description A1", new BigDecimal(100), 10, "hhh");
			OrderItem orderItem = new OrderItem(null,  1,new BigDecimal(10)); // quantity is set to 2
			orderItemService.saveOrderItem(orderItem,order,product1);

			// 6. Create and save StockTransaction for Product
			StockTransaction stockTransaction = new StockTransaction(null,LocalDate.now().atStartOfDay(),3,"personal"); // e.g., stock of 5 received
			stockTransactionService.saveStockTransaction(stockTransaction,product1);

			// Display all products to verify
			System.out.println("All Products:");
			System.out.println(productService.getAllProducts());

			// Display all orders and order items to verify
			System.out.println("All Orders:");
			System.out.println(orderService.getAllOrders());
			System.out.println("All Order Items:");
			System.out.println(orderItemService.getAllOrderItems());

			// Display all stock transactions to verify
			System.out.println("All Stock Transactions:");
			System.out.println(stockTransactionService.getAllStockTransactions());
		};
	}
}


