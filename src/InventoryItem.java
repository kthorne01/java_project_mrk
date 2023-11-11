import java.time.LocalDate;
import java.time.Period;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Represents an individual inventory item
class InventoryItem {
    private String name;
    private int currentQuantity;
    private int minQuantity;
    private int maxQuantity;
    private LocalDate expiryDate; // New attribute for expiry date
    private Period markdownPeriod; // New attribute to store markdown period

    public boolean needsReplenishment() {
        return currentQuantity < minQuantity;
    }

    public int calculateReplenishmentQuantity() {
        return maxQuantity - currentQuantity;
    }

    public int getCurrentQuantity() {
        return this.currentQuantity;
    }

    // Method to get the expiry date
    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    // Method to get the markdown period
    public Period getMarkdownPeriod() {
        return this.markdownPeriod;
    }

    public InventoryItem(String name, int minQuantity, int maxQuantity, LocalDate expiryDate, Period markdownPeriod) {
        //adding in error handling
        if (minQuantity >= maxQuantity) {
            throw new IllegalArgumentException("Minimum quantity must be less than maximum quantity.");
        }
        if (expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry date must not be in the past.");
        }
        this.name = name;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.expiryDate = expiryDate;
        this.markdownPeriod = markdownPeriod;
        this.currentQuantity = 0;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Other getters and setters as needed
    // ...

    public void updateQuantity(int quantity) {
        this.currentQuantity = quantity;
        checkAndNotify();
        checkExpiry();
        checkMarkdown(); // New check for markdown
    }

    private void checkAndNotify() {
        if (currentQuantity < minQuantity || currentQuantity > maxQuantity) {
            //calling logGeneralMessage from outside the InventoryManager class, couldn't use log event due to private....
            //....access and instead of changing it to public, just created another method to keep it encapsulated
            InventoryManager.notifyInventoryLevel(this);
        } else {
            InventoryManager.logGeneralMessage(this.getName() + " - Inventory level is within the expected range.");
        }
    }

    // New method to check for expired items
    private void checkExpiry() {
        if (LocalDate.now().isAfter(expiryDate)) {
            InventoryManager.notifyExpiry(this, currentQuantity);
        }
    }

    // New method to check for markdowns
    private void checkMarkdown() {
        LocalDate markdownDate = expiryDate.minus(markdownPeriod);
        if (LocalDate.now().isAfter(markdownDate) && LocalDate.now().isBefore(expiryDate)) {
            InventoryManager.notifyMarkdown(this, currentQuantity);
        }
    }
}
