# Fejlesztői dokumentáció - GUI

## TankTrouble

Ez az osztály az ősosztálya a játéknak.
### public void addNewOwnRoom(Room newOwnRoom)

Ez a függvény eltárolja a játékos saját szobáját, amennyiben rendelkezik ilyen objektummal.

### public void addNewListOfRemoteRooms(ArrayList<Room> newListOfRemoteRooms)

Ez a függvény frissíti a felfedezett szobák listáját úgy, hogy az előző listát minden elemét kitörli, majd a paraméterként megadott lista minden elemét hozzá adja a privát változóként eltárolt listához.

### public ArrayList<Room> getListOfRemoteRooms()

Ez a függvény a privát változóban eltárolt elérhető szobákat kérdezi le.

###  public Room getOwnRoom()

Ez a függvény lekéri a játékos saját szobáját.

### public boolean hasOwnRoom()

Ez a függvény lekérdezi, hogy van-e a játékosnak saját szobája. Amennyiben van akkor a vissza térési érték igaz, azaz true.

### public String getThisPlayerName()

Ez a függvény lekéri ebben a játék kliensben játszó játékos játékosnevét.

###  public Player getThisPlayer()



###  public void modifyPlayerName(String name)



## MainMenuWindow osztály

Ez az osztály egy class-ot és egy form-ot foglal magába. A form-ban az alapvető funkciókat elrejtettem, ilyenek például a Java Swing könyvtár bizonyos objektumai, amivel képesek vagyunk GUI-t készíteni. 

### Konstruktor

A konstruktorban létrejön az az ablak, amiben minden építő eleme ennek az ablakban helyet fog foglalni. Továbbá itt társulnak hozzá a gombokhoz a lenyomáskor meghívódó függvények, azaz action listenerek. A függvény nevek jól mutatják a megvalósított funkciót, így erre külön nem térek ki.

### public void setMainMenuWindowFrameVisible()

Ez a függvény a main menu ablakát jeleníti meg. Ezt a függvényt abban az esetben kell meghívni, ha ezt az ablakot elrejtjük a felhasználó szeme elől, mint például játék közben, hogy ne legyen két ablak megnyitva.

### private void loadMainMenuWindowImages()

Ennek a függvénynek a célja, hogy a project mappától számított `src/main/gui/resources/` elérési úton létező képeket megjelenítse a kijelölt helyén a főképernyőnek. Ennek a függvénynek hibakezelést egy esetben végeztük el, amikor a kép nem található a megadott elérési úton, így hibával zárva be a programot.

### public void openCreateRoomWindow() 

Ez a függvény felelős a főmenü eltüntetéséért és az új szoba létrehozása ablaknak a megjelenítéséért.

### public void openListRoomsWindow()

Ez a függvény felelős a főmenü eltüntetéséért és az lobby listázó ablak megjelenítéséért.

### public void setNewPlayerName()

Ebben a függvényben valósul meg a felhasználó nevének a megadása egy felugró ablak segítéségével. A felugró ablak egy beviteli felugró ablak, amibe a felhasználó tetszőleges szöveget írhat  be, tetszőleges hosszúságban. Amennyiben a felhasználó beviszi a szöveget a függvény első körben ellenőrzi, hogy nulla nyitott és húsz zárt intervallumban helyezkedik-e el a bevitt szövegnek a hossza illetve ellenőrzi továbbá a bevitt szövegnek a specifikációban megállapított helyeségét, miszerint csak számokat és az angol ABC karaktereit tartalmazhatja a felhasználó neve.  Ha a felhasználó rosszul adta meg a nevet, akkor egy végtelenített ciklusban újra megjelenik ez a felugró ablak, egy segítséggel, hogy miknek kell megfelelnie ennek a névnek.
Amennyiben ezeknek a feltételeknek megfelelet a bevitt szöveg, abban az esetben megtörténik a felhasználó név tárolása, illetve engedélyezetté válik a szoba létrehozása és a szobák kilistázása funkció. 
Amennyiben a felhasználó nem adott meg a bevitt szöveg NullPointer-ként jelenne meg, így ezt a hibát le kellett kezelni. Ezt úgy valósítottuk meg, hogy a felhasználó felhasználónév nélkül tovább jut a főoldalra, viszont ott csak a kilépés lehetősége és a felhasználó név változtatása lehetőség jelenik meg számára. 

###  public void quit()

Ez a függvény kilépteti a programot 0-s hibakóddal.

## CreateRoomWindow

Ez az osztály felelős az új játék létrehozása funkció GUI megvalósítására. Önmagában ez az osztály egy felugró ablakot valósít meg.

### Konstruktor

