import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

// Manages inventory items and logs inventory changes
class InventoryManager {
    private static Map<String, LocalDateTime> log = new HashMap<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Date format without milliseconds

    public static void notifyInventoryLevel(InventoryItem item) {
        System.out.println("Notification: Inventory level for " + item.getName() + " is out of bounds.");
        logEvent(item.getName());
    }

    // New method for expiry notification
    public static void notifyExpiry(InventoryItem item, int quantity) {
        System.out.println("Expiry Notification: " + quantity + " of " + item.getName() + " have expired.");
        logEvent(item.getName() + " - Expired");
    }

    // New method for markdown notification
    public static void notifyMarkdown(InventoryItem item, int quantity) {
        System.out.println("Markdown Notification: " + quantity + " of " + item.getName() + " need to be marked down.");
        logEvent(item.getName() + " - Markdown");
    }

    private static void logEvent(String itemName) {
        LocalDateTime timestamp = LocalDateTime.now();
        String formattedTimestamp = timestamp.format(formatter); // Format the timestamp
        log.put(itemName, timestamp);
        System.out.println("Logged: " + itemName + " at " + formattedTimestamp);
    }

    // New public method for general logging
    public static void logGeneralMessage(String message) {
        LocalDateTime timestamp = LocalDateTime.now();
        String formattedTimestamp = timestamp.format(formatter);
        log.put(message, timestamp);  // You might want to adjust how messages are stored in the log map
        System.out.println("Logged: " + message + " at " + formattedTimestamp);
    }

    // Additional methods for managing inventory
    // ...
}
