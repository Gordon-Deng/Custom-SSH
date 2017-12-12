package javaBean;

public class Result {
    private String name;
    private String value;
    private String type;

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Result(){
        super();
    }

    public Result(String name,String type,String value){
        super();
        this.name = name;
        this.value = value;
        this.type = type;
    }
}