Ez a konstruktor az objektum létrejöttekor meghív egy felugró ablakot, ami megjeleníti a játokos nevéből kreált szoba nevet, illetve ezen a ponton a játékos megadhatja, hogy összesen, hány ember játszhat a szerverén egy időben. Amennyiben a CANCEL opciót választja akkor a főoldalon találja magát, viszont, ha az OK gombot választotta és a szám, amit beírt helyes, akkor létrehozza a program a szobát és tovább irányítja a játékost a lobbyba.

### public void backToMainMenuWindow()

Ez a függvény felelős a hálózati felderítés leállítására, illetve a főmenü megjelenítésére.

### public void createRoom(String roomName, int slots)

Ez a függvény létrehoz egy szobát a felhasználónak, amihez hozzárendeli a tulajdonos nevéből generált szoba nevet, illetve a szoba tulajdonságát, miszerint ez a szoba host ebben a kliensben. Továbbá létrejön egy lista, amiben a felcsatlakozott játékosok fognak helyet foglalni, továbbá  hozzárendelődik a szobának a maximális kapacitása.

Ezek után a szoba létrehozója automatikusan hozzáadódik a szobához és a szoba elérhető lesz más kliensekből is. Legvégül pedig a program a játékost egy új felületre irányítja át, ami a lobby.

## ListRoomsWindow

### Konstruktor

Itt létrejön az ablak, illetve hozzáadódnak a különböző eseményeket lereagáló függvények.

### private void addActionListeners()

Ez a függvény adja hozzá a gombokhoz a megnyomásukkor létrejövő esemény lereagálásához megírt függvényeket. Ilyen gombok például:

- vissza gomb, ami vissza irányít a főmenübe,
- keresés gomb, ami megjeleníti a felderített szobákat ebben az ablakban rádió gombok segítéségével,
- részvétel gomb, ami kikeresi a kiválasztott rádió gombot a listából és kezdeményezi a csatlakozást ehhez a szobához.

### private void listRoomInThePanel()

### public void joinChosenRoom(Room chosenRoom)

Ez a függvény hívja meg a hálózati kezelő réteg csatlakozásért felelős függvényét. Amennyiben sikeres volt a csatlakozás, abban az esetben a felhasználót egy másik ablakba irányítja át, ami a lobby.

### public void backToMainMenuWindow()


Ez a függvény felelős az ablak megszüntetéséért és a főmenü megjelenítéséért.

## WaitForGameStartWindow

### Konstruktor 

Ez hozza létre az ablakot, illetve adja hozzá azt a funkciót, ami szerint csak a szoba tulajdonosa indíthatja el a játékot. Ebben a konstruktorba foglalnak helyet a start és a kilépés gomboknak eseményeinek a lereagálása is.

### public void setWaitForGameStartWindowFrameVisible()

Ez a függvény megjeleníti a lobby ablakát.


### public void leaveRoom()

Ez a függvény megsemmisíti az ablakot, jelzi a hálózati rétegnek, hogy a jelenlegi kliens elhagyta a lobbyt, illetve a felhasználót visszairányítja a főmenübe.

### public void startGame()

Ez a függvény elrejti ideiglenesen a lobby-t a felhasználó szeme elől, mivel játék befejeztével ide fogja őt visszadobni. Ekkor létrejön egy új játék szoba és a szerveren (ami mindig a szobát létrehozó játékos kliense) legenerálódik a pálya, a tankok helyzete és ez kirajzolódik ezen a játék kliensen. A hálózati réteg elkezdi a játékhoz szükséges adatokat küldeni a többi kliensre.

### public void remoteGameStarted()

Ez a függvény felelős a lobby eltüntetéséért.

### public void updateJoinedPlayerList(ArrayList<Player> list)

Ez a függvény egy játékosokból álló listából, ami a függvény paramétere megjeleníti szöveges formátumban az ablak közepén a csatlakozott játékosokat.



## MovingObject osztály

Ez az osztály az ősosztálya, azon mozgó objektumokhoz tartozó osztályoknak, amelyek a játékban résztvesznek. 

Az osztály tartalmaz egy enum osztályt, amely a jobb, bal, fel és le irányokat valósítja meg.
Ezen felül még tartozik hozzá 4 különböző változó: 
    - position: Publikus változó és a Field osztály egy objektuma, azaz azt adja meg, hogy a mozgó objektum a pálya melyik mezőjén helyezkedik.
    - direction: Az előbb ismertetett enum osztályból származik és a mozgó objektum irányát adja meg, azaz merre halad vagy néz.
    - destroyed: Egy boolean típusú változó és azt jelzi, hogy a mozgó objektum megsemmisült-e vagy sem.
    - owner: A Player osztályból objektum, amely a mozgó objektum tulajdonosát adja meg.

