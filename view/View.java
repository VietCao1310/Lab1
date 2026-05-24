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
    
    //read integer from user
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
    
    
    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
    
    //when update 
    public String readStringAllowEmpty(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
    
    //show success or failure
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
