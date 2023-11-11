import java.time.LocalDate;
import java.time.Period;

public class ProductManagerTest {

    public static void main(String[] args) {
        // Test case I: Create a product with all right arguments
        System.out.println("Test I: Create product with all right arguments");
        ProductManager.createProduct("123", "Apple", LocalDate.now().plusMonths(3), Period.ofDays(6));

        // Test case II: Create another product with a duplicate ProductID
        System.out.println("\nTest II: Create product with duplicate ProductID");
        ProductManager.createProduct("123", "Orange", LocalDate.now().plusMonths(3), Period.ofDays(6));

        // Test case III: Run the createProduct command without parameters
        System.out.println("\nTest III: Create product without parameters");
        ProductManager.createProduct();

        // Test case IV: Check default values
        System.out.println("\nTest IV: Create product with default values");
        ProductManager.createProduct("456", "Banana");
        // After this, you would typically check in your database or data structure
        // to ensure that the default values are set correctly.
        // This would be more of an integration test or a manual verification.
    }
}

