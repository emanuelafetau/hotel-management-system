package UI;
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
    
 // ADD THIS METHOD – do not remove existing ones
    public void showDashboard(Admin admin) {
        DashboardView dashboardView = new DashboardView(hotel, admin);

        Scene scene = new Scene(
                dashboardView.getRoot(),
                1100,
                700
        );

        // Add dashboard-specific CSS (login CSS stays untouched)
        scene.getStylesheets().add(
                getClass().getResource("dashboard.css").toExternalForm()
        );

        primaryStage.setTitle("Hotel Management System - Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}