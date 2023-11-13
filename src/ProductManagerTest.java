import java.time.LocalDate;
import java.time.Period;

public class ProductManagerTest {

    //1st SET of test cases***************************
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

        //2nd SET of test cases*************************
        // Assuming you have a method to add products for testing purposes
        ProductManager.createProduct("001", "Apple", LocalDate.now().plusMonths(3), Period.ofDays(6));
        ProductManager.createProduct("002", "Banana", LocalDate.now().plusMonths(4), Period.ofDays(3)); // Additional parameters as required

        // Test case I: Display a specific product
        System.out.println("Test I: Display a specific product");
        ProductManager.displayProduct("Apple", "001");

        // Test case II: Try to display a product that does not exist
        System.out.println("\nTest II: Display a non-existing product");
        ProductManager.displayProduct("Orange", "003");

        // Test case III: Display all products (no parameters provided)
        System.out.println("\nTest III: Display all products");
        ProductManager.displayProduct();

        // Test case IV: You would typically simulate an error condition here,
        // but how you do this depends on your implementation and what kinds of errors
        // you expect. This might involve, for example, passing invalid arguments
        // or temporarily modifying your method to throw an exception.
    }
}

