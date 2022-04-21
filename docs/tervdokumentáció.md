# Tervdokumentáció

<img src="Tervdokumentáció.drawio.png" width="800">

## Osztályok

### TankTrouble class

Hello, szhia!

### NetworkController class

Ez az osztály felelős azért, hogy elvégezze a program halózati kommunikációs feladatait. Ezen belül a `scan()` függvény felelős a hálózaton elérhető szobák felderítéséért. Ezt úgy végzi el, hogy socket küldéseket végez egy adott kulccsal (egy előre meghatározott karakter sorozattal) a /24-es subnet címein, és amennyiben egy szerver válaszol a kliens felé, abban az esetben ez az osztály a `foundRoom` atrribútum listájához hozzáfűz egy elemet, ami a felfedezett szoba tula
d

Másik lehetőség, hogy lobby szobát hozzunk létre, amit a `makeRoom()` metódussal tehetünk meg. Ekkor a `serverMode` attribútomot igaz értékre kell állítani.

#### Kivétel kezelés

Kivétel történhet abban az esetben, ha nem sikerül létrehozni a socketet, amivel kommunikálni fogunk a játék kliensek között. Ebben az esetben a felhasználónak egy felugró ablakban fogja megjeleníteni a hiba szövegét. 

### Room class

Ez az osztály a NetworkController osztálynak az .

### Battlefield class




### Field class

### Tank class

### Missile class

### Player class



