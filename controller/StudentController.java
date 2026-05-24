package controller;

import model.Student;
import fileio.IFileReadWrite;
import fileio.StudentFileHelper;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private List<Student> students;

    public StudentController() {

        try {
            IFileReadWrite<Student> fileHelper = new StudentFileHelper();
            students = fileHelper.read();
            if (students == null) {
                students = new ArrayList<>();
            }
        } catch (Exception e) {
            students = new ArrayList<>();
        }
    }

    public List<Student> getAll() {
        return students;
    }

    public void addRegistration(Student s) {
        students.add(s);
    }

    public Student findByCode(String code) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(code)) {
                return s;
            }
        }
        return null;
    }

    public boolean updateRegistration(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(updatedStudent.getId())) {
                students.set(i, updatedStudent);
                return true;
            }
        }
        return false;
    }

    public boolean deleteRegistration(String id) {
        Student s = findByCode(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }

    public List<Student> searchByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> filterByCampus(String campusCode) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getId().toUpperCase().startsWith(campusCode.toUpperCase())) {
                result.add(s);
            }
        }
        return result;
    }

    public boolean saveToFile() {
        try {
            IFileReadWrite<Student> fileHelper = new StudentFileHelper();
            return fileHelper.write(students);
        } catch (Exception e) {
            return false;
        }
    }

    public int countStudentsByMountain(String mountainCode) {
        int count = 0;
        for (Student s : getAll()) {
            if (s.getMountainCode().equalsIgnoreCase(mountainCode)) {
                count++;
            }
        }
        return count;
    }

    public double getTotalFeeByMountain(String mountainCode) {
        double total = 0;
        for (Student s : getAll()) {
            if (s.getMountainCode().equalsIgnoreCase(mountainCode)) {
                total += s.getFee();
            }
        }
        return total;
    }
}
