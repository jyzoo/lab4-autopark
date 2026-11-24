
package autopark;

// Класс легкового автомобиля
public class PassengerCar extends LandVehicle {

    private int passengerCapacity;

    public PassengerCar(String vin, String brand, String model,
                        int year, double mileage, String fuelType,
                        int passengerCapacity) {

        super(vin, brand, model, year, mileage, fuelType);
        setPassengerCapacity(passengerCapacity);
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        if (passengerCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Количество пассажиров должно быть положительным");
        }
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public String getVehicleType() {
        return "Легковой автомобиль";
    }

    @Override
    public double calculateMaintenanceCost() {
        return 5000 + getMileage() * 0.5;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Количество пассажиров: " + passengerCapacity);
        System.out.println("Стоимость ТО: " + calculateMaintenanceCost() + " руб.");
    }
}
