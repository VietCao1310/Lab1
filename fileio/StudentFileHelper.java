package fileio;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import entities.Student;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StudentFileHelper implements IFileReadWrite<Student> {

    private final String FILE_NAME = "src/fileio/registrations.dat";

    @Override
    public List<Student> read() throws Exception {
        File f = new File(FILE_NAME);
           if (!f.exists()) {
               return new ArrayList<>();
           }
           ObjectInputStream ois = null;
           try {
               ois = new ObjectInputStream(
                       new FileInputStream(f));

               return (List<Student>) ois.readObject();

           } finally {
               if (ois != null) {
                   ois.close();
               }
           }
    }

    @Override
    public boolean write(List<Student> list) throws Exception {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(
                        new FileOutputStream(FILE_NAME));

                oos.writeObject(list);

                return true;

            } catch (IOException e) {
                return false;

            } finally {
                if (oos != null) {
                    oos.close();
                }
            }
    }
    
    private String convertToString(Student student) {
        return String.format("%s,%s,%s,%s,%s,%.0f",
                student.getId(),
                student.getName(),
                student.getPhone(),
                student.getEmail(),
                student.getMountainCode(),
                student.getFee());
    }

    
    private Student convertToStudent(String str) {
        try {
            String[] p = str.split(",");
            if (p.length < 6) return null;
            
            String id = p[0].trim();
            String name = p[1].trim();
            String phone = p[2].trim();
            String email = p[3].trim();
            String mCode = p[4].trim();
            double fee = Double.parseDouble(p[5].trim());
            
            return new Student(id, name, phone, email, mCode, fee);
        } catch (NumberFormatException e) {
            return null; // 
        }
    }
}