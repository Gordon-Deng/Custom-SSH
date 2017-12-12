package javaBean;

import java.util.Map;

public class ActionBean {
    private String name;
    private String classname;
    private String method;
    private Map<String , Result> results;

    public ActionBean(){
        super();
    }

    public ActionBean(String name,String classname,String method, Map<String , Result> results){
        super();
        this.name = name;
        this.classname = classname;
        this.method = method;
        this.results = results;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Result> getResults() {
        return results;
    }

    public void setResults(Map<String, Result> results) {
        this.results = results;
    }
}

