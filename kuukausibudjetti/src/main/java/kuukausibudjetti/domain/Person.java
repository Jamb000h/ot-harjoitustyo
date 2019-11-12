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
    private ArrayList<Integer> incomes;
    private ArrayList<Integer> expenditures;
    
    public Person(long id, String name) {
        this.id = id;
        this.name = name;
        this.incomes = new ArrayList<>();
        this.expenditures = new ArrayList<>();
    }
    
    public void addIncome(Integer sum) {
        this.incomes.add(sum);
    }
    
    public void addExpenditure(Integer sum) {
        this.expenditures.add(sum);
    }

    public ArrayList<Integer> getIncomes() {
        return incomes;
    }

    public ArrayList<Integer> getExpenditures() {
        return expenditures;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", id=" + id + '}';
    }
    
}
