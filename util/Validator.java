package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Validator {
    
    public  static final double DEFAULT_FEE = 6000000;       
    //Pattern data when user input
    private static final String ID_PATTERN = "^[S|H|D|Q|C]E\\d{6}$";
    private static final String NAME_PATTERN = "^[a-zA-Z\\\\s]{2,20}$";
    private static final String PHONE_PATTERN = "^0\\d{9}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    //pattern phone to check viettel or vnpt
    private static final Set<String> VIETTEL_PREFIXES = new HashSet<>(Arrays.asList("086","096","097","098","032","033","034","035","036","037","038","039"));
    private static final Set<String> VNPT_PREFIXES = new HashSet<>(Arrays.asList("088","091","094","083","084","085"));

    public static boolean validStudentId(String id) {
        return id != null && id.matches(ID_PATTERN);
    }
    public static boolean validName(String name) {
        return name != null && name.matches(NAME_PATTERN);
    }
    public static boolean validPhone(String phone) {
        return phone != null && phone.matches(PHONE_PATTERN);
    }
    public static boolean validEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }
    //check 3 number to return viettel or vnpt
    public static boolean isViettelOrVNPT(String phone) {
        if (!validPhone(phone)) return false;
        String prefix = phone.substring(0,3);
        return VIETTEL_PREFIXES.contains(prefix) || VNPT_PREFIXES.contains(prefix);
    }
}
