import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Dashboard UI for Hotel Management System
 * Pure VIEW class – no business logic
 */
public class DashboardView {

    private final Hotel hotel;
    private final Admin admin;
    private BorderPane root;

    public DashboardView(Hotel hotel, Admin admin) {
        this.hotel = hotel;
        this.admin = admin;
        createView();
    }

    private void createView() {
        root = new BorderPane();
        root.getStyleClass().add("dashboard-root");
        root.setPadding(new Insets(20));

        root.setTop(createHeader());
        root.setCenter(createCharts());
        root.setBottom(createStats());
    }

    /* =========================
       HEADER
       ========================= */
    private Parent createHeader() {
        Label title = new Label("Dashboard");
        title.getStyleClass().add("dashboard-title");

        Label subtitle = new Label("Welcome, " + admin.getName());
        subtitle.getStyleClass().add("dashboard-subtitle");

        VBox header = new VBox(5, title, subtitle);
        header.setPadding(new Insets(0, 0, 20, 0));
        return header;
    }

    /* =========================
       CHART AREA
       ========================= */
    private Parent createCharts() {
        HBox charts = new HBox(30);
        charts.setAlignment(Pos.CENTER);

        charts.getChildren().addAll(
                createOccupancyChart(),
                createInvoiceChart()
        );

        return charts;
    }

    /* Room occupancy bar chart */
    private Parent createOccupancyChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Occupied Rooms");

        BarChart<String, Number> chart =
                new BarChart<>(xAxis, yAxis);
        chart.setTitle("Room Occupancy");
        chart.setPrefSize(450, 300);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        long single = hotel.getAllRooms().stream()
                .filter(r -> r.getRoomType() == Room.RoomType.SINGLE && r.isOccupied())
                .count();

        long doub = hotel.getAllRooms().stream()
                .filter(r -> r.getRoomType() == Room.RoomType.DOUBLE && r.isOccupied())
                .count();

        long suite = hotel.getAllRooms().stream()
                .filter(r -> r.getRoomType() == Room.RoomType.SUITE && r.isOccupied())
                .count();

        series.getData().add(new XYChart.Data<>("Single", single));
        series.getData().add(new XYChart.Data<>("Double", doub));
        series.getData().add(new XYChart.Data<>("Suite", suite));

        chart.getData().add(series);
        return chart;
    }

    /* Invoice pie chart */
    private Parent createInvoiceChart() {
        PieChart chart = new PieChart();
        chart.setTitle("Invoice Status");
        chart.setPrefSize(450, 300);

        long paid = hotel.getAllInvoices().stream()
                .filter(Invoice::isPaid)
                .count();

        long unpaid = hotel.getAllInvoices().size() - paid;

        chart.getData().add(new PieChart.Data("Paid", paid));
        chart.getData().add(new PieChart.Data("Unpaid", unpaid));

        return chart;
    }

    /* =========================
       STATS CARDS
       ========================= */
    private Parent createStats() {
        HBox stats = new HBox(40);
        stats.setAlignment(Pos.CENTER);
        stats.setPadding(new Insets(25, 0, 0, 0));

        stats.getChildren().addAll(
                statCard("Total Rooms", hotel.getAllRooms().size()),
                statCard("Occupied Rooms", hotel.getOccupiedRooms().size()),
                statCard("Total Revenue", "$" + String.format("%.2f", hotel.getTotalRevenue()))
        );

        return stats;
    }

    private VBox statCard(String title, Object value) {
        Label t = new Label(title);
        t.setFont(Font.font(14));

        Label v = new Label(value.toString());
        v.setFont(Font.font(22));

        VBox box = new VBox(5, t, v);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("dashboard-card");

        return box;
    }

    /* =========================
       ROOT ACCESS
       ========================= */
    public Parent getRoot() {
        return root;
    }
}
