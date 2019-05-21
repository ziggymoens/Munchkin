package domein;

import domein.repositories.SpelDbRepository;
import exceptions.SpelException;
import exceptions.database.InternetException;
import language.LanguageResource;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ziggy
 */
public class DomeinController {

    public Spel spel;
    private SpelDbRepository sr;

    /**
     * Lege constructor DomeinController
     */
    public DomeinController() {

    }

    public void laadSpelRepo(){
        sr = new SpelDbRepository();
    }

    /**
     * Methode om het spel te starten adhv het aantal spelers en de gekozen taal
     *
     * @param aantalSpelers Gekozen aantal spelers (3-6)
     */
    public void startSpel(int aantalSpelers) {
        if (aantalSpelers < 3 || aantalSpelers > 6) {
            throw new SpelException("exception.spel.players");
        }
        spel = new Spel(aantalSpelers);
    }

    /**
     * String die info geeft over het spel en de spelers
     *
     * @return String met alle informatie
     */
    public String geefInformatie() {
        StringBuilder ret = new StringBuilder();
        String[] sInfo = spel.geefInfo();
        int index = 1;
        for (String lijn : sInfo) {
            ret.append(String.format("%s %d: %s%n", LanguageResource.getString("player"), index, lijn));
            index++;
        }
        return ret.toString();
    }

    /**
     * Methode die methode uit spel aanroept die controle doet op volgorde
     */
    public void controleerVolgorde() {
        spel.controleerVolgorde();
    }

    /**
     * Methode die de naam van de speler retourneert adhv de index waarop deze in de spelerslijst staat -- roept spel.getSpelers() aan + getNaam().
     * @param i index van de speler
     * @return naam van de speler
     */
    public String geefNaamSpeler(int i) {
        return spel.getSpelers().get(i).getNaam();
    }

    public String geefGeslachtSpeler(int i){
        return spel.getSpelers().get(i).getGeslacht();
    }

    /**
     * Methode die het level van de speler retourneert adhv de index waarop deze in de spelerslijst staat -- roept spel.getSpelers aan + getLevel().
     * @param i index
     * @return level van de speler op die index
     */
    public int geefLevel(int i) {
        return spel.getSpelers().get(i).getLevel();
    }

    /**
     * Methode die de naam van de winnaar teruggeeft als deze level 10 bereikt. -- roept spel.geefWinnaar() aan.
     * @return naam van de winnaar
     */
    public String geefNaamWinnaar() {
        return spel.geefWinnaar();
    }


    /**
     * Methode die spel opslaat. -- roept de spelerDBRepository op om daar de methode spelOpslaan aan te roepen
     */
    public void spelOpslaan() {
        sr.spelOpslaan(this.spel);
    }

//    /**
//     * @return
//     */
//    public String geefOpgeslagenSpellen() {
//        List<Spel> spellen = sr.getSpellen();
//        StringBuilder ret = new StringBuilder();
//        int index = 1;
//        for (Spel spel : spellen) {
//            ret.append(String.format("Spel %d%n%s", index, spel.toString()));
//        }
//        return ret.toString();
//    }

//    /**
//     * Spel laden
//     *
//     * @param index
//     */
//    public void spelLaden(int index) {
//        this.spel = sr.getSpellen().get(index - 1);
//    }

    /**
     * Methode die de spelsituatie weergeeft. -- roept hiervoor spel.geefSpelsituatie op.
     * @return de spelsituatie
     */
    public List<String> geefSpelsituatie() {
        return spel.geefSpelsituatie();
    }

    /**
     * Methode die de beknopte spelsituatie weergeeft. -- roept hiervoor spel.geefBeknopteSpelsituatie aan.
     * @return de beknopte spelsituatie
     */
    public List<String> geefBeknopteSpelsituatie(/*boolean vecht*/){
        return spel.geefBeknopteSpelsituatie(/*vecht*/);
    }   

    /**
     * Methode die een boolean retourneert wanneer niemand gewonnen is. -- roept hiervoor spel.niemandGewonnen aan.
     * @return wanneer niemand gewonnen is
     */
    public boolean niemandGewonnen() {
        return spel.niemandGewonnen();
    }

