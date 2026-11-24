
package autopark;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Главный класс управления автопарком
public class AutoPark {

    // Коллекции для хранения данных
    private List<Vehicle> vehicles;
    private Map<Integer, Driver> drivers;
    private List<Trip> trips;
    private Set<String> vehicleVins;

    // Назначенные водители: VIN автомобиля -> ID водителя
    private Map<String, Integer> assignments;

    // Конструктор
    public AutoPark() {
        vehicles = new ArrayList<>();
        drivers = new HashMap<>();
        trips = new ArrayList<>();
        vehicleVins = new HashSet<>();
        assignments = new HashMap<>();
    }

    // Геттеры с защитой коллекций от внешнего изменения
    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    public Map<Integer, Driver> getDrivers() {
        return new HashMap<>(drivers);
    }

    public List<Trip> getTrips() {
        return new ArrayList<>(trips);
    }

    public Set<String> getVehicleVins() {
        return new HashSet<>(vehicleVins);
    }

    public Map<String, Integer> getAssignments() {
        return new HashMap<>(assignments);
    }

    // Добавление автомобиля
    public void addVehicle(Vehicle vehicle) throws AutoParkException {

        if (vehicle == null) {
            throw new AutoParkException("Автомобиль не указан");
        }

        if (vehicleVins.contains(vehicle.getVin())) {
            throw new AutoParkException("Автомобиль с таким VIN уже существует");
        }

        vehicles.add(vehicle);
        vehicleVins.add(vehicle.getVin());

        System.out.println("Автомобиль успешно добавлен!");
    }

    // Добавление водителя
    public void addDriver(Driver driver) throws AutoParkException {

        if (driver == null) {
            throw new AutoParkException("Водитель не указан");
        }

        if (drivers.containsKey(driver.getId())) {
            throw new AutoParkException("Водитель с таким ID уже существует");
        }

        drivers.put(driver.getId(), driver);

        System.out.println("Водитель успешно добавлен!");
    }

