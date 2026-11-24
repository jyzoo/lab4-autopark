
package autopark;

// Абстрактный базовый класс транспорта
public abstract class Vehicle {

    // Приватные поля
    private String vin;
    private String brand;
    private String model;
    private int year;
    private double mileage;
    private VehicleStatus status;

    // Конструктор
    public Vehicle(String vin, String brand, String model,
                   int year, double mileage) {

        setVin(vin);
        setBrand(brand);
        setModel(model);
        setYear(year);
        setMileage(mileage);

        this.status = VehicleStatus.AVAILABLE;
    }

    // Геттеры
    public String getVin() {
        return vin;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getMileage() {
        return mileage;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    // Сеттеры с проверкой данных
    public void setVin(String vin) {
        if (vin == null || vin.trim().isEmpty()) {
            throw new IllegalArgumentException("VIN не может быть пустым");
        }
        this.vin = vin;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Марка не может быть пустой");
        }
        this.brand = brand;
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Модель не может быть пустой");
        }
        this.model = model;
    }

    public void setYear(int year) {
        if (year < 1900 || year > 2026) {
            throw new IllegalArgumentException("Некорректный год выпуска");
        }
        this.year = year;
    }

    public void setMileage(double mileage) {
        if (mileage < 0) {
            throw new IllegalArgumentException("Пробег не может быть отрицательным");
        }
        this.mileage = mileage;
    }

    public void setStatus(VehicleStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Статус не может быть пустым");
        }
        this.status = status;
    }

    // Добавление пробега
    public void addMileage(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Расстояние должно быть положительным");
        }
        this.mileage += distance;
    }

    // Абстрактные методы для полиморфизма
    public abstract String getVehicleType();

    public abstract double calculateMaintenanceCost();

    // Вывод информации о транспорте
    public void displayInfo() {
        System.out.println("Тип: " + getVehicleType());
        System.out.println("VIN: " + vin);
        System.out.println("Марка: " + brand);
        System.out.println("Модель: " + model);
        System.out.println("Год выпуска: " + year);
        System.out.println("Пробег: " + mileage + " км");
        System.out.println("Статус: " + status);
    }
}
