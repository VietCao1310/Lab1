package fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import entities.Student;

public class StudentFileHelper implements IFileReadWrite<Student> {

    private final String FILE_NAME = "src/fileio/registrations.dat";

    @Override
    public List<Student> read() throws Exception {
        List<Student> list = new ArrayList<>();
        File f = new File(FILE_NAME);
        if (!f.exists()) return list;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Student s = convertToStudent(line);
                if (s != null) list.add(s);
            }
        } finally {
            if (br != null) br.close();
        }
        return list;
    }

    @Override
    public boolean write(List<Student> list) throws Exception {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(FILE_NAME));
            for (Student s : list) {
                bw.write(convertToString(s));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            if (bw != null) bw.close();
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