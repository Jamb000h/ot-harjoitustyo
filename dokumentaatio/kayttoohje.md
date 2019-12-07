# Käyttöohje

Lataa tiedosto [kuukausibudjetti-1.0-SNAPSHOT.jar](https://github.com/Jamb000h/ot-harjoitustyo/releases/download/viikko6/kuukausibudjetti-1.0-SNAPSHOT.jar)

## Konfigurointi

Ohjelma olettaa, että sen käynnistyshakemistossa on konfiguraatiotiedosto _config.properties_, jossa on seuraavat asetukset:

```
dbFile=filename.db
```

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar kuukausibudjetti-1.0-SNAPSHOT.jar
```

## Henkilöiden lisääminen

Voit heti ohjelman auettua tai missä tahansa vaiheessa lisätä uusia henkilöitä budjetille vasemman reunan henkilölistan alapuolelta.

<img src="./lisaa-henkilo.png">

## Kirjausten lisääminen

Voit heti ohjelman auettua lisätä kirjauksia yhteiseen budjettiin henkilölistan oikealla puolella. Jos budjetissa on henkilöitä lisättynä, voit aina palata yhteisiin kirjauksiin painamalla vasemman reunan henkilölistauksesta kohtaa _yhteiset_. Henkilölle saat tehtyä kirjauksia klikkaamalla ensin henkilön nimeä ja sitten tekemällä kirjauksia.

<img src="./lisaa-tulo.png">

## Henkilöiden ja kirjauksien poistaminen

Voit poistaa kirjauksia ja henkilöitä listoissa olevien _poista_-napeilla. **HUOM!** Henkilön poistaminen poistaa myös henkilölle tehdyt kirjaukset budjetista.

## Budjetin kokonaistilanne

Voit koska tahansa tarkastaa budjetin kokonaistilanteen alareunan luvuista.

<img src="./kokotilanne.png">

## Henkilön ja yhteisten kirjausten tilanne

Valitsemalla joko henkilö tai yhteiset kirjaukset, voit tutkia ikkunan oikeassa reunassa henkilön tai yhteisten kirjausten summia ja erotusta.

<img src="./henkilotilanne.png">
