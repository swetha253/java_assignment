package temp;
class Driver {
    private String name;
    private String carModel;
    private double rating;
    private double distanceFromCustomer;

    public Driver(String name, String carModel, double rating, double distanceFromCustomer) {
        this.name = name;
        this.carModel = carModel;
        this.rating = rating;
        this.distanceFromCustomer = distanceFromCustomer;
    }

    public String getName() {
        return name;
    }

    public String getCarModel() {
        return carModel;
    }

    public double getRating() {
        return rating;
    }

    public double getDistanceFromCustomer() {
        return distanceFromCustomer;
    }
}

class Ride {
    private double distance;
    private String carRequested;

    public Ride(double distance, String carRequested) {
        this.distance = distance;
        this.carRequested = carRequested;
    }

    public double getDistance() {
        return distance;
    }

    public String getCarRequested() {
        return carRequested;
    }
}

class RentARide {
    private Driver[] drivers;

    public RentARide(Driver[] drivers) {
        this.drivers = drivers;
    }

    public String findDriverForRide(Ride ride) {
        double shortestDistance = Double.MAX_VALUE;
        Driver closestDriver = null;
        boolean specificCarRequested = (ride.getCarRequested() != null && !ride.getCarRequested().isEmpty());
        boolean carFound = false;

        for (Driver driver : drivers) {
            if (driver.getRating() < 4) {
                continue;
            }

            if (specificCarRequested) {
                if (driver.getCarModel().equalsIgnoreCase(ride.getCarRequested())) {
                    carFound = true;
                    if (driver.getDistanceFromCustomer() < shortestDistance) {
                        shortestDistance = driver.getDistanceFromCustomer();
                        closestDriver = driver;
                    }
                }
            } else {
                if (driver.getDistanceFromCustomer() < shortestDistance) {
                    shortestDistance = driver.getDistanceFromCustomer();
                    closestDriver = driver;
                }
            }
        }

        if (closestDriver != null) {
            if (!specificCarRequested || (specificCarRequested && carFound)) {
                return "Driver " + closestDriver.getName() + " will get you to the destination." +
                        " Your charge will be Rs " + (ride.getDistance() * 8);
            } else {
                return "No driver available for the requested car model. Please select another car.";
            }
        } else {
            return "No suitable driver found for your ride request.";
        }
    }
}





package temp;
import java.util.Scanner;
public class RentApp {
    public static void main(String[] args) {
        Driver[] drivers = {
                new Driver("A", "Sedan", 4, 500),
                new Driver("B", "HatchBack", 4.3, 1000),
                new Driver("C", "5 Seater", 4.8, 200),
                new Driver("D", "Sedan", 4.1, 700),
                new Driver("E", "HatchBack", 4.7, 430)
        };

        Scanner scanner = new Scanner(System.in);

        System.out.print("Customer Ride Distance: ");
        double rideDistance = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Car Requested: ");
        String carRequested = scanner.nextLine();

        scanner.close();

        Ride ride = new Ride(rideDistance, carRequested);

        RentARide rentARide = new RentARide(drivers);
        String result = rentARide.findDriverForRide(ride);
        System.out.println(result);
    }
}

