# Tank Trouble játék specifikáció
## Csapattagok
- Girgász Péter Ákos
- Mihalik Márk
- Vezse Botond

## Játékmenet rövid leírása
Kettő vagy több játékos a játék kezdetekor random helyen találja magát egy véletlenszerűen generált pályán. A játékosok egy-egy tankot irányítanak és céljuk a másik játékosok járműveinek megsemmisítése. A játék last standing. Az nyer, aki az utolsó életben maradt játékos a pályán.

# A játék részletes bemutatása
## Játék kezdete

A játékos a kezdőképernyőn három lehetőség közül választhat, ha megadta egy szövegdobozban a nevét:
- `Join` (csatlakozás már meglévő játékhoz)
- `Make game` (játékszerver indítása)    
- `Quit` (kilépés a játékból)
Amíg nem adja meg a nevét, addig ezek az opciók közül csak a `Quit` lesz elérhető a számára.

### Csatlakozás meglévő játékhoz

Erre a gombra kattintva a játék feltérképezi az azonos lokális hálózaton létrehozott Tank Trouble szervereket, majd ezeket egy listában megjeleníti, ahol a játékos kiválaszthatja a kiszolgáló neve alapján azt, amelyikhez csatlakozni szeretne. Ezek után bekerül egy lobby-ba (előszobába). Ahol addig várakozik, amíg a kiválasztott szerver létrehozója el nem indítja a játékot, vagy a felhasználó vissza nem lép a szerverlistára.

A kliens végigpingeli az adott subneten az összes címet valami porton, és megkérdezi van-e ott szoba. A válaszban szerepel a szoba neve, a maximális játékoslétszám és az elérhető szabad helyek száma. A talált szobák megjelennek a listában.

### Játékszerver indítása

Ezt az opciót választva van lehetőség új szervert létrehozni. A felhasználó megadhatja a szoba nevét és a játékosok maximális számát. A szerver létrehozása után automatikusan a lobby-ba (előszobába) kerül a  létrehozója. Az előszoba addig él, ameddig az azt létrehozó játékos el nem hagyja a `Leave game` (elhagyás) gombbal vagy – minimum két játékos esetén – el nem indítja azt a `Start game` gombbal. Az elhagyás és az indítás előtt a játékosnak egy felugró dobozban meg kell erősítenie a szándékát.

### Kilépés

A kilépés gombra kattintva egy felugró üzenő dobozban a játékosnak meg kell erősítenie a kilépési szándékát. Amennyiben megerősíti, a program futása befejeződik a felhasználó számítógépén, ellenkező esetben pedig folytatódik.

## A pálya felépítése
A játéktér 2 dimenziós, **n** *x* **m** mezőből áll, ahol *n* és *m* pozitív egész számok. A játéktér mérete fix, a felhasználó nem tudja módosítani. Ezeken a mezőkön mozoghatnak a játékosok a tankokkal. A mezők lehetnek falak és bejárható utak. A falakon nem lehet átmenni a tankkal, sem átlőni nem lehet rajtuk. Az utakon a tankok szabadon mozoghatnak. A játékosok a pályát felülnézetből látják.
A pálya a szerver elindításakor véletlenszerűen generálódik úgy, hogy a tankok ne legyenek elszigetelve egymástól, tehát legyen bejárható szabad út tetszőleges két tank között.


## Tankok irányítása
A tankok irányítása a `wasd` billentyűkkel történik meg egyszeri lenyomás esetén. A `w` jelenti a pályán a felfele, az `a` a balra, az `s` a lefele és a `d` pedig a jobbra mozgást. A gomb tunkolása során a tank gyorsabban halad, mivel minden egyes gomblenyomásra halad egy lépést a választott irányba. A tankokat "ugrás" szerűen lehet irányítani. Amennyiben valamilyen irányban fordulást kell eszközölni, akkor az forgatási iránynak megfelelő gombot kell lenyomni, ekkor a tank el fog fordulni. Majd az adott gomb újbóli lenyomását követően fog csak a jármű az adott irányba előre haladni. A tank arra néz amerre a lövege.

Erre példa:
A tank a pályán felfele irányba néz, és a játékos jobbra szeretne fordulni. Ekkor le kell nyomnia a `d` gombot. Ezután, hogy jobbra egy egységet lépjen előre, újból meg kell nyomnia a `d` gombot.

A lövés parancs a `space` gomb lenyomásakor aktiválódik, és ez a tank lövegének irányába fog elindítani egy lövedéket, amely egyenes vonalú egyenletes mozgást végez mindaddig, amíg egy tank vagy egy fal mozgásában akadályozni fogja ezt az entitást. A lövés indítványozása, megbénítja a tankot fél másodpercre.

## A játék logikája

Minden tank egy élettel rendelkezik, amit találat esetén veszít el. A lövedékek nem hatolhatnak át a falakon, illetve más tankokon, mivel ezek az objektumok elnyelik. Az a szerencsés és egyben ügyes játékos nyeri a játszmát, aki legtovább marad életben ebben az ádáz küzdelemben. Az utolsó életben maradt játékos győz. Azok a játékosok, akik alul maradtak a küzdelemben, és egy másik tank kegyetlenül kilőtte őket, tovább nézhetik a játékot, vagy elhagyhatják a szervert, így visszakerülnek a kezdőképernyőre. Abban az esetben, ha véget ér a játék, akkor minden néző, illetve a nyertes játékos 5 másodperc után a játék új szervert hoz létre, aminek előszobájába csatlakoztatja ezeket a játékosokat.

## Tesztesetek

### Pálya összefüggőségének a vizsgálata

A pálya generálásának validálását egy teszt esettel valósítjuk meg, ami ellenőrzi, hogy egy adott pontból minden pálya terület elérhető-e. Vagyis, hogy a pálya, egy összefüggő gráfot ad eredményül a bejárás után. 

### Tank megsemmisülésének vizsgálata

Ebben a tesztesetben elvégezzük a tank manuális megsemmisítését abban az esetben, ha a másik tank lövege a célba talál. 

### Tank-löveg megsemmisülésének vizsgálata

Ebben a tesztben megvizsgáljuk azt az esetet, ha a lövedék falat, vagy célt talál. Abban az esetben sikeres a teszt, ha a lövedék megsemmisült. 

### Szoba felfedezésének vizsgálata

Ehhez a teszt két (különböző gépen) futatott program szükséges, mivel a teszteset célja, hogy kilistázza azokat a szabad szobákat, ahol létrehozott lobby áll rendelkezésre.

#### A specifikációt készítették:
- **Girgász Péter Ákos** - OH9I5E
- **Mihalik Márk** - ST9OKE
- **Vezse Botond** - JWS3X8

A specifikációt közösen, Visual Studio Code Live Share kiegészítővel készítettük.
