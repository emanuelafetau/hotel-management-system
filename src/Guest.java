
import java.util.ArrayList;

	public class Guest {

	    private int guestId;
	    private String name;
	    private String phoneNumber;
	    private boolean checkedIn;
	    private ArrayList<String> preferences;

	    private static ArrayList<Guest> allGuests = new ArrayList<>();
	    private static int guestCounter = 1;

	    private Guest(int guestId, String name, String phoneNumber) {
	        this.guestId = guestId;
	        this.name = name;
	        this.phoneNumber = phoneNumber;
	        this.checkedIn = false;
	        this.preferences = new ArrayList<>();
	    }

	    
	    public static Guest registerGuest(String name, String phoneNumber) {
	        int newId = guestCounter++;
	        Guest guest = new Guest(newId, name, phoneNumber);
	        allGuests.add(guest);
	        System.out.println("Guest " + name + " registered successfully with ID " + newId);
	        return guest;
	    }

	    
	    public static ArrayList<Guest> viewAllGuests() {
	        return new ArrayList<>(allGuests);
	    }

	    
	    public static Guest findGuestById(int guestId) {
	        for (Guest g : allGuests) {
	            if (g.getGuestId() == guestId) {
	                return g;
	            }
	        }
	        return null;
	    }

	    
	    public int getGuestId() {
	        return guestId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getPhoneNumber() {
	        return phoneNumber;
	    }

	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }

	    public boolean isCheckedIn() {
	        return checkedIn;
	    }

	    public void setCheckedIn(boolean checkedIn) {
	        this.checkedIn = checkedIn;
	    }

	    public ArrayList<String> getPreferences() {
	        return preferences;
	    }

	    
	    
	    public void addPreference(String preference) {
	        preferences.add(preference);
	    }
	    
	    
	 
	    public void checkIn() {
	        if (!checkedIn) {
	            checkedIn = true;
	            System.out.println(name + " has checked in.");
	        } else {
	            System.out.println(name + " is already checked in.");
	        }
	    }

	    public void checkOut() {
	        if (checkedIn) {
	            checkedIn = false;
	            System.out.println(name + " has checked out.");
	        } else {
	            System.out.println(name + " is not currently checked in.");
	        }
	    }

	   
	    public void viewDetails() {
	        System.out.println("========== Guest Details ==========");
	        System.out.println("Guest ID: " + guestId);
	        System.out.println("Name: " + name);
	        System.out.println("Phone: " + phoneNumber);
	        System.out.println("Checked In: " + (checkedIn ? "Yes" : "No"));
	        System.out.println("Preferences: " + (preferences.isEmpty() ? "None" : preferences));
	        System.out.println("===================================");
	    }

	    
	    @Override
	    public String toString() {
	        return "Guest " + guestId + ": " + name + " (" + phoneNumber + ")";
	    }
	}
