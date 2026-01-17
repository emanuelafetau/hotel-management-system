import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator {
    
    private final Stage primaryStage;
    private final Hotel hotel;
    
    public SceneNavigator(Stage primaryStage, Hotel hotel) {
        this.primaryStage = primaryStage;
        this.hotel = hotel;
    }
    
    public void showLogin() {
        LoginView loginView = new LoginView(hotel, this);
        Scene scene = new Scene(loginView.getRoot(), 1100, 600);
        
        // Load CSS stylesheet
        scene.getStylesheets().add(
            getClass().getResource("login.css").toExternalForm()
        );
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Grand Plaza Hotel - Management System");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public void showDashboard(Admin admin) {
        // TODO: Create DashboardView
        System.out.println("Login successful! Welcome, " + admin.getName());
        System.out.println("Dashboard would be shown here.");
        
        // For now, just show a simple message
        // Later you can create a proper DashboardView
    }
}