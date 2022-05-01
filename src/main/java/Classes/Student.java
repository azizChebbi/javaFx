package Classes;

public class Student {
    public String name ;
    private String cin ;

    public Student(String name, String cin) {
        this.name = name;
        this.cin = cin;
    }

    public String getCin(){
        return this.cin ;
    }
}
