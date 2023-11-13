import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

class ProductManager {
    private static Map<String, InventoryItem> products = new HashMap<>();

    public static void createProduct(String productId, String productName, LocalDate expiryDate, Period markdownPeriod) {
        // Check if productId is unique
        if (products.containsKey(productId)) {
            System.out.println("ProductName should have a uniqueID, the ProductName already exists with the same uniqueID");
            return;
        }

        // Check for mandatory parameters
        if (productId == null || productName == null) {
            System.out.println("productID and ProductName are required. Other arguments take default values");
            // Set default values if not provided
            expiryDate = (expiryDate == null) ? LocalDate.now().plusMonths(3) : expiryDate;
            markdownPeriod = (markdownPeriod == null) ? Period.ofDays(6) : markdownPeriod;
        }

        try {
            // Create and store the product
            //InventoryItem product = new InventoryItem(productName, /* default min and max values */, expiryDate, markdownPeriod);
            InventoryItem product = new InventoryItem(productName, 5, 50, expiryDate, markdownPeriod);
            products.put(productId, product);
            System.out.println(productName + " with the ProductID " + productId + " created successfully");
        } catch (Exception e) {
            // Log any other exceptions
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Overloaded createProduct method without parameters
    public static void createProduct() {
        // Handle the case where no parameters are provided
        System.out.println("ProductID and ProductName are required. Other arguments take default values.");
    }

    // Overloaded createProduct method for just ProductID and ProductName
    public static void createProduct(String productId, String productName) {
        // You can set default values for the other parameters here
        LocalDate defaultExpiryDate = LocalDate.now().plusMonths(3); // Example default value
        Period defaultMarkdownPeriod = Period.ofDays(6); // Example default value

        // Now call the original createProduct method with these default values
        createProduct(productId, productName, defaultExpiryDate, defaultMarkdownPeriod);
    }

    // Overloaded displayProduct method without parameters
    public static void displayProduct() {
        // Implementation to display all products
    }

    public static void displayProduct(String productName, String productId) {
        // Display all products if no parameters are provided
        if (productName == null && productId == null) {
            products.values().forEach(product -> System.out.println(product));
            return;
        }

        try {
            InventoryItem product = null;
            if (productId != null && products.containsKey(productId)) {
                product = products.get(productId);
            } else if (productName != null) {
                product = products.values().stream()
                        .filter(p -> p.getName().equals(productName))
                        .findFirst()
                        .orElse(null);
            }

            if (product == null) {
                System.out.println("Product name and Product ID not found");
            } else {
                System.out.println(product);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //shows the replenishment details for specific products identifying them by product ID
    public static void displayProductToRefill() {
        boolean needsRefill = false;
        for (InventoryItem item : products.values()) {
            if (item.needsReplenishment()) {
                System.out.println(item.getName() + " needs to be replenished by " + item.calculateReplenishmentQuantity() + " units.");
                needsRefill = true;
            }
        }

        if (!needsRefill) {
            System.out.println("No products need to be replenished at this time.");
        }
    }

    public static void displayProductToRefill(String productId) {
        InventoryItem item = products.get(productId);

        if (item == null) {
            System.out.println("ProductID " + productId + " not found.");
            return;
        }

        if (item.needsReplenishment()) {
            System.out.println(item.getName() + " needs to be replenished by " + item.calculateReplenishmentQuantity() + " units.");
        } else {
            System.out.println("Product " + item.getName() + " does not need replenishment at this time.");
        }
    }

    //displays the count of a specific product by product ID
    public static void displayProductCount() {
        if (products.isEmpty()) {
            System.out.println("No products on the shelf.");
            return;
        }

        for (InventoryItem item : products.values()) {
            System.out.println(item.getName() + " count: " + item.getCurrentQuantity());
        }
    }


    public static void displayProductCount(String productId) {
        InventoryItem item = products.get(productId);

        if (item == null) {
            System.out.println("ProductID " + productId + " not found.");
            return;
        }

        System.out.println(item.getName() + " count: " + item.getCurrentQuantity());
    }

    //displays the expiry date of a product specified by its product ID
    public static void displayProductsExpiryDate(String productId) {
        InventoryItem item = products.get(productId);

        if (item == null) {
            System.out.println("ProductID " + productId + " not found.");
            return;
        }

        System.out.println("Product " + item.getName() + " (ID: " + productId + ") expires on " + item.getExpiryDate());
    }

    //displays the expiry dates of all products
    public static void displayProductsExpiryDate() {
        if (products.isEmpty()) {
            System.out.println("No products on the shelf.");
            return;
        }

        for (Map.Entry<String, InventoryItem> entry : products.entrySet()) {
            InventoryItem item = entry.getValue();
            System.out.println("Product " + item.getName() + " (ID: " + entry.getKey() + ") expires on " + item.getExpiryDate());
        }
    }

    //displays all expired products and makes the message stick out
    public static void displayExpiredProducts() {
        boolean hasExpiredProducts = false;
        LocalDate today = LocalDate.now();

        for (Map.Entry<String, InventoryItem> entry : products.entrySet()) {
            InventoryItem item = entry.getValue();
            if (item.getExpiryDate().isBefore(today)) {
                System.out.println("***** EXPIRED: Product " + item.getName() + " (ID: " + entry.getKey() + ") expired on " + item.getExpiryDate() + " *****");
                hasExpiredProducts = true;
            }
        }

        if (!hasExpiredProducts) {
            System.out.println("No expired products on the shelf.");
        }
    }

    /**
     * Displays all products that are past their markdown date.
     * Iterates through all products, calculates their markdown date,
     * and checks if the markdown date is before today's date.
     * If a product is past its markdown date, it is displayed.
     * If no products are found past their markdown date, a relevant message is displayed.
     */
    public static void displayProductsInMarkDown() {
        boolean markdownProductFound = false;
        LocalDate today = LocalDate.now();

        for (Map.Entry<String, InventoryItem> entry : products.entrySet()) {
            InventoryItem item = entry.getValue();
            LocalDate markdownDate = item.getExpiryDate().minus(item.getMarkdownPeriod());

            if (markdownDate.isBefore(today)) {
                System.out.println("Product " + item.getName() + " (ID: " + entry.getKey() + ") is past the markdown date of " + markdownDate);
                markdownProductFound = true;
            }
        }

        if (!markdownProductFound) {
            System.out.println("No products are past the markdown date.");
        }
    }

    //displays all products that need to be marked down within the next week, if none then appropriate message shows
    public static void displayProductsForMarkDown() {
        boolean upcomingMarkdownFound = false;
        LocalDate today = LocalDate.now();
        LocalDate weekFromNow = today.plusWeeks(1);

        for (Map.Entry<String, InventoryItem> entry : products.entrySet()) {
            InventoryItem item = entry.getValue();
            LocalDate markdownDate = item.getExpiryDate().minus(item.getMarkdownPeriod());

            if (markdownDate.isAfter(today) && markdownDate.isBefore(weekFromNow)) {
                System.out.println("Product " + item.getName() + " (ID: " + entry.getKey() + ") needs to be marked down by " + markdownDate);
                upcomingMarkdownFound = true;
            }
        }

        if (!upcomingMarkdownFound) {
            System.out.println("No products need to be marked down within the next week.");
        }
    }




    // Other methods...
}

