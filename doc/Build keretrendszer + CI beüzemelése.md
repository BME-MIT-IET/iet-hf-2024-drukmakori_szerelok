# Build keretrendszer + CI beüzemelése

## Build keretrendszer

A build keretrendszer beüzemeléséhez a Maven-t választottuk.

A [`pom.xml`](..\pom.xml) fájl tartalmazza a projekt függőségeit és a build konfigurációját.

A build parancs: `mvn clean package assembly:single`. Ez létrehozza a `target` mappában a futtatható `.jar` fájlt.

A tesztek az `mvn test` paranccsal futtathatóak.

## CI beüzemelése

A CI beüzemeléséhez a GitHub Actions-t választottuk.

A CI konfigurációja a [`.github/workflows/maven.yml`](../.github/workflows/maven.yml) fájlban található.

A `main` branch-en minden push-ra és pull request-re lefut. A Maven paranccsal buildeli a projektet és futtatja a teszteket.