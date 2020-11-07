package com.e.teacher;

public class Teacher {
    private String name;
    private String email;
    private String contact;
    private long registrationno;
    private String password;

    public Teacher() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getRegistrationno() {
        return registrationno;
    }

    public void setRegistrationno(long registrationno) {
        this.registrationno = registrationno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
