package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void alkusaldoOikein() {
        assertEquals("saldo: 0.10", kortti.toString());   
    }
    
    @Test
    public void saldolatausToimiiOikein() {
        kortti.lataaRahaa(5);
        assertEquals("saldo: 0.15", kortti.toString());      
    }
    
    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.5", kortti.toString());      
    }
    
    @Test
    public void saldoEiMuutuJosOtetaanRahaaYliSaldon() {
        kortti.otaRahaa(15);
        assertEquals("saldo: 0.10", kortti.toString());     
    }
    
    @Test
    public void rahanOttaminenPalauttaaTrueJosSaldoRiittaa() {
        assertTrue(kortti.otaRahaa(10));   
    }
    
    @Test
    public void rahanOttaminenPalauttaaFalseJosSaldoEiRiita() {
        assertFalse(kortti.otaRahaa(15));   
    }
}
