package sample;

public class Person {
    String name;
    int money;

    public enum status{
        STUDENT, TEACHER, STAFF,PARENT;
    }

    status st;


    public Person(status st){
        this.st = st;
    }
    public Person(String name, int money){
        this.name = name;
        this.money = money;
    }

    public void statusDetails(){
        switch(st){
            case STUDENT: System.out.println("Student registered");
            case TEACHER: System.out.println("Teacher registered");
            case STAFF: System.out.println("Staff registered");
            case PARENT: System.out.println("Parent registered");
        }



    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public Integer getMoney(){
        return money;
    }



}
