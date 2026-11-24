
package autopark;

import java.util.HashSet;
import java.util.Set;

// Класс водителя
public class Driver {

    private int id;
    private String fullName;
    private Set<String> licenseCategories;
    private int experience;

    public Driver(int id, String fullName,
                  Set<String> licenseCategories, int experience) {

        setId(id);
        setFullName(fullName);
        setLicenseCategories(licenseCategories);
        setExperience(experience);
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<String> getLicenseCategories() {
        return new HashSet<>(licenseCategories);
    }

    public int getExperience() {
        return experience;
    }

    // Сеттеры
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным");
        }
        this.id = id;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("ФИО не может быть пустым");
        }
        this.fullName = fullName;
    }

    public void setLicenseCategories(Set<String> licenseCategories) {
        if (licenseCategories == null || licenseCategories.isEmpty()) {
            throw new IllegalArgumentException("Необходимо указать категории прав");
        }
        this.licenseCategories = new HashSet<>(licenseCategories);
    }

    public void setExperience(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Стаж не может быть отрицательным");
        }
        this.experience = experience;
    }

    // Проверка наличия категории прав
    public boolean hasCategory(String category) {
        return licenseCategories.contains(category);
    }

    // Вывод информации о водителе
    public void displayInfo() {
        System.out.println("ID водителя: " + id);
        System.out.println("ФИО: " + fullName);
        System.out.println("Категории прав: " + licenseCategories);
        System.out.println("Стаж: " + experience + " лет");
    }
}