    /**
     * Methode die speler een naam geeft. -- roept spel.geefSpelerNaam aan.
     * @param i index van de speler in de spelerlijst
     * @param naam van de speler
     */
    public void geefSpelerNaam(int i, String naam) {
        spel.geefSpelerNaam(i, naam);
    }

    /**
     * Methode die een nieuwe speler aanmaakt. -- roept spel.maakNieuweSpeler aan.
     */
    public void maakSpeler() {
        spel.maakNieuweSpeler();
    }

    /**
     * Methode die speler op bepaalde index in de spelerlijst een geslacht toekent. -- roept spel.geefSpelerGeslacht aan.
     * @param i index
     * @param geslacht
     */
    public void geefSpelerGeslacht(int i, String geslacht) {
        spel.geefSpelerGeslacht(i, geslacht);
    }

    /**
     * Methode die startkaarten aan de spelers geeft. -- roept spel.geefStartKaarten aan.
     */
    public void geefStartKaarten() {
        spel.geefStartKaarten();
    }

    /**
     * Methode die de bovenste Kerkerkaart teruggeeft als string.format + de afbeelding. -- roept spel.toonBovensteKk() aan.
     * @return bovenste kerkerkaart als string
     */
    public String toonBovensteKk() {
        return String.format("/ui/images/kaarten/%d.png", spel.toonBovensteKk());
    }

    /**
     * Methode die adhv het id het type van de kaart teruggeeft als string. -- roept spel.geefTypekaart(id) op.
     * @param id van de kaart
     * @return String typekaart
     */
    public String geefTypeKaart(int id) {
        return spel.geefTypeKaart(id);
    }

    /**
     * Methode die de curse kaart speelt. -- roept spel.speelCurse(naam) aan.
     * @param naam van de speler
     */
    public void curseKaart(String naam) {
        spel.speelCurse(naam);
    }

    /**
     * Methode die het id van de bovenste kerkerkaart op de stapel teruggeeft. -- roept spel.toonBovensteKk() aan.
     * @return id van de bovenste kaart
     */
    public int geefIdBovensteKaart() {
        return spel.toonBovensteKk();
    }

    /**
     * Methode die bovenste kerkerkaart verwijderd en een nieuwe ophaalt.
     */
    public void nieuweBovensteKaartK() {
        spel.nieuweBovensteKaartK();
    }

    /**
     * Methode die kerkerkaart aan de speler geeft adhv van de naam vd speler. -- roept spel.geefKerkerKaartAanSpeler(naam) op.
     * @param naam van de speler
     */
    public void geefKerkerkaartAanSpeler(String naam) {
        spel.geefKerkerkaartAanSpeler(naam);
    }

    /**
     * public String geefTypeLostCurse() {
     * return spel.geefTypeLostCurse().toLowerCase();
     * }
     * <p>
     * public String toonItemsSpeler(String naam) {
     * int i = 0;
     * String ret = "";
     * for (String line : spel.geefItemsSpeler(naam)){
     * ret += String.format("%d) %s%n", i,line);
     * i++;
     * }
     * return ret;
     * }
     * <p>
     * public int geefAantalItemsSpeler(String naam) {
     * if (spel.geefAantalItemsSpeler(naam) == -99){
     * throw new SpelException("exception.spel.itemsspeler");
     * }
     * return spel.geefAantalItemsSpeler(naam);
     * }
     * <p>
     * public void verwijderItemSpeler(String naam, int keuze) {
     * spel.verwijderItemSpeler(naam, keuze-1);
     * }
     */

    /**
     * Methode die een naam aan het spel geeft. -- roept spel.setNaam(naam) op.
     * @param naam van het spel
     */
    public void geefSpelNaam(String naam) {
        spel.setNaam(naam);
    }

    /**
     * Methode die bovenste kaart to string returnt. -- roept spel.bovensteKaartToString() aan.
     * @return String van bovenste kaart.
     */
    public String bovensteKaartToString() { return spel.bovensteKaartToString(); }

