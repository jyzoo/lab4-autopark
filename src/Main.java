
import autopark.*;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// Главный класс программы
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AutoPark autoPark = new AutoPark();

    public static void main(String[] args) {

        // Добавляем демонстрационные данные
        initializeData();

        System.out.println("======================================");
        System.out.println("       СИСТЕМА УПРАВЛЕНИЯ АВТОПАРКОМ");
        System.out.println("======================================");

        boolean running = true;

        while (running) {

            showMenu();

            int choice = readInt("Выберите действие: ");

            try {
                switch (choice) {

                    case 1:
                        addVehicle();
                        break;

                    case 2:
                        addDriver();
                        break;

                    case 3:
                        autoPark.showAllVehicles();
                        break;

                    case 4:
                        autoPark.showAllDrivers();
                        break;

                    case 5:
                        assignDriver();
                        break;

                    case 6:
                        registerTrip();
                        break;

                    case 7:
                        autoPark.showAllTrips();
                        break;

                    case 8:
                        sendToMaintenance();
                        break;

                    case 9:
                        completeMaintenance();
                        break;

                    case 10:
                        autoPark.showAssignments();
                        break;

                    case 0:
                        running = false;
                        System.out.println("Программа завершена.");
                        break;

                    default:
                        System.out.println("Неверный пункт меню!");
                }

            } catch (AutoParkException | IllegalArgumentException e) {
                System.out.println("ОШИБКА: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Непредвиденная ошибка: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nНажмите Enter для продолжения...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // Отображение меню
    private static void showMenu() {

        System.out.println("\n========== ГЛАВНОЕ МЕНЮ ==========");
        System.out.println("1. Добавить автомобиль");
        System.out.println("2. Добавить водителя");
        System.out.println("3. Просмотреть автомобили");
        System.out.println("4. Просмотреть водителей");
        System.out.println("5. Назначить водителя");
        System.out.println("6. Зарегистрировать поездку");
        System.out.println("7. История поездок");
        System.out.println("8. Отправить автомобиль на ТО");
        System.out.println("9. Завершить техническое обслуживание");
        System.out.println("10. Просмотреть назначения");
        System.out.println("0. Выход");
        System.out.println("==================================");
    }

    // Инициализация демонстрационных данных
    private static void initializeData() {

        try {
            Vehicle car = new PassengerCar(
                    "VIN001", "Toyota", "Camry",
                    2022, 45000, "Бензин", 4
            );

            Vehicle truck = new Truck(
                    "VIN002", "Volvo", "FH",
                    2021, 120000, "Дизель", 20
            );

            autoPark.addVehicle(car);
            autoPark.addVehicle(truck);

            Set<String> categories1 = new HashSet<>();
            categories1.add("B");
            categories1.add("C");

            Set<String> categories2 = new HashSet<>();
            categories2.add("B");

            Driver driver1 = new Driver(
                    1, "Иванов Иван Иванович",
                    categories1, 10
            );

            Driver driver2 = new Driver(
                    2, "Петров Петр Петрович",
                    categories2, 5
            );

            autoPark.addDriver(driver1);
            autoPark.addDriver(driver2);

            System.out.println("\nДемонстрационные данные загружены!");

        } catch (AutoParkException e) {
            System.out.println("Ошибка загрузки данных: " + e.getMessage());
        }
    }

    // Добавление автомобиля
    private static void addVehicle() throws AutoParkException {

        System.out.println("\n=== ДОБАВЛЕНИЕ АВТОМОБИЛЯ ===");

        System.out.println("1. Легковой автомобиль");
        System.out.println("2. Грузовой автомобиль");

        int type = readInt("Выберите тип: ");

        String vin = readString("VIN: ");
        String brand = readString("Марка: ");
        String model = readString("Модель: ");
        int year = readInt("Год выпуска: ");
        double mileage = readDouble("Пробег: ");
        String fuel = readString("Тип топлива: ");

        Vehicle vehicle;

        if (type == 1) {

            int capacity = readInt("Количество пассажиров: ");

            vehicle = new PassengerCar(
                    vin, brand, model, year,
                    mileage, fuel, capacity
            );

        } else if (type == 2) {

            double capacity = readDouble("Грузоподъемность (тонн): ");

            vehicle = new Truck(
                    vin, brand, model, year,
                    mileage, fuel, capacity
            );

        } else {
            System.out.println("Неверный тип автомобиля!");
            return;
        }

        autoPark.addVehicle(vehicle);
    }

    // Добавление водителя
    private static void addDriver() throws AutoParkException {

        System.out.println("\n=== ДОБАВЛЕНИЕ ВОДИТЕЛЯ ===");

        int id = readInt("ID водителя: ");
        String name = readString("ФИО: ");
        int experience = readInt("Стаж работы: ");

        Set<String> categories = new HashSet<>();

        System.out.println("Введите категории прав через пробел (B C D): ");

        String[] input = readString("Категории: ").toUpperCase().split("\\s+");

        for (String category : input) {
            categories.add(category);
        }

        Driver driver = new Driver(
                id, name, categories, experience
        );

        autoPark.addDriver(driver);
    }

    // Назначение водителя
    private static void assignDriver() throws AutoParkException {

        System.out.println("\n=== НАЗНАЧЕНИЕ ВОДИТЕЛЯ ===");

        String vin = readString("Введите VIN автомобиля: ");
        int driverId = readInt("Введите ID водителя: ");

        autoPark.assignDriver(vin, driverId);
    }

    // Регистрация поездки
    private static void registerTrip() throws AutoParkException {

        System.out.println("\n=== РЕГИСТРАЦИЯ ПОЕЗДКИ ===");

        int tripId = readInt("Номер поездки: ");
        String vin = readString("VIN автомобиля: ");
        String route = readString("Маршрут: ");
        double distance = readDouble("Расстояние (км): ");

        autoPark.registerTrip(
                tripId, vin, route, distance
        );
    }

    // Отправка автомобиля на ТО
    private static void sendToMaintenance() throws AutoParkException {

        System.out.println("\n=== ТЕХНИЧЕСКОЕ ОБСЛУЖИВАНИЕ ===");

        String vin = readString("VIN автомобиля: ");

        autoPark.sendToMaintenance(vin);
    }

    // Завершение ТО
    private static void completeMaintenance() throws AutoParkException {

        System.out.println("\n=== ЗАВЕРШЕНИЕ ТО ===");

        String vin = readString("VIN автомобиля: ");

        autoPark.completeMaintenance(vin);
    }

    // Безопасный ввод строки
    private static String readString(String message) {

        System.out.print(message);
        return scanner.nextLine().trim();
    }

    // Безопасный ввод целого числа
    private static int readInt(String message) {

        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите целое число.");
            }
        }
    }

    // Безопасный ввод вещественного числа
    private static double readDouble(String message) {

        while (true) {
            try {
                System.out.print(message);

                String value = scanner.nextLine()
                        .trim()
                        .replace(',', '.');

                return Double.parseDouble(value);

            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите число.");
            }
        }
    }
}
