import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final int guestId;
    private String email;
    private final List<String> preferences;
    
    public Guest(int guestId, String name, String phoneNumber, String email) {
        super(name, phoneNumber);
        this.guestId = guestId;
        setEmail(email);
        this.preferences = new ArrayList<>();
    }
    
    public int getGuestId() {
        return guestId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }
    
    public List<String> getPreferences() {
        return new ArrayList<>(preferences);
    }
    
    public void addPreference(String preference) {
        if (preference != null && !preference.isBlank()) {
            preferences.add(preference);
        }
    }
    
    public boolean removePreference(String preference) {
        return preferences.remove(preference);
    }
    
    public boolean hasPreference(String preference) {
        return preferences.contains(preference);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Guest guest = (Guest) obj;
        return guestId == guest.guestId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(guestId);
    }
    
    public String getDisplayInfo() {
        return String.format("%s (ID: %d)%nPhone: %s%nEmail: %s", 
            getName(), guestId, getPhoneNumber(), email);
    }
    
    @Override
    public String toString() {
        return String.format("Guest[ID=%d, Name=%s]", guestId, getName());
    }
}