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
    
    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", id=" + id + '}';
    }
    
}
