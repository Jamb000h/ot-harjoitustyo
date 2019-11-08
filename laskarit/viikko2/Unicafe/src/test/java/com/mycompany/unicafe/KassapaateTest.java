package com.mycompany.unicafe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonne
 */
public class KassapaateTest {
    
    Kassapaate paate;

    @Before
    public void setUp() {
        paate = new Kassapaate();
    }

    @Test
    public void kassapaatteenAlkutilaOikein() {
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassaMyyEdullisenLounaanJosKateistaRiittavasti() {
        int vaihtoraha = paate.syoEdullisesti(250);
        assertEquals(10, vaihtoraha);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void kassaEiMyyEdullistaLounastaJosKateistaEiOleRiittavasti() {
        int vaihtoraha = paate.syoEdullisesti(230);
        assertEquals(230, vaihtoraha);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kassaMyyMaukkaanLounaanJosKateistaRiittavasti() {
        int vaihtoraha = paate.syoMaukkaasti(420);
        assertEquals(20, vaihtoraha);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void kassaEiMyyMaukastaLounastaJosKateistaEiOleRiittavasti() {
        int vaihtoraha = paate.syoMaukkaasti(390);
        assertEquals(390, vaihtoraha);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kassaMyyEdullisenLounaanJosKortillaRiittavastiRahaa() {
        Maksukortti kortti = new Maksukortti(1000);
        assertTrue(paate.syoEdullisesti(kortti));
        assertEquals(760, kortti.saldo());
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kassaMyyMaukkaanLounaanJosKortillaRiittavastiRahaa() {
        Maksukortti kortti = new Maksukortti(1000);
        assertTrue(paate.syoMaukkaasti(kortti));
        assertEquals(600, kortti.saldo());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassaEiMyyEdullistaLounastaJosKortillaEiKatetta() {
        Maksukortti kortti = new Maksukortti(230);
        assertFalse(paate.syoEdullisesti(kortti));
        assertEquals(230, kortti.saldo());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kassaEiMyyMaukastaLounastaJosKortillaEiKatetta() {
        Maksukortti kortti = new Maksukortti(390);
        assertFalse(paate.syoMaukkaasti(kortti));
        assertEquals(390, kortti.saldo());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanRahamaaraKasvaaKunKortilleLadataanRahaa() {
        Maksukortti kortti = new Maksukortti(100);
        paate.lataaRahaaKortille(kortti, 10);
        assertEquals(110, kortti.saldo());
        assertEquals(100010, paate.kassassaRahaa());
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKunKortilleLadataanNegatiivinenSumma() {
        Maksukortti kortti = new Maksukortti(100);
        paate.lataaRahaaKortille(kortti, -10);
        assertEquals(100, kortti.saldo());
        assertEquals(100000, paate.kassassaRahaa());
    }
}
