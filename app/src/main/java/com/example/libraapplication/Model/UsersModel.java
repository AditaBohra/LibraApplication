package com.example.libraapplication.Model;

public class UsersModel {
    private String euid;
    private String uname;
    private String email;
    private String selectedRole;

    public String getEuid() {
        return euid;
    }

    public void setEuid(String euid) {
        this.euid = euid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }
}
