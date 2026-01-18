import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoginView {

    // Option 1: Use online image (current)
    private static final String HERO_IMAGE_URL = 
            "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=900&h=900&fit=crop";
    
    // Option 2: Use local image - uncomment this and comment the line above
    // Put your image in the same folder as your .java files
    // private static final String HERO_IMAGE_URL = "file:hotel-lobby.jpg";

    private final Hotel hotel;
    private final SceneNavigator navigator;

    private final StackPane root = new StackPane();

    public LoginView(Hotel hotel, SceneNavigator navigator) {
        this.hotel = hotel;
        this.navigator = navigator;
        build();
    }

    public Parent getRoot() {
        return root;
    }

    private void build() {
        root.getStyleClass().add("root-background");
        root.setPadding(new Insets(30));

        // Card container
        BorderPane card = new BorderPane();
        card.getStyleClass().add("card");
        card.setMaxWidth(980);
        card.setPrefHeight(600);  // Increased height to match image

        DropShadow shadow = new DropShadow();
        shadow.setRadius(28);
        shadow.setOffsetY(14);
        shadow.setColor(Color.color(0, 0, 0, 0.55));
        card.setEffect(shadow);

        // LEFT: hero image + branding overlay
        StackPane left = createLeftPanel();
        
        // RIGHT: form
        VBox right = createRightPanel();

        card.setLeft(left);
        card.setCenter(right);

        root.getChildren().add(card);
    }

    private StackPane createLeftPanel() {
        StackPane left = new StackPane();
        left.setPrefWidth(420);
        left.setMinWidth(360);
        left.getStyleClass().add("left-panel");

        // Image that fills the entire height
        ImageView hero = new ImageView(new Image(HERO_IMAGE_URL, true));
        hero.setPreserveRatio(false);
        hero.setFitWidth(420);
        hero.setFitHeight(600);  // Match the card height
        
        // Clip image to have rounded corners on left side only
        hero.setClip(createLeftRoundedClip(420, 600));

        // Solid overlay - also clipped to match
        Region overlay = new Region();
        overlay.getStyleClass().add("image-overlay");
        overlay.setPrefSize(420, 600);
        overlay.setClip(createLeftRoundedClip(420, 600));

        VBox brandBox = new VBox(8);
        brandBox.setPadding(new Insets(26));
        brandBox.setAlignment(Pos.BOTTOM_LEFT);

        Label brand = new Label("GRAND PLAZA");
        brand.getStyleClass().add("brand-title");

        Label tagline = new Label("LUXURY • ELEGANCE • DISTINCTION");
        tagline.getStyleClass().add("brand-tagline");

        Label hotelName = new Label("Welcome to " + hotel.getHotelName());
        hotelName.getStyleClass().add("hotel-name");

        brandBox.getChildren().addAll(brand, tagline, hotelName);

        left.getChildren().addAll(hero, overlay, brandBox);

        return left;
    }
    
    // Creates a rectangle with rounded corners on LEFT side only
    private javafx.scene.shape.Rectangle createLeftRoundedClip(double width, double height) {
        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(width, height);
        clip.setArcWidth(40);  // Rounded corners
        clip.setArcHeight(40);
        return clip;
    }

    private VBox createRightPanel() {
        VBox right = new VBox(14);
        right.setPadding(new Insets(32));
        right.setAlignment(Pos.TOP_LEFT);
        right.getStyleClass().add("right-panel");

        Label title = new Label("Welcome Back");
        title.getStyleClass().add("form-title");

        Label subtitle = new Label("Please enter your credentials to access the management system.");
        subtitle.getStyleClass().add("form-subtitle");

        VBox header = new VBox(6, title, subtitle);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("text-input");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("text-input");

        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.getStyleClass().add("checkbox");

        Hyperlink forgotPassword = new Hyperlink("Forgot password?");
        forgotPassword.getStyleClass().add("link");

        HBox options = new HBox(10, rememberMe, spacer(), forgotPassword);
        options.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(options.getChildren().get(1), Priority.ALWAYS);

        Label statusLabel = new Label("");
        statusLabel.getStyleClass().add("status-label");

        Button loginBtn = new Button("Sign in");
        loginBtn.setDefaultButton(true);
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.getStyleClass().add("btn-primary");

        Button exitBtn = new Button("Exit");
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn.getStyleClass().add("btn-secondary");

        // Login button action
        loginBtn.setOnAction(e -> handleLogin(emailField, passwordField, statusLabel));

        // Exit button action
        exitBtn.setOnAction(e -> System.exit(0));

        VBox form = new VBox(12,
                header,
                new Separator(),
                emailField,
                passwordField,
                options,
                statusLabel,
                loginBtn,
                exitBtn
        );
        form.setFillWidth(true);

        right.getChildren().add(form);

        return right;
    }

    private void handleLogin(TextField emailField, PasswordField passwordField, Label statusLabel) {
        String email = safeTrim(emailField.getText());
        String password = passwordField.getText() == null ? "" : passwordField.getText();

        // Debug: Print what was entered
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Email entered: '" + email + "'");
        System.out.println("Password entered: '" + password + "'");
        System.out.println("Email length: " + email.length());
        System.out.println("Password length: " + password.length());

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both email and password.");
            System.out.println("Login failed: Empty fields");
            return;
        }

        // Debug: Print all admins in the system
        System.out.println("\n=== CHECKING ADMINS IN SYSTEM ===");
        int adminCount = 0;
        for (Employee emp : hotel.getAllEmployees()) {
            System.out.println("Employee: " + emp.getName() + " - " + emp.getClass().getSimpleName());
            if (emp instanceof Admin) {
                Admin a = (Admin) emp;
                adminCount++;
                System.out.println("  Admin #" + adminCount);
                System.out.println("  Email: '" + a.getEmail() + "'");
                System.out.println("  Email match: " + a.getEmail().equalsIgnoreCase(email));
                System.out.println("  Password verify: " + a.verifyPassword(password));
            }
        }
        System.out.println("Total admins found: " + adminCount);

        // Find admin in hotel's employee list
        Admin admin = findAdmin(email, password);

        if (admin != null) {
            System.out.println("Login successful for: " + admin.getName());
            statusLabel.setText("");
            navigator.showDashboard(admin);
        } else {
            System.out.println("Login failed: No matching admin found");
            statusLabel.setText("Invalid credentials. Please try again.");
            passwordField.clear();
        }
        System.out.println("===================\n");
    }

    private Admin findAdmin(String email, String password) {
        for (Employee employee : hotel.getAllEmployees()) {
            if (employee instanceof Admin) {
                Admin admin = (Admin) employee;
                if (admin.getEmail().equalsIgnoreCase(email) && 
                    admin.verifyPassword(password)) {
                    return admin;
                }
            }
        }
        return null;
    }

    private static Region spacer() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }

    private static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }
}