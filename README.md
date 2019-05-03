# RepoMunchkin - G35

## Door Kilian Hoefman, Ziggy Moens, Michiel Murphy & Jonathan Vanden Eynden Van Lysebeth

### Presentatie: 21/5/2019 - 15u10

Java applicatie starten via Main-package:
> [StartUp.java](/src/main/StartUp.java) -> CUI

> [StartUpGui.java](/src/main/StartUpGui.java) -> GUI


Applicatie runnen in Netbeans:
> De threads werken niet alsook de kleuren en opmaak van de CUI

Applicatie runnen in IntelliJ:
> Alles werkt, src-map aanduiden als Sources in Project Structure

**Deze applicatie is geschreven in en voor JAVA 10**

Project Structure:
- [Connection](src/connection)
- [Domein](/src/domein)
  * [Kaarten](/src/domein/kaarten)
  * [Repositories](/src/repositories)
  * [DomeinController](/src/domein/DomeinController.java)
  * [Spel](/src/domein/Spel.java)
  * [Speler](/src/doemin/Speler.java)
- [Exceptions](/src/exceptions)
- [Language](/src/language)
  * [Resourcebundle](/src/language/resourcebundle)
- [Main](/src/main)
  * [StartUp](/src/main/StartUp.java)
  * [StartUpGui](/src/main/StartUpGui.java)
- [Offline data](/src/offline_data)
- [Persistentie](/src/persistentie)
  * [Mappers](/src/persistentie/mappers)
  * [Persistentie controller](/src/persistentie/mappers/PersistentieController.java)
- [Printer](/src/printer)
- [UI](/src/ui)
  * [Use Case's CUI](/src/ui/cui/ucs)
  * [Use Case's GUI](/src/ui/gui)

