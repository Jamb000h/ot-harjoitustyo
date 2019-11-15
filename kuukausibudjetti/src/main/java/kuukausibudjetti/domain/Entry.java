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
public class Entry {
    private final long id;
    private final Integer sum;
    private final EntryType type;
    private final String desc;
    
    public Entry(long id, Integer sum, EntryType type, String desc) {
        this.id = id;
        this.sum = sum;
        this.type = type;
        this.desc = desc;
    }
    
    public long getId() {
        return this.id;
    }

    public Integer getSum() {
        return this.sum;
    }
    
    public EntryType getType() {
        return this.type;
    }
    
    public String getDesc() {
        return this.desc;
    }
}
