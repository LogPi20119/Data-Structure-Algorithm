import java.util.Scanner;

public class GasMileage {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < 2; i++) {

            System.out.println("Enter car name, miles, gallons:");

            String car = sc.next();
            double miles = sc.nextDouble();
            double gallons = sc.nextDouble();

            double mpg = miles / gallons;

            System.out.println(car + " MPG: " + mpg);
        }

        sc.close();
    }
}
