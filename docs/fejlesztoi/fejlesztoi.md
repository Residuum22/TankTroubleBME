# Fejlesztői dokumentáció - GUI

## TankTrouble

Ez az osztály az ősosztálya a játéknak.
### public void addNewOwnRoom(Room newOwnRoom)

Ez a függvény eltárolja a játékos saját szobáját, amennyiben rendelkezik ilyen objektummal.

### public void addNewListOfRemoteRooms(ArrayList<Room> newListOfRemoteRooms)

Ez a függvény frissíti a felfedezett szobák listáját úgy, hogy az előző listát minden elemét kitörli, majd a paraméterként megadott lista minden elemét hozzá adja a privát változóként eltárolt listához.

### public ArrayList<Room> getListOfRemoteRooms()

Ez a függvény a privát változóban eltárolt elérhető szobákat kérdezi le.

### public Room getOwnRoom()

Ez a függvény lekéri a játékos saját szobáját.

### public boolean hasOwnRoom()

Ez a függvény lekérdezi, hogy van-e a játékosnak saját szobája. Amennyiben van akkor a vissza térési érték igaz, azaz true.

### public String getThisPlayerName()

Ez a függvény lekéri ebben a játék kliensben játszó játékos játékosnevét.

### public Player getThisPlayer()

Ez a függvény lekéri ebben a játék kliensben játszó játékos Player objektumát.

### public void modifyPlayerName(String name)

Ez a függvény megváltoztatja az eltárolt játékosnevet.

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

## GameWindow

### Konstruktor 

A konstruktorban létrejön az ablak, illetve azt az alapvető funkcionalitás letiltásra kerül, hogy space gomb megnyomása esetén a kilépés a játékból funkció hívódjon meg. Továbbá hozzáadódnak a gombok eseményeinek lekezelésére szolgáló függvények. Ebben a konstruktorban létrejön továbbá egy időzítő, ami megadott (100 ms)-os időközönként meghívódik és a tankok helyzetét, illetve a lövedékek pozícióját frissíti ebben az ablakban. Ez a függvény figyeli továbbá, hogy hány tank helyezkedik el a pályán, mivel ha csak egyetlen egy tank marad, akkor az a játékos megnyerte a jáátékot.

###  public void generateBattlefield()

Ez a függvény hívja meg a Battlefield osztály pályagenerálás függvényét.

### public static Battlefield getBattlefield()

Ez a függvény visszaadja a legenerált csatateret.

### public void setBattleField(Battlefield newBattlefield)

Ez a függvény egy csatateret állít be ebben az objektumban.

### public void drawBattlefield()

Ez a függvény végzi el a csatatér koordinátáiból a kijelzőre történő kirajzolást, úgy hogy folyamatosan új JPaneleket ad hozzá ehhez az ablakhoz és ezeknek fal, vagy pálya tulajdonság esetén fekete illetve fehér hátteret ad. 


### public void updateScreen()

Ez a függvény egyszere végzi el a tankok helyzetének és a lövedékek helyzetének a kirajzolását. 

### public void updateTank()

Ez a függvény végzi el a Tank megjelenítését a tank jelenlegi koordinátája alapján. A megjelenítés úgy zajlik, hogy amennyiben a tank még nincs elpusztítva, akkor a tank helyén lévő JPanel hátterét módosítjuk és erre a JPanelre ráhelyezünk egy tank képet. A Tanknak a képe abba az irényba van elforgatva, amerre áll a tank. 

### public void updateMissile()

Ez a függvény végzi el a lövedék megjelenítését a koordinátája alapján. Ezt hasonlóképpen jelenítjük meg, mint a tankokat, annyi kivétellel, hogy ezt nem kell elforgatni.

### public void removeMissileFromList()

Ez a függvény eltávolítja a megsemmisült lövedéket a lövedékek listájából. 

### public void removeTankFromList()

Ez a függvény eltávolítja a tankot a tankok listájából.

### public static void setBattlefieldMissile(Missile newMissile)

Ez a függvény elhelyez a pályán egy új lövedéket.

###  private static BufferedImage rotateTankImage(BufferedImage bimg, Tank currentTank)

Ez a függvény végzi el e képnek a forgatását a tank pozíciója alapján, hogy a megjelenítéskor a tank lövegtornya mindig abba az irányba álljon, amerre a tank néz.

### public JFrame getGameWindowFrame()

Ez a függvény visszaadja az ablak leíró objektumát. 