    // Поиск автомобиля по VIN
    public Vehicle findVehicle(String vin) throws AutoParkException {

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVin().equals(vin)) {
                return vehicle;
            }
        }

        throw new AutoParkException("Автомобиль не найден");
    }

    // Назначение водителя на автомобиль
    public void assignDriver(String vin, int driverId)
            throws AutoParkException {

        Vehicle vehicle = findVehicle(vin);
        Driver driver = drivers.get(driverId);

        if (driver == null) {
            throw new AutoParkException("Водитель не найден");
        }

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new VehicleNotAvailableException(
                    "Автомобиль недоступен для назначения");
        }

        if (assignments.containsKey(vin)) {
            throw new AutoParkException(
                    "На автомобиль уже назначен водитель");
        }

        assignments.put(vin, driverId);

        System.out.println("Водитель " + driver.getFullName()
                + " назначен на автомобиль "
                + vehicle.getBrand() + " " + vehicle.getModel());
    }

    // Регистрация поездки
    public void registerTrip(int tripId, String vin,
                             String route, double distance)
            throws AutoParkException {

        if (distance <= 0) {
            throw new AutoParkException(
                    "Расстояние поездки должно быть положительным");
        }

        Vehicle vehicle = findVehicle(vin);

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new VehicleNotAvailableException(
                    "Автомобиль недоступен для поездки");
        }

        Integer driverId = assignments.get(vin);

        if (driverId == null) {
            throw new AutoParkException(
                    "На автомобиль не назначен водитель");
        }

        Driver driver = drivers.get(driverId);

        // Проверка категории водительских прав
        if (vehicle instanceof Truck && !driver.hasCategory("C")) {
            throw new AutoParkException(
                    "Для управления грузовым автомобилем нужна категория C");
        }

        if (vehicle instanceof PassengerCar && !driver.hasCategory("B")) {
            throw new AutoParkException(
                    "Для управления легковым автомобилем нужна категория B");
        }

        // Проверка повторяющегося ID поездки
        for (Trip trip : trips) {
            if (trip.getId() == tripId) {
                throw new AutoParkException(
                        "Поездка с таким ID уже существует");
            }
        }

        // Создание поездки
        Trip trip = new Trip(
                tripId,
                vehicle,
                driver,
                route,
                distance,
                LocalDate.now()
        );

        vehicle.setStatus(VehicleStatus.IN_USE);

        // Учет пробега
        vehicle.addMileage(distance);

        trips.add(trip);

        // После завершения поездки автомобиль снова свободен
        vehicle.setStatus(VehicleStatus.AVAILABLE);

        System.out.println("Поездка успешно зарегистрирована!");
        System.out.println("Пробег автомобиля увеличен на "
                + distance + " км");
    }

    // Отправка автомобиля на техническое обслуживание
    public void sendToMaintenance(String vin) throws AutoParkException {

        Vehicle vehicle = findVehicle(vin);

        if (vehicle.getStatus() == VehicleStatus.IN_USE) {
            throw new VehicleNotAvailableException(
                    "Нельзя отправить автомобиль, находящийся в рейсе");
        }

        if (vehicle.getStatus() == VehicleStatus.MAINTENANCE) {
            throw new AutoParkException(
                    "Автомобиль уже находится на обслуживании");
        }

        vehicle.setStatus(VehicleStatus.MAINTENANCE);

        // Снимаем назначение водителя
        assignments.remove(vin);

        System.out.println("Автомобиль отправлен на техническое обслуживание");
        System.out.println("Стоимость ТО: "
                + vehicle.calculateMaintenanceCost() + " руб.");
    }

    // Завершение технического обслуживания
    public void completeMaintenance(String vin) throws AutoParkException {

        Vehicle vehicle = findVehicle(vin);

        if (vehicle.getStatus() != VehicleStatus.MAINTENANCE) {
            throw new AutoParkException(
                    "Автомобиль не находится на обслуживании");
        }

        vehicle.setStatus(VehicleStatus.AVAILABLE);

        System.out.println("Техническое обслуживание завершено!");
        System.out.println("Автомобиль снова доступен");
    }

    // Вывод всех автомобилей
    public void showAllVehicles() {

        System.out.println("\n========== АВТОПАРК ==========");

        if (vehicles.isEmpty()) {
            System.out.println("Автомобилей пока нет");
            return;
        }

        for (Vehicle vehicle : vehicles) {
            vehicle.displayInfo();
            System.out.println("------------------------------");
        }
    }

    // Вывод всех водителей
    public void showAllDrivers() {

        System.out.println("\n========== ВОДИТЕЛИ ==========");

        if (drivers.isEmpty()) {
            System.out.println("Водителей пока нет");
            return;
        }

        for (Driver driver : drivers.values()) {
            driver.displayInfo();
            System.out.println("------------------------------");
        }
    }

    // Вывод истории поездок
    public void showAllTrips() {

        System.out.println("\n========== ИСТОРИЯ ПОЕЗДОК ==========");

        if (trips.isEmpty()) {
            System.out.println("Поездок пока нет");
            return;
        }

        for (Trip trip : trips) {
            trip.displayInfo();
            System.out.println("------------------------------");
        }
    }

    // Вывод назначенных водителей
    public void showAssignments() {

        System.out.println("\n========== НАЗНАЧЕНИЯ ==========");

        if (assignments.isEmpty()) {
            System.out.println("Назначений пока нет");
            return;
        }

        for (Map.Entry<String, Integer> entry : assignments.entrySet()) {

            Vehicle vehicle = null;

            try {
                vehicle = findVehicle(entry.getKey());
            } catch (AutoParkException e) {
                System.out.println(e.getMessage());
                continue;
            }

            Driver driver = drivers.get(entry.getValue());

            System.out.println(
                    vehicle.getBrand() + " " + vehicle.getModel()
                            + " — " + driver.getFullName()
            );
        }
    }
}
