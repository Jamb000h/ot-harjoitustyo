# Kuukausibudjetti
Tämä repositorio sisältää harjoitustyön ja _laskarit_ kurssille **Ohjelmistotekniikka**

## Dokumentaatio
[Käyttöohje](./dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](./dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](./dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](./dokumentaatio/testausdokumentti.md)

[Työaikakirjanpito](./dokumentaatio/tyoaikakirjanpito.md)

## Releaset
[Viikko 5](https://github.com/Jamb000h/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6 (final)](https://github.com/Jamb000h/ot-harjoitustyo/releases/tag/viikko6)

## Ohjelmiston käynnistys
Aja komento
```mvn compile exec:java -Dexec.mainClass=kuukausibudjetti.ui.BudgetUI```
kansiossa ```/kuukausibudjetti```

## Ohjelmiston testaus
### Testien ajaminen
Aja komento
```mvn test```
kansiossa ```/kuukausibudjetti```
### Testikattavuusraportti
Aja komento
```mvn jacoco:report```
kansiossa ```/kuukausibudjetti```

## Muut komennot
### Suorituskelpoisen jar-tiedoston generointi
Aja komento
```mvn package```
kansiossa ```/kuukausibudjetti```
Tämän jälkeen jar-tiedoston voi suorittaa komennolla
``` java -jar target/kuukausibudjetti-1.0-SNAPSHOT.jar```
### Koodin laadullinen raportti
Aja komento
```mvn jxr:jxr checkstyle:checkstyle```
kansiossa ```/kuukausibudjetti```
### Javadoc-dokumentaation generointi
Aja komento
```mvn javadoc:javadoc```
kansiossa ```/kuukausibudjetti```


