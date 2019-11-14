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
public class Budget {
    private ArrayList<Entry> expenditures;
    private ArrayList<Entry> incomes;
    
    public Budget() {
        this.expenditures = new ArrayList<>();
        this.incomes = new ArrayList<>();
    }

    public ArrayList<Entry> getExpenditures() {
        return expenditures;
    }

    public ArrayList<Entry> getIncomes() {
        return incomes;
    }
}
