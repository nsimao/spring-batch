package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {
// ------------------------------ FIELDS ------------------------------

    @Id
    private Integer id;
    private String name;
    private String dept;
    private Integer salary;
    private Date time;

// --------------------------- CONSTRUCTORS ---------------------------

    public User() {
    }

    public User(Integer id, String name, String dept, Integer salary, Date time) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
        this.time = time;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (dept != null ? !dept.equals(user.dept) : user.dept != null) return false;
        if (salary != null ? !salary.equals(user.salary) : user.salary != null) return false;
        return time != null ? time.equals(user.time) : user.time == null;
    }

    @Override
    public final int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dept != null ? dept.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
