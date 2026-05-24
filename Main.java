
import controller.MountainController;
import controller.StudentController;
import java.util.List;
import java.util.Scanner;
import entities.Mountain;
import entities.Student;
import util.Validator;
import view.View;

public class Main {
    
    private final StudentController stCon = new StudentController();        //manager student
    private final MountainController mtCon = new MountainController();      //manager mountain 
    private final View view = new View(new Scanner(System.in));             //scanner
    private boolean isChanged = false;                          //has anything changed ?

    public static void main(String[] args) {
        Main main = new Main();
        while (true) {
            try {
                int choice = main.view.showMainMenu();
                switch (choice) {
                    case 1:
                        main.newRegistration();
                        break;
                    case 2:
                        main.updateRegistration();
                        break;
                    case 3:
                        main.displayAll();
                        break;
                    case 4:
                        main.deleteRegistration();
                        break;
                    case 5:
                        main.searchByName();
                        break;
                    case 6:
                        main.filterByCampus();
                        break;
                    case 7:
                        main.statistics();
                        break;
                    case 8:
                        main.save();
                        break;
                    case 9:
                        main.exit();
                        break;
                    default:
                        main.view.showMessage("This function is not available.");
                }
            } catch (Exception e) {
                main.view.showMessage("Invalid input. Please try again.");
            }
        }
    }

    private void newRegistration() {
        String id;
        while (true) {
            id = view.readString("Enter Student ID: ");
            if (Validator.validStudentId(id)) {
                if (stCon.findByCode(id) == null) {
                    break;
                }
                view.showMessage("ID already exists!");
            } else {
                view.showMessage("Invalid ID format (SE123456)!");
            }
        }

        String name = view.readString("Enter Name: ");
        while (!Validator.validName(name)) {
            name = view.readString("Invalid! Name must have 2-20 chars(a-z): ");
        }
        name = Validator.titleCase(name);

        String phone = view.readString("Enter Phone: ");
        while (!Validator.validPhone(phone)) {
            phone = view.readString("Invalid! Phone must have 10 digits and belong to VietNam : ");
        }

        String email = view.readString("Enter Email: ");
        while (!Validator.validEmail(email)) {
            email = view.readString("Invalid email format! Ex: (example@.com): ");
        }

        view.showMountains(mtCon.getAll());
        String mCode = view.readString("Enter Mountain Code: ").toUpperCase();
        while (mtCon.findByCode(mCode) == null) {
            mCode = view.readString("Not found! Try again mountain Code: ");
            
        }
        
             
        //defaut fee = 6.000.000
        double fee = Validator.DEFAULT_FEE;
        //check for discount
        if (Validator.isViettelOrVNPT(phone)) {
            fee = fee * 0.65;
            view.showMessage("Discount 35% applied!");
        }

        stCon.addRegistration(new Student(id, name, phone, email, mCode, fee));
        isChanged = true;
        view.showMessage("Registration successful!");
    }

    private void updateRegistration() {
        String id = view.readString("Enter Student ID to update: ");
        Student s = stCon.findByCode(id);
        if (s == null) {
            view.showMessage("This student has not registered yet.");
            return;
        }

        String name = view.readStringAllowEmpty("New Name (Current: " + s.getName() + "): ");
        if (!name.isEmpty()) {
            while (!Validator.validName(name)) {
                name = view.readString("Invalid! Name 2-20 chars (or Enter to skip): ");
                if (name.isEmpty()) {
                    break;
                }
            }
            if (!name.isEmpty()) {
                name = Validator.titleCase(name);
                s.setName(name);
            }
        }

        String phone = view.readStringAllowEmpty("New Phone (Current: " + s.getPhone() + "): ");
        if (!phone.isEmpty()) {
            while (!Validator.validPhone(phone)) {
                phone = view.readString("Invalid! 10 digits and belong to VietNam (or Enter to skip): ");
                if (phone.isEmpty()) {
                    break;
                }
            }
            if (!phone.isEmpty()) {
                s.setPhone(phone);
                double baseFee = Validator.DEFAULT_FEE;
                if (Validator.isViettelOrVNPT(phone)) {
                    baseFee = baseFee * 0.65;
                    view.showMessage("Discount 35% applied based on new phone!");
                }
                s.setFee(baseFee);
            }
        }

        String email = view.readStringAllowEmpty("New Email (Current: " + s.getEmail() + "): ");
        if (!email.isEmpty()) {
            while (!Validator.validEmail(email)) {
                email = view.readString("Invalid format (or Enter to skip): ");
                if (email.isEmpty()) {
                    break;
                }
            }
            if (!email.isEmpty()) {
                s.setEmail(email);
            }
        }

        view.showMountains(mtCon.getAll());
        String mCode = view.readStringAllowEmpty("New Mountain Code (Current: " + s.getMountainCode() + "): ").toUpperCase();
        
        if (!mCode.isEmpty()) {
            while (mtCon.findByCode(mCode) == null) {
                mCode = view.readString("Not found! Try again (or Enter to skip): ");
                if (mCode.isEmpty()) {
                    break;
                }
            }
            if (!mCode.isEmpty()) {
                s.setMountainCode(mCode);
            }
        }

        isChanged = true;
        view.showMessage("Update successful!");
    }

