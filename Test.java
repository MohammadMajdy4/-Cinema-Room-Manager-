import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        start();
    }



    // getting user input
    public static int getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }



    // starting massage and important declarations :
    public static void start() {
        System.out.println("Enter the number of rows:");
        int rows = getInput();
        System.out.println("Enter the number of seats in each row:");
        int seats = getInput();

        char[][] cinemaSeats = new char[rows + 1][seats + 1];
        cinema(rows, seats, cinemaSeats);
        int[] purchaseTickets = {0};
        int[] currentIncome = {0};
        run(rows, seats, cinemaSeats, purchaseTickets, currentIncome);
    }


    // Running the program
    public static void run(int rows, int seats, char[][] cinema, int[] purchased, int[] income) {

        boolean isRunning = true;
        while (isRunning) {
            menuMassage();
            isRunning = menu(rows, seats, cinema, purchased, income);
        }

    }




    // menu massage
    public static void menuMassage() {
        System.out.println(
                "1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");
    }



    public static boolean menu(int rows, int seats, char[][] cinema, int[] purchased, int[] income) {
        switch (getInput()) {
            case 1:
                printingCinemaSeats(rows, seats, cinema);
                break;
            case 2:
                while (!buyATicket(rows, seats, cinema, purchased, income)) {/*do nothing here*/}
                break;
            case 3:
                statistics(rows, seats, purchased, income);
                break;
            case 0:
                return exit();
        }
        return true;
    }


    // cinema with empty seats
    public static void cinema(int rows, int seats, char[][] cinemaSeats) {

        for (int i = 0; i <= seats; i++) {
            cinemaSeats[0][i] = i == 0 ? ' ' : (char)(i + 48) ;
        }
        for (int i = 1; i <= rows; i++) {
            for (int j = 0; j <= seats; j++) {
                cinemaSeats[i][j] = j == 0 ? (char)(i + 48) : 'S';
            }
        }

    }




    public static void updatingReservedSeats(int row, int seat, char[][] updatingCinemaSeat, int[] purchased) {
        updatingCinemaSeat[row][seat] = 'B';
        purchased[0]++;
    }




    // Exit
    public static boolean exit() {
        return false;
    }




    public static void statistics(int rows, int seats, int[] purchased, int[]income) {
        int sold = purchased[0];
        System.out.println("Number of purchased tickets: " + sold);
        System.out.println("Percentage: " + String.format("%.2f",percentageOfPurchasedTickets(rows, seats, sold))+ "%");
        System.out.println("Current income: $" + income[0]);
        System.out.println("Total income: $" + TotalIncome(rows,seats));

    }




    public static double percentageOfPurchasedTickets(int rows, int seats, int purchased) {
        return Math.round((1.0 * purchased / (rows * seats * 1.0)) * 100.0 * 100.0) / 100.0;
    }



    // return if cinema seats more than 60 seats
    public static int checkingCapacityOfCinemaHall(int rows, int seats) {
        if (rows * seats > 60) { return 1; }
        else { return 0; }
    }




    // reserving seat
    public static boolean buyATicket(int rows, int seats, char[][] cinemaSeats, int[] purchased, int[] income) {

        System.out.println("Enter a row number:");
        int row = getInput();

        System.out.println("Enter a seat number in that row:");
        int seat = getInput();

        if (row < 0 || row > rows || seat < 0 || seat > seats) {
            System.out.println("Wrong input!");
            return false;
        } else {

            if (isEmptySeat(row, seat, cinemaSeats)) {
                System.out.printf("Ticket price: $%d\n", cinemaSeatPrice(row, rows, seats));
                updatingReservedSeats(row, seat, cinemaSeats, purchased);
                income[0] += cinemaSeatPrice(row, rows, seats);
                return true;
            } else {
                System.out.println("That ticket has already been purchased!");
                return false;
            }
        }

    }





    public static boolean isEmptySeat(int row, int seat, char[][] cinemaSeat) {
        if (cinemaSeat[row][seat] == 'B') {
            return false;
        }
        return true;
    }



    // returning the price of ticket for the reserved seat
    public static int cinemaSeatPrice(int row, int cinemaRows, int cinemaSeats) {
        if(checkingCapacityOfCinemaHall(cinemaRows, cinemaSeats) == 1) {
            if (row <= cinemaRows / 2) {
                return 10;
            } else {
                return 8;
            }
        } else {
            return 10;
        }
    }




    // printing cinema seats :
    public static void printingCinemaSeats(int rows, int seats, char[][] printCinema) {
        System.out.printf("Cinema:\n");
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seats; j++) {
                System.out.print(printCinema[i][j] + " ");
            }
            System.out.println();
        }
    }




    public static int TotalIncome(int rows, int seats) {

        if (checkingCapacityOfCinemaHall(rows, seats) == 1) {
            // check odd rows : odd & 1 = 1 , even & 1 = 0
            if ((rows & 1) == 0) {
                return (rows / 2) * seats * 10 + (rows / 2) * seats * 8;
            } else {
                return (rows / 2) * seats * 10 + (rows / 2 + 1) * seats * 8;
            }

        } else {
            return rows * seats * 10;
        }

    }

}