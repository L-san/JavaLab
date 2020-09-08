package ru.ssau.tk.lsan.JavaLab;

public class HelloJava {
//TODO отдельные файлы для задачек, видимо. А то мэйн один, а вас много
    public static void main(String[] args) {
        Person lSan = new Person();
        lSan.setFirstName("Lyudmila");
        lSan.setLastName("Seleznyova");
        lSan.setPassportId(5);
        System.out.println(lSan.getFirstName());
        System.out.println(lSan.getLastName());
        System.out.println(lSan.getPassportId());
    }
}