    /**
     * Methode die volgende speler aan beurt zet. -- roept spel.setSpelerAanBeurt(i) aan.
     * @param i index van de speler
     */
    public void zetSpelerAanBeurt(int i) {
        spel.setSpelerAanBeurt(i);
    }

    /**
     * Methode die de volgende speler aan beurt teruggeeft als integer. -- roept spel.getSpelerAanBeurt() op.
     * @return int van de volgende speler aan beurt.
     */
    public int geefSpelerAanBeurt(){
        return spel.getSpelerAanBeurt();
    }

    /**
     * Methode die string retour van de kaarten die naar items kunnen. -- roept spel.geefKaartenKunnenNaarItems(naam) aan.
     * @param naam van de speler
     * @return String van kaarten die naar items kunnen
     */
    public String geefKaartenKunnenNaarItems(String naam) {
        return spel.geefKaartenKunnenNaarItems(naam);
    }

    /**
     * Methode die lijst van ids teruggeeft van de kaarten die naar items kunnen. -- roept spel.geefIdKaartenNaarItems(naam) aan.
     * @param naam van de speler
     * @return List van Integers van ids vd kaarten die naar items kunnen.
     */
    public List<Integer> geefIdsKunnenNaarItems(String naam){
        return spel.geefIdKaartenNaarItems(naam);
    }

    /**
     * Methode die String teruggeeft van verkoopbare kaarten adhv de naam vd speler. -- spel.geefVerkoopbareKaarten(naam) aan.
     * @param naam van de speler
     * @return String van de verkoopbare kaarten
     */
    public String geefVerkoopbareKaarten(String naam) {
        return spel.geefVerkoopbareKaarten(naam);
    }

    /**
     * Methode die lijst van ids teruggeeft van de kaarten die verkoopbaar zijn. -- roept spel.geefIdVerkoopbarekaarten(naam) aan.
     * @param naam van de speler
     * @return List van Integers met de ids van de kaarten die verkocht kunnen worden.
     */
    public List<Integer> geefIdVerkoopbareKaarten(String naam){
        return spel.geefIdVerkoopbarekaarten(naam);
    }

    /**
     * Methode die string teruggeeft van de kaarten die niet verkoopbaar zijn. -- roept spel.geefNietVerkoopbareKaarten(naam) aan.
     * @param naam van de speler
     * @return String van de niet verkoopbare kaarten
     */
    public String geefNietVerkoopbareKaarten(String naam) {
        return spel.geefNietVerkoopbareKaarten(naam);
    }

    /**
     * Methode die level verhoogt wanneer kaart verkocht wordt. -- roept spel.verhoogLevel(naam, levelUp) op.
     * @param naam van de speler
     * @param levelUp hoeveel levels gewonnen
     */
    public void verhoogLevel(String naam, int levelUp){
        spel.verhoogLevel(naam, levelUp);
    }

    /**
     * Methode die het resultaat van het gevecht bepaalt.
     * @param monster id
     * @param speler id
     * @return boolean gewonnen of niet
     */
    public boolean gevechtResultaat(int monster, int speler){
        return spel.gevechtResultaat(monster, speler);
    }

    /**
     * Methode die aantal kaarten van een bepaalde speler retourt als integer. -- roept spel.getAantalKaarten(naam) aan.
     * @param naam van de speler
     * @return int aantal kaarten
     */
    public int getAantalKaarten(String naam){
        return spel.getAantalKaarten(naam);
    }

    public Object geefMonsterAttribuut(int id, String soort){
        return spel.geefMonsterAttribuut(id, soort);
    }

    /**
     * ongebruikt
     * @return
     */
    public boolean geefDatabank() {
        return spel.isKlein();
    }

    /**
     * Methode die de waarde van een schatkaart met een bepaalt id teruggeeft. -- roept spel.getWaardeSchatkaart(id) aan.
     * @param id van de kaart
     * @return int waarde van de kaart met id
     */
    public int getWaardeSchatkaart(int id){
        return spel.getWaardeSchatkaart(id);
    }

