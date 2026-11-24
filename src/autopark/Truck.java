
package autopark;

// Класс грузового автомобиля
public class Truck extends LandVehicle {

    private double loadCapacity;

    public Truck(String vin, String brand, String model,
                 int year, double mileage, String fuelType,
                 double loadCapacity) {

        super(vin, brand, model, year, mileage, fuelType);
        setLoadCapacity(loadCapacity);
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        if (loadCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Грузоподъемность должна быть положительной");
        }
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String getVehicleType() {
        return "Грузовой автомобиль";
    }

    @Override
    public double calculateMaintenanceCost() {
        return 10000 + getMileage() * 1.2;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Грузоподъемность: " + loadCapacity + " тонн");
        System.out.println("Стоимость ТО: " + calculateMaintenanceCost() + " руб.");
    }
}
