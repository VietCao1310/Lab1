package entities;


public class Mountain {
    private String code;
    private String name;
    private String province; 
    private String description;

    public Mountain(String code, String name, String province, String description) {
        this.code = code;
        this.name = name;
        this.province = province;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public String getDescription() {
        return description;
    }
   
    @Override
    public String toString() {
        return code + " - " + name;
    }
}
