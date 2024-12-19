package com.example.dandanfinalproject;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
    /**Create private properties */

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty address;
    private final SimpleDoubleProperty weight;
    private final SimpleBooleanProperty allergy;


    /**Constructor with specific properties */

    public Student(int id, String firstName, String lastName, String address, double weight, boolean allergy)
    {
        this.id=new SimpleIntegerProperty(id);
        this.firstName=new SimpleStringProperty(firstName);
        this.lastName= new SimpleStringProperty(lastName);
        this.address=new SimpleStringProperty(address);
        this.weight=new SimpleDoubleProperty(weight);
        this.allergy = new SimpleBooleanProperty(allergy);
    }

    /**getter and setter */
    public int getId()
    {
        return id.get();
    }

    public String getFirstName()
    {
        return firstName.get();
    }



    public String getLastName()
    {
        return lastName.get();
    }

    public String getAddress()
    {
        return address.get();
    }

    public double getWeight()
    {
        return weight.get();
    }
    public boolean getAllergy(){return allergy.get();}


    public void setId(int id)
    {
        this.id.set(id);
    }

    public void setFirstName(String firstName)
    {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName)
    {
        this.lastName.set(lastName);
    }

    public void setAddress(String address)
    {
        this.address.set(address);
    }

    public void setWeight(double weight)
    {
        this.weight.set(weight);
    }
    public void setAllergy(boolean allergy){this.allergy.set(allergy);}
}
