package controller;

import entities.Mountain;
import fileio.IFileReadWrite;
import fileio.MountainFileHelper;
import java.util.ArrayList;
import java.util.List;

public class MountainController {

    private List<Mountain> mountains = new ArrayList<>();
    //read file --> list
    public MountainController() {
        try {
            IFileReadWrite file = new MountainFileHelper();
            mountains = file.read();
        } catch (Exception e) {
            mountains = new ArrayList<>();
        }
    }
    
    public List<Mountain> getAll() {
        return mountains;
    }

    public Mountain findByCode(String code) {
        for (Mountain m : mountains) {
            if (m.getCode().equalsIgnoreCase(code)) {
                return m;
            }
        }
        return null;
    }
}
