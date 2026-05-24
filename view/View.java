package view;

import java.util.List;
import java.util.Scanner;

public class View {

    private final Scanner sc;
    //object input
    public View(Scanner sc) {
        this.sc = sc;
    }

    public int showMainMenu() {

        System.out.println("===== Mountain Hiking Registration =====");

        String[] options = {"New Registration",
            "Update Registration",
            "Display Registered List",
            "Delete Registration",
            "Search Participants by Name",
            "Filter Data by Campus",
            "Statistics by Mountain",
            "Save Data to File",
            "Exit",};

        Menu.showMenu(options);
        return Integer.parseInt(sc.nextLine());
    }

    public void showMountains(List mountains) {
        if (mountains.isEmpty()) {
            System.out.println("No mountains loaded.");
            return;
        }
        for (Object m : mountains) {
            System.out.println(m);
        }
    }
    
    //read input from user
    public int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val < min || val > max) {
                    throw new Exception();
                }
                return val;
            } catch (Exception e) {
                System.out.println("Invalid number, try again!");
            }
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again!");
            }
        }
    }

    public int readInt(String prompt, int oldValue) {
        while (true) {
            try {
                int val;
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                if (line.trim().isEmpty()) {
                    val = oldValue;
                } else {
                    val = Integer.parseInt(line.trim());
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again!");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = Double.parseDouble(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again!");
            }
        }
    }

    public double readDouble(String prompt, double oldValue) {
        while (true) {
            try {
                double val;
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                if (line.trim().isEmpty()) {
                    val = oldValue;
                } else {
                    val = Double.parseDouble(sc.nextLine().trim());
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again!");
            }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public String readStringAllowEmpty(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
