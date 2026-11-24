
package autopark;

// Абстрактный класс наземного транспорта
public abstract class LandVehicle extends Vehicle {

    private String fuelType;

    public LandVehicle(String vin, String brand, String model,
                       int year, double mileage, String fuelType) {

        super(vin, brand, model, year, mileage);
        setFuelType(fuelType);
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        if (fuelType == null || fuelType.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип топлива не указан");
        }
        this.fuelType = fuelType;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Тип топлива: " + fuelType);
    }
}
