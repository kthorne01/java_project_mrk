import java.time.LocalDate;
import java.time.Period;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Example usage with markdown dates
        InventoryItem apples = new InventoryItem("Apples", 10, 45, LocalDate.of(2023, 12, 1), Period.ofDays(7));
        InventoryItem lawnMowers = new InventoryItem("Lawn Mowers", 2, 5, LocalDate.of(2024, 1, 1), Period.ofDays(14));
        InventoryItem diapers = new InventoryItem("Baby Diapers", 5, 15, LocalDate.of(2023, 11, 30), Period.ofDays(10));


        // Simulating inventory update
        apples.updateQuantity(9); // This should trigger a notification and logging
        lawnMowers.updateQuantity(6); // This should also trigger a notification and logging
        diapers.updateQuantity(3);
    }
}
