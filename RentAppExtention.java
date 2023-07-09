package extention;
class Driver {
    private String name;
    private String carModel;
    private double rating;
    private double distanceFromCustomer;
    private String preferredDestination;

    public Driver(String name, String carModel, double rating, double distanceFromCustomer, String preferredDestination) {
        this.name = name;
        this.carModel = carModel;
        this.rating = rating;
        this.distanceFromCustomer = distanceFromCustomer;
        this.preferredDestination = preferredDestination;
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

    public String getPreferredDestination() {
        return preferredDestination;
    }
}

class Ride {
    private double distance;
    private String carRequested;
    private String destination;

    public Ride(double distance, String carRequested, String destination) {
        this.distance = distance;
        this.carRequested = carRequested;
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public String getCarRequested() {
        return carRequested;
    }

    public String getDestination() {
        return destination;
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
                if (closestDriver.getPreferredDestination().contains(ride.getDestination())) {
                    return "Driver " + closestDriver.getName() + " will take you to the destination." +
                            " Your charge will be Rs " + (ride.getDistance() * 8);
                } else {
                    // Driver with preferred destination cancels the request
                    Driver nextDriver = null;
                    shortestDistance = Double.MAX_VALUE;

                    for (Driver driver : drivers) {
                        if (driver.getRating() < 4 || !driver.getCarModel().equalsIgnoreCase(ride.getCarRequested())) {
                            continue;
                        }

                        if (driver.getDistanceFromCustomer() < shortestDistance) {
                            shortestDistance = driver.getDistanceFromCustomer();
                            nextDriver = driver;
                        }
                    }

                    if (nextDriver != null) {
                        return "Driver " + nextDriver.getName() + " will take you to the destination." +
                                " Your charge will be Rs " + (ride.getDistance() * 8);
                    } else {
                        return "No suitable driver found for your ride request.";
                    }
                }
            } else {
                return "No driver available for the requested car model. Please select another car.";
            }
        } else {
            return "No suitable driver found for your ride request.";
        }
    }
}




package extention;
import java.util.Scanner;
public class RentApp {
    public static void main(String[] args) {
        Driver[] drivers = {
                new Driver("A", "5 Seater", 4, 500, "Gurgaon, Noida, Delhi"),
                new Driver("B", "HatchBack", 4.3, 1000, "Gurgaon"),
                new Driver("C", "5 Seater", 4.8, 200, "Noida"),
                new Driver("D", "Sedan", 4.1, 700, "Noida"),
                new Driver("E", "5 Seater", 4.7, 430, "Delhi")
        };

        Scanner scanner = new Scanner(System.in);

        System.out.print("Customer Ride Distance: ");
        double rideDistance = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Car Requested: ");
        String carRequested = scanner.nextLine();

        System.out.print("Destination: ");
        String destination = scanner.nextLine();

        scanner.close();

        Ride ride = new Ride(rideDistance, carRequested, destination);

        RentARide rentARide = new RentARide(drivers);
        String result = rentARide.findDriverForRide(ride);
        System.out.println(result);
    }
}

