package com.automation.test.day5;

public class Spartan {
    private int id;
    private String name;
    private String gender;
    private Long phone;

    public Spartan(int id, String name, String gender, Long phone){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.phone=phone;

    }
    public int getId(){
        return id;
    }

    public static void main(String[] args) {
        Spartan spartan = new Spartan(1,"Machael","Male",3214213214L);
        System.out.println( spartan.getId());

    }
}
