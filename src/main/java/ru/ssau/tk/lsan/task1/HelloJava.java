package ru.ssau.tk.lsan.task1;

public class HelloJava {
//TODO отдельные файлы для задачек, видимо. А то мэйн один, а вас много
    public static void main(String[] args) {
        //task 1.1------------------------------------------------------
        Person lSan = new Person();
        lSan.setFirstName("Lyudmila");
        lSan.setLastName("Seleznyova");
        lSan.setPassportId(5);
        System.out.println(lSan.getFirstName());
        System.out.println(lSan.getLastName());
        System.out.println(lSan.getPassportId());
        //--------------------------------------------------------------

        //task 1.2------------------------------------------------------
        Person human1 = new Person();
        Person human2 = new Person("firstName","lastName");
        Person human3 = new Person(5);
        Person human4 = new Person("firstName","lastName",5);
        //--------------------------------------------------------------
    }
}
