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
        assertEquals(10, kortti.saldo());   
    }
    
    @Test
    public void saldolatausToimiiOikein() {
        kortti.lataaRahaa(5);
        assertEquals(15, kortti.saldo());   
    }
    
    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(5);
        assertEquals(5, kortti.saldo());   
    }
    
    @Test
    public void saldoEiMuutuJosOtetaanRahaaYliSaldon() {
        kortti.otaRahaa(15);
        assertEquals(10, kortti.saldo());   
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
