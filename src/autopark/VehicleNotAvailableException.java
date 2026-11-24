
package autopark;

// Исключение при попытке использовать занятый автомобиль
public class VehicleNotAvailableException extends AutoParkException {

    public VehicleNotAvailableException(String message) {
        super(message);
    }
}
