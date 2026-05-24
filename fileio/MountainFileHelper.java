package fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import model.Mountain;

/**
 *
 * @author NguyenPhuc
 */
public class MountainFileHelper implements IFileReadWrite<Mountain> {

    private final String FILE_NAME = "src/fileio/MountainList.csv";

    @Override
    public List<Mountain> read() throws Exception {
        List<Mountain> list = new ArrayList<>();
        File f;
        FileInputStream file = null;
        BufferedReader myInput = null;// create Buffer    
        try {
            f = new File(FILE_NAME);//open file
            String fullPath = f.getAbsolutePath(); //get Fullpath of file
            file = new FileInputStream(fullPath);
            myInput = new BufferedReader(new InputStreamReader(file));
            // read line until the end of the file
            String line = null;
            boolean first = true;
            while ((line = myInput.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (first) {
                    first = false;
                    continue;
                }
                Mountain moun = convertToMountain(line);
                list.add(moun);
            }

        } catch (IOException ex) {
            throw ex;
        } finally {
            if (myInput != null) {
                myInput.close();
            }
            if (file != null) {
                file.close();
            }
        }
        return list;
    }

    @Override
    public boolean write(List<Mountain> list) throws Exception {
        File f;
//        FileOutputStream file = null;
//        BufferedWriter myOutput = null;// create Buffer    
//        try {
//            f = new File(FILE_NAME);//open file
//            String fullPath = f.getAbsolutePath(); //get Fullpath of file
//            file = new FileOutputStream(fullPath);
//            myOutput = new BufferedWriter(new OutputStreamWriter(file));
//            // write line until the end of the file
//            for (Mountain moun : list) {
//                String str = convertToString(moun);
//                myOutput.write(str);
//                myOutput.newLine();
//            }
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            if (myOutput != null) {
//                myOutput.close();
//            }
//            if (file != null) {
//                file.close();
//            }
//        }
        return true;
    }

    private Mountain convertToMountain(String str) {
        Mountain moun = null;

        String[] p = str.split(",");
        int num = Integer.parseInt(p[0].trim());
        String code = String.format("MT%02d", num);        
        String name = p[1].trim();
        String province = p[2].trim();
        String description = "";
        if (p.length >= 4) {
            description = p[3].trim();
        }
        moun = new Mountain(code, name, province, description);
        return moun;
    }
}