    private void deleteRegistration() {
        String id = view.readString("Enter ID to delete: ");
        Student s = stCon.findByCode(id);
        if (s != null) {
            System.out.println("\n--- Student Information ---");
            System.out.println("ID: " + s.getId());
            System.out.println("Name: " + s.getName());
            System.out.println("Phone: " + s.getPhone());
            System.out.println("Email: " + s.getEmail());
            System.out.println("Mountain: " + s.getMountainCode());
            System.out.println("Fee: " + String.format("%,.0f", s.getFee()));

            String confirm = view.readString("Are you sure you want to delete this registration? (Y/N): ");
            if (confirm.equalsIgnoreCase("Y")) {
                stCon.deleteRegistration(id);
                isChanged = true;
                view.showMessage("Deleted successfully!");
            } else {
                view.showMessage("Delete canceled.");
            }
        } else {
            view.showMessage("Student ID not found.");
        }
    }

    private void displayAll() {
        List<Student> list = stCon.getAll();
        if (list.isEmpty()) {
            view.showMessage("No registrations found.");
        } else {
            displayStudentTable(list);
        }
    }

    private void searchByName() {
        String name = view.readString("Enter name to search: ");
        List<Student> result = stCon.searchByName(name);
        if (result.isEmpty()) {
            view.showMessage("No results found.");
        } else {
            displayStudentTable(result);
        }
    }

    private void filterByCampus() {
        String campus = view.readString("Enter Campus Code (SE, HE, DE, QE, CE): ");
        List<Student> result = stCon.filterByCampus(campus);
        if (result.isEmpty()) {
            view.showMessage("No results found for this campus.");
        } else {
            displayStudentTable(result);
        }
    }

    private void statistics() {
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-8s | %-25s | %-10s | %-15s\n", "Code", "Mountain", "Count", "Total Fee");
        System.out.println("---------------------------------------------------------");
        for (Mountain m : mtCon.getAll()) {
            int count = stCon.countStudentsByMountain(m.getCode());
            if (count > 0) {
                System.out.printf("%-8s | %-25s | %10d | %,15.0f\n",m.getCode(), m.getName(), count, stCon.getTotalFeeByMountain(m.getCode()));
            }
        }
    }

    private void save() {
        if (stCon.saveToFile()) {
            isChanged = false;
            view.showMessage("Data saved successfully.");
        }
    }

    private void exit() {
        if (isChanged) {
            String confirm = view.readString("WARNING!!\nYou have unsaved changes. Save before exiting? (Y/N): ");
            if (confirm.equalsIgnoreCase("Y")) {
                save();
            }
        }
        view.showMessage("Exiting program...");
        System.exit(0);
    }

    //display list
    private void displayStudentTable(List<Student> list) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-18s | %-12s | %-8s | %-12s\n", "ID", "Name", "Phone", "M.Code", "Fee");
        System.out.println("--------------------------------------------------------------------------------");
        for (Student s : list) {
            System.out.printf("%-10s | %-18s | %-12s | %-8s | %,.0f\n", s.getId(), s.getName(), s.getPhone(), s.getMountainCode(), s.getFee());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
}