## Tank osztály

A tankot, mint mozgó objektumot valósítja meg, a MovingObject osztályból származik, ezért annak változóit örökli, azokon felül, pedig tartalmaz még egy JLabel objektum változót, amely a megjelenítéséért felel.

A konstruktor futása során a tank pozícióját a generált harcmező egy véletlenszerű mezőjére állítja, az irányát is véletlenszerűen választja meg, a destroyed változója false értéket veszi fel, valamint tulajdonosaként az adott játékost jelöli meg.

A továbbiakban az osztály függvényeinek leírása olvasható.

### addControl()

Egy publikus void függvény, amely feljogosítja a tank tulajdonosát a tank irányítására. A mozgáshoz a nyíl, a lövéshez a SPACE billentyűket állítja be. 

### shootMissile()

Egy publikus void függvény, amely a tank irányába egy lövedék objektumot hoz létre, majd változóit, a pozícióját, az irányát, a tulajdonosát és az elpusztítottság állapot tank ugyenezen változóinak értékével egyenlővé teszi.

### moveTankToNextPosition(int KeyCode)

Egy publikus void függvény, amely egy int változót kap paraméterként, amely az adott billentyű kódja.
Amennyiben az adott tank objektum kap a mozgatásához szükséges parancsot, az adott billentyű által, ez függvény valóstítja meg a mozgást. Elsőként azt ellenőrzi, hogy az irány megfelel-e az mozgás irányának, ha nem akkor az adott irányba fordítja, ha igen, akkor azt ellenőrzi, hogy a következő pozíció fal-e vagy út, ha út akokr tovább lép, ha fal, akkor pedig nem történik semmi.

### destroyTank()

Egy publikus void függvény, amely a tank elpusztításáért felel, azaz a destroy változót true-ra állítja.
Az elpusztított tank tulajdonosának felugrik egy ablak, ahol kiválaszthatja, hogy a továbbiakban a játékot a végéig tovább nézi vagy visszalép a főmenübe.

### getThisTankJLabel()

Egy publikus függvény, amely a tank objektum thisTankJLabel változóját adja vissza.

### getTankPosition()

Egy publikus függvény, amely a tank objektum position változóját adja vissza.

## Missile osztály

A lövedéket, mint mozgó objektumot valósítja meg, a MovingObject osztályból származik, ezért annak változóit örökli, azokon felül, pedig tartalmaz még egy JLabel objektum változót, amely a megjelenítéséért felel. A változóinak értékét nem a konstruktor, hanem a Tank osztály *shootMissile()* függvénye adja meg.

A továbbiakban az osztály függvényeit fogom ismeretetni.

### updateMissilePosition()

Egy publikus void függény, amely a lövedékek mozgatásáért felel és 150 ms-ként van meghívva minden lövedék objektum esetén.
Minden lövedék a direction változónak megfelelő irányba mozog, amíg falnak vagy Tanknak nem ütközik. Minden esetben ellenőrzi, hogy a következő mező fal-e vagy út, ha fal, akkor megsemmisül, azaz hívja a *destroyMissile()* függvényt. Ellenkező esetben ellenőrzi, hogy a következő mezőn tartózkodik-e tank objektum, amennyiben nem, akkor halad tovább, ha pedig igen, akkor mind a tank *destroyTank()*, mind a lövedék *destroyMissile()* függvénye meghívódik.

### destroyMissile()

Egy publikus void függvény, amely a lövedék elpusztításáért felel, azaz a destroy változót true-ra állítja.

### getThisMissileJLabel()

Egy publikus függvény, amely a lövedék objektum thisTankJLabel változóját adja vissza.

### getMissilePosition()

Egy publikus függvény, amely a lövedék objektum position változóját adja vissza.

## Player

Ez az osztály a játékosok azonosításáért felel.

Változók:
    - name: Publikus, sztring típusú változó, amely a játékos nevét adja meg.
    - ip: Publikus, Inet4Address típusú változól, amely a játékos ip-címét tárolja.
    - id: Publikus, int típusó változó, amely a játékos egyedi azonosításáért felel.

A konstruktor futása során az id változó értéke egy random számmal lesz egyenlő, ha a konstruktor kap egy sztringet is paraméterként, akkor a name változó is beállításra kerül. 

A továbbiakban a függvényeket ismertetem.

### setName(String name)

Egy publikus void függvény, amely a játékos nevét állítja be.

### setIp(Inet4Address ip)

Egy publikus void függvény, amely a játékos ip-jét állítja be.
