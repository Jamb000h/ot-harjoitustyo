/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.domain;

/**
 *
 * @author Jonne
 */
public enum EntryType {
    EXPENDITURE(0), INCOME(1);

    private final int id;

    EntryType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