    /**
     * Methode die overzicht van de spelen teruggeeft. -- roept sr.geefOverzicht() aan.
     * @return List van Strings met overzicht spelen
     */
    public List<String> geefOverzichtSpelen() {
        return sr.geefOverzicht();
    }

    /**
     * Setter voor het spel te setten op het huidige spel via this.spel = spel.
     * @param spel
     */
    public void setSpel(Spel spel) {
        this.spel = spel;
    }

    /**
     * Methode die spel laadt. -- roept sr.laadSpel(id, this (constructor van domeincontroller)) aan.
     * @param id van het spel
     */
    public void laadSpel(int id) {
        sr.laadSpel(id, this);
    }

    /*public void verwijderOpgeslagenSpel(int id) {
        sr.verwijderOpgeslagenSpel(id);
    }*/

    /**
     * Methode die boolean teruggeeft of het spel reeds bestaat. -- roept sr.bestaatSpel(id) aan.
     * @param id van het spel
     * @return true = ja het bestaat reeds
     */
    public boolean bestaatSpel(int id) {
        return sr.bestaatSpel(id);
    }

    /**
     * Methode die overzicht van de kaarten in hand van de speler teruggeeft. -- roept spel.toonOverzichtKaartenInHand2(speler) aan.
     * @param speler
     * @return List van Strings met overzicht van de kaarten in hand.
     */
    public List<String> toonOverzichtKaartenInHand2(int speler){
        return spel.toonOverzichtKaartenInHand2(speler);
    }


    public List<String> geefTegenspelers(){
        List<Speler> spelers = spel.getSpelers();
        List<String> tegenspelers = new ArrayList<>();
        for(int i = 0; i<tegenspelers.size(); i++){
            if(i != geefSpelerAanBeurt()){
                tegenspelers.add(spelers.get(i).getNaam());
            }
        }
        return tegenspelers;
    }

    /**
     * Methode die een dobbelsteen gooit (random waarde 1-6). -- roept spel.gooiDobbelsteen() aan.
     * @return int met waarde van worp
     */
    public int gooiDobbelsteen(){
        return spel.gooiDobbelsteen();
    }

    //weg
    public Speler geefSpeler(int i){
        return spel.geefSpeler(i);
    }

    /**
     * Methode die aantal spelers teruggeeft. -- roept spel.getAantalSpelers() aan.
     * @return int met aantal spelers
     */
    public int geefAantalSpelers() {
        return spel.getAantalSpelers();
    }

