# BDD tesztek - Cucumber

## Szerelő javít tesztek

### Cső megjavítása
- Van egy elromlott cső, amin áll egy szerelő, és ezt megpróbálja megjavítani.
- Ezután a csőnek megjavítottnak kell lennie.

### Pumpa megjavítása
- Van egy elromlott pumpa, amin áll egy szerelő, és ezt megpróbálja megjavítani.
- Ezután a pumpának megjavítottnak kell lennie.

## Szabotőr akciók egy csövön tesztek

### Cső elrontása
- Van egy nem elromlott cső, amin áll egy szabotőr, és ezt megpróbálja elrontani.
- Ezután a csőnek elromlottnak kell lennie.

### Cső csúszóssá tevése
- Van egy cső, amin áll egy szabotőr, és ezt megpróbálja csúszóssá tenni.
- Ezután a csőnek csúszósnak kell lennie.

### Cső ragadóssá tevése
- Van egy cső, amin áll egy szabotőr, és ezt megpróbálja ragadóssá tenni.
- Ezután a csőnek ragadósnak kell lennie.

## Szerelő akciók egy ciszternán tesztek

### Cső elvétele
- Van egy ciszterna, amin áll egy szerelő, és megpróbál elvenni egy csövet a ciszternából.
- Ezután a ciszternában már csak 4 csőnek kell lennie, és a szerelőnél, kell aktív elemnek lennie.

### Pumpa elvétele
- Van egy ciszterna, amin áll egy szerelő, és megpróbál elvenni egy pumpát a ciszternából.
- Ezután a ciszternában már nem szabad pumpának lennie, és a szerelőnél, kell aktív elemnek lennie.

## Játékosok mozgatása tesztek

### Egy játékos egy csőre lép
- Egy szerelő a kezdőpozíciójában, éppen egy ciszternán áll. Tudván, hogy jobbra tőle egy üres cső található, átlép arra a mezőre.
- Az **assertEquals** összehasonlító függvénnyel ellenőrzi a teszt végén, hogy a szerelő jelenlegi mezője, amelyen tartózkodik valóban megváltozott-e a szomszédos csőre, illetve a szomszédos csövön beállítódik-e, hogy rajta tartózkodik a szerelő.

### Két játékos egy azonos pumpára lép
- Egy szerelő a kezdőpozíciójában, éppen egy ciszternán áll. Emellett egy másik játékost, a teszt esetében egy szabotőrt a ciszternától kettővel odébb lévő csőre van elhelyezve. A ciszterna és a cső közé egy pumpa lett letéve, amelyen egyszerre több játékos is tartózkodhat. Mindkét játékos átlép a mezőjükről a szomszédos pumpára.
- Az **assertEquals** összehasonlító függvénnyel ellenőrzi a teszt végén, hogy mind a szerelő és mind a szabötőr jelenlegi mezője, amelyen tartózkodnak valóban megváltozott-e a szomszédos pumpára, illetve a szomszédos pumpán beállítódik-e, hogy rajta tartózkodik a mindkét játékos.

## Összefoglalás
A szerintünk átfogó és részletes tesztek megírása után végre is hajtottuk őket, és mindegyikre az elvárt viselkedést kaptuk. A legfontosabb funkciókat többnyire sikerült letesztelni, azonban nehézségeket okozott pár helyen a Getter/Setter függvények hiánya, amely miatt pár esetet nem lehetett/nehezebb volt megvizsgálni. De a végrehajtott tesztek alapján a program helyesen funkcionálónak tűnik.