/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.domain;

import java.util.ArrayList;

/**
 *
 * @author Jonne
 */
public class Person {
    private final String name;
    private final long id;
    private Budget budget;
    
    public Person(long id, String name) {
        this.id = id;
        this.name = name;
        this.budget = new Budget();
    }
    
    public long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Budget getBudget() {
        return this.budget;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", id=" + id + '}';
    }
    
}
