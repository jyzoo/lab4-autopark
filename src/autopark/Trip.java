
package autopark;

import java.time.LocalDate;

// Класс поездки
public class Trip {

    private int id;
    private Vehicle vehicle;
    private Driver driver;
    private String route;
    private double distance;
    private LocalDate date;

    // Конструктор
    public Trip(int id, Vehicle vehicle, Driver driver,
                String route, double distance, LocalDate date) {

        setId(id);
        setVehicle(vehicle);
        setDriver(driver);
        setRoute(route);
        setDistance(distance);
        setDate(date);
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public String getRoute() {
        return route;
    }

    public double getDistance() {
        return distance;
    }

    public LocalDate getDate() {
        return date;
    }

    // Сеттеры
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID поездки должен быть положительным");
        }
        this.id = id;
    }

    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Транспорт не указан");
        }
        this.vehicle = vehicle;
    }

    public void setDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Водитель не указан");
        }
        this.driver = driver;
    }

    public void setRoute(String route) {
        if (route == null || route.trim().isEmpty()) {
            throw new IllegalArgumentException("Маршрут не может быть пустым");
        }
        this.route = route;
    }

    public void setDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Расстояние должно быть положительным");
        }
        this.distance = distance;
    }

    public void setDate(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Некорректная дата поездки");
        }
        this.date = date;
    }

    // Вывод информации о поездке
    public void displayInfo() {
        System.out.println("Номер поездки: " + id);
        System.out.println("Автомобиль: " +
                vehicle.getBrand() + " " + vehicle.getModel());
        System.out.println("Водитель: " + driver.getFullName());
        System.out.println("Маршрут: " + route);
        System.out.println("Расстояние: " + distance + " км");
        System.out.println("Дата: " + date);
    }
}
