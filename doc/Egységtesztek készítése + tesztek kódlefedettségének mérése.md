# Egységtesztek készítése + tesztek kódlefedettségének mérése

## Egységtesztek készítése

Az egységtesztek készítéséhez a **JUnit** és **Mockito** package-eket használtuk.

Nem minden osztály lett tesztelve, de azok, amelyeket teszteltünk, azok részletesen le lettek fedve.

Az egységtesztek a [`/src/test/java`](../src/test/java) mappában találhatóak.

A UI osztályok tesztelése nem volt célunk, mivel ezt a **_Manuális tesztek megtervezése_** feladat magában foglalja.

## Kódlefedettség mérése

A kódlefedettséget a **JaCoCo** segítségével mértük. A build konfigurációban be lett állítva, hogy a tesztek futtatása
után a JaCoCo generáljon egy jelentést.

Az elkészült jelentés a `/target/site/jacoco/index.html` fájl megnyitásával tekinthető meg.

A jelentések elemzése segített a le nem fedett ágak azonosításában.

Több fájl lefedettsége is eléri a 100%-ot.