    /**
     * Methode die controleert of speler geconnecteerd is met het internet
     */
    public void checkConnection() {
        try {
            String host="redhat.com";
            int port=80;
            int timeOutInMilliSec=5000;// 5 Seconds
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), timeOutInMilliSec);
        }catch (Exception e){
            throw new InternetException();
        }
    }

    /**
     * Methode die een lijst van ids teruggeeft van de kaarten die zich in de hand van de speler bevinden. -- roept spel.geefIDKaartenInHand(naam) aan.
     * @param naam van de speler
     * @return List van Integers die het id van de kaart teruggeven.
     */
    public List<Integer> geefIDKaartenInHand(String naam) {
        return spel.geefIDKaartenInHand(naam);
    }

    /**
     * Methode die battlepoints van het monster instelt. -- roept spel.setMonsterBattlepoints(monsterBattlePoints) aan.
     * @param monsterBattlePoints int battlepoints
     */
    public void setMonsterBattlePoints(int monsterBattlePoints) {
        spel.setMonsterBattlePoints(monsterBattlePoints);
    }

    /**
     * Methode die battlepoints van de speler instelt. -- roept spel.setSpelerBattlepoints(spelerBattlePoints) aan.
     * @param spelerBattlePoints
     */
    public void setSpelerBattlePoints(int spelerBattlePoints) {
        spel.setSpelerBattlePoints(spelerBattlePoints);
    }

    /**
     * Methode die het aantal battlepoints van het monster teruggeeft. -- roept spel.getMonsterBattlePoints() aan.
     * @return int met aantal battlepoints van het monster
     */
    public int getMonsterBattlePoints() {
        return spel.getMonsterBattlePoints();
    }

    /**
     * Methode die het aantal battlepoints van de speler teruggeeft. -- roept spel.getSpelerBattlePoints() aan.
     * @return int met aantal battlepoints van de speler
     */
    public int getSpelerBattlePoints() {
        return spel.getSpelerBattlePoints();
    }
    /**
     * Methode die kaart onderaan de stapel toevoegt.
     * @param kaart
     */

    public void voegKaartOnderaanStapelToe(int kaart){
        spel.voegKaartOnderaanStapelToe(kaart);
    }

    /**
     * Methode die kaarten weggooit. -- roept spel.gooiKaartenWeg(naam, gekozenKaarten) aan.
     * @param naam van de speler
     * @param gekozenKaarten die weggegooid moeten worden
     */
    public void gooiKaartenWeg(String naam, List<Integer> gekozenKaarten) {
        spel.gooiKaartenWeg(naam, gekozenKaarten);
    }

    /**
     * Methode die kaarten verkoopt. -- roept spel.verkoopKaarten(naam, gekozenKaarten) aan.
     * @param naam van de speler
     * @param gekozenKaarten die verkocht moeten worden
     */
    public void verkoopKaarten(String naam, List<Integer> gekozenKaarten) {
        spel.verkoopKaarten(naam, gekozenKaarten);
    }

    /**
     * Methode die kaarten naar items verplaatst. -- roept spel.verplaatsNaarItems(naam, gekozenKaarten) aan.
     * @param naam van de speler
     * @param gekozenKaarten die naar items moeten
     */
    public void verplaatsNaarItems(String naam, List<Integer> gekozenKaarten) {
        spel.verplaatsNaarItems(naam, gekozenKaarten);
    }

    /**
     * Methode die de kaarten uit de databank haalt. -- roept sr.haalKaartenUitDb() aan.
     */
    public void haalKaartenUitDb() {
        sr.haalKaartenUitDb();
    }

    /**
     *  Methode die set of de speler hulp wilt tegen een monster
     * @param help Ja of nee (in de juiste taal)
     */
    public void setHelp(String help){
        spel.setHelp(help);
    }

    /**
     * Setter die set wie er mee doet aan het gevecht
     * @param helptmee
     */

    public void setHelptmee(List<Boolean> helptmee){
        spel.setHelptmee(helptmee);
    }

    /**
     * Setter die set wie er aan beurt is binnenin het gevecht (hangt los van spelerAanBeurt)
     * @param spelerAanBeurt geeft de speler die de kaart trekt mee
     */

    public void setSpelerAanBeurtGevecht(int spelerAanBeurt) {
        spel.setSpelerAanBeurtGevecht(spelerAanBeurt);
    }

    /**
     * Getter die teruggeeft of de speler hulp wilt bij het vechten
     * @return
     */

    public String getHelp(){
        return spel.getHelp();
    }

    /**
     * Getter die een Lijst teruggeeft met mensen die helpen en niet
     * @return
     */

    public List<Boolean> gethelptmee(){
        return spel.getHelptmee();
    }

    /**
     * Methode die bepaalt hoeveel levels er bij de spelerzijde moeten geteld worden als spelers meehelpen in het gevecht
     * @return extralevels dat er bij moet geteld worden
     */

    public int spelerLevels(){
        return spel.spelerLevels();
    }

    /**
     *  Methode die badstuff uitvoert
     * @param id van speler
     */

    public void voerBadStuffUit(int id){
        spel.voerBadStuffUit(id);
    }

    /**
     * Getter die teruggeeft wie er aan beurt is binninenin gevecht
     * @return
     */
    public int getSpelerAanBeurtGevecht() {
        return spel.getSpelerAanBeurtGevecht();
    }
    /**
     * Methode die valideert of de Speler de kaart mag leggen adhv zijn positie in het spel (hij een helper is/tegenspeler of degene die de kaart trekt)
     * @param kaart int kaart die volgt uit de keuze van de speler
     * @return
     */

    public boolean validatieKaartSpeler(int kaart){
        return spel.validatieKaartSpeler(kaart);
    }

    /**
     * Methode die valideert of de speler de Kaart mag spelen adhv zijn positie in het spel (hij een helper is/tegenspeler of degene die de kaart trekt)
     * @param kaart kaart die meegegevn wordt
     * @param monster boolean die bepaalt of er een monster mag gespeeld worden of niet
     * @return boolean die bepaalt of de kaart mag gespeeld worden
     */
    public boolean validatieKaartSpeler(int kaart, boolean monster){
        return spel.validatieKaartSpeler(kaart, monster);
    }

    /**
     *  Methode die bepaalt of de speler de kaart mag spelen adhv de items die de persoon heeft
     * @param kaart de kaart die meegegeven wordt
     * @return boolean die bepaalt of de kaart gespeeld mag worden
     */
    public boolean validatieKaartItems(int kaart){
        return spel.validatieKaartItems(kaart);
    }

    /**
     * Methode die bepaalt of de speler de kaart mag spelen adhv de items die de persoon heeft
     * @param kaart ID van de kaart
     * @return true or false
     */
    public boolean validatieKaartItems2(int kaart){
        return spel.validatieKaartItems2(kaart);
    }

    /**
     * Methode die cursekaart speelt
     * @param speler id van speler
     * @param kaart kaart die gespeeld moet worden
     */
    public void speelCurse(int speler, int kaart){
        spel.speelCurse(speler, kaart);
    }

    /**
     * Methdode die een Monster speelt
     * @param kaart kaart die gespeeld moet worden
     * @param monster of er een monster gespeeld mag worden
     */
    public void speelMonster(int kaart, boolean monster){
        spel.speelMonster(kaart, monster);
    }

    /**
     * Methode die een Consumable speelt
     * @param kaart
     */
    public void speelConsumable(int kaart){
        spel.speelConsumable(kaart);
    }

    /**
     * Methode die items bijvoegt als het een Ras of Weapon is bij items
     * @param kaart
     */
    public void itemsBijvoegen(int kaart){
        spel.itemsBijvoegen(kaart);
    }

    /**
     * Methode die de kaarten van een speler teruggeeft
     * @param naam van de speler
     * @return String van kaarten
     */
    public String geefKaartenVanSpeler(String naam){
        return spel.geefKaartenVanSpeler(naam);
    }

    /**
     * Geeft items van speler terug
     * @param naam van speler
     * @return String van items van speler
     */
    public String geefItemsVanSpeler(String naam){
        return spel.geefItemsVanSpeler(naam);
    }

    /**
     * Geeft een array van kaarten terug
     * @param naam van speler
     * @return kaarten in een array van ints
     */
    public int[] geefKaartenVanSpelerInt(String naam){
        return spel.geefKaartenVanSpelerInt(naam);
    }

    /**
     * Geeft een array van items terug
     * @param naam van speler
     * @return items in een array van ints
     */
    public int[] geefItemsVanSpelerInt(String naam){
        return spel.geefItemsVanSpelerInt(naam);
    }

    /**
     * Geeft een overzicht in de juiste volgorde van de spelers
     * @return
     */
    public List<String[]> spelerOverzichtVolgorde(){
        return spel.geefspelerOverzichtVolgorde();
    }

    /**
     * Methode die bepaalt wat voor soort kaart het is
     * @param kaart kaart
     * @param monster of er een monster gespeeld mag worden
     * @return String van het soort kaart
     */
    public String controleWelkeKaart(int kaart, boolean monster){
       return  spel.controleWelkeKaart(kaart, monster);
    }

    /**
     * Methode die een specifiek aantal schatkaarten uitdeelt aan de speler(s) die gewonnen hebben met het gevecht
     * @param speler die de schatkaarten moet krijgen
     * @param schatten aantal schatten dat de speler moet krijgen
     */
    public void deelSchatkaartenUit(int speler, int schatten){
        spel.deelSchatkaartenUit(speler, schatten);
    }

    public DomeinController geefDomeinController() {
        return this;
    }

    public String geefRaceSpeler(int i){
        return spel.geefRaceSpeler(i);
    }
}


