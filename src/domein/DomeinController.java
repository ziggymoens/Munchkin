package domein;

import domein.kaarten.Schatkaart;
import domein.kaarten.kerkerkaarten.monsterbadstuff.BadStuff;
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

    private Spel spel;
    private SpelDbRepository sr;

    /**
     * Constructor DomeinController
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
     *
     */
    public void controleerVolgorde() {
        spel.controleerVolgorde();
    }

    /**
     * @param i
     * @return
     */
    public String geefNaamSpeler(int i) {
        return spel.getSpelers().get(i).getNaam();
    }

    /**
     * @param i
     * @return
     */
    public int geefLevel(int i) {
        return spel.getSpelers().get(i).getLevel();
    }

    /**
     * @return
     */
    public String geefNaamWinnaar() {
        return spel.geefWinnaar();
    }


    /**
     * Spel opslaan
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
     * @return
     */
    public List<String> geefSpelsituatie() {
        return spel.geefSpelsituatie();
    }
    public List<String> geefBeknopteSpelsituatie(/*boolean vecht*/){
        return spel.geefBeknopteSpelsituatie(/*vecht*/);
    }   

    /**
     * @return
     */
    public boolean niemandGewonnen() {
        return spel.niemandGewonnen();
    }

    /**
     * @param i
     * @param naam
     */
    public void geefSpelerNaam(int i, String naam) {
        spel.geefSpelerNaam(i, naam);
    }

    /**
     *
     */
    public void maakSpeler() {
        spel.maakNieuweSpeler();
    }

    /**
     * @param i
     * @param geslacht
     */
    public void geefSpelerGeslacht(int i, String geslacht) {
        spel.geefSpelerGeslacht(i, geslacht);
    }

    /**
     *
     */
    public void geefStartKaarten() {
        spel.geefStartKaarten();
    }

    /**
     * @return
     */
    public String toonBovensteKk() {
        return String.format("ui/images/%d.png", spel.toonBovensteKk());
    }

    /**
     * @param id
     * @return
     */
    public String geefTypeKaart(int id) {
        return spel.geefTypeKaart(id);
    }

    /**
     * @param naam
     */
    public void curseKaart(String naam) {
        spel.speelCurse(naam);
    }

    /**
     * @return
     */
    public int geefIdBovensteKaart() {
        return spel.toonBovensteKk();
    }

    /**
     *
     */
    public void nieuweBovensteKaartK() {
        spel.nieuwBovensteKaartK();
    }

    /**
     * @param naam
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

    public void geefSpelNaam(String naam) {
        spel.setNaam(naam);
    }

    public String bovensteKaartToString() { return spel.bovensteKaartToString(); }

    public void zetSpelerAanBeurt(int i) {
        spel.setSpelerAanBeurt(i);
    }

    public int geefSpelerAanBeurt(){
        return spel.getSpelerAanBeurt();
    }

    public String geefKaartenKunnenNaarItems(String naam) {
        return spel.geefKaartenKunnenNaarItems(naam);
    }

    public List<Integer> geefIdsKunnenNaarItems(String naam){
        return spel.geefIdKaartenNaarItems(naam);
    }

    public String geefVerkoopbareKaarten(String naam) {
        return spel.geefVerkoopbareKaarten(naam);
    }

    public List<Integer> geefIdVerkoopbareKaarten(String naam){
        return spel.geefIdVerkoopbarekaarten(naam);
    }

    public List<Integer> geefIdKaartenNaarItems(String naam){
        return spel.geefIdKaartenNaarItems(naam);
    }

    public void verwijderKaart(int id){
        spel.verwijderKaart(id);
    }

    public String geefNietVerkoopbareKaarten(String naam) {
        return spel.geefNietVerkoopbareKaarten(naam);
    }

    public void verhoogLevel(String naam, int levelUp){
        spel.verhoogLevel(naam, levelUp);
    }

    public boolean gevechtResultaat(int monster, int speler){
        return spel.gevechtResultaat(monster, speler);
    }

    public int getAantalKaarten(String naam){
        return spel.getAantalKaarten(naam);
    }

    public Object geefMonsterAttribuut(int id, String soort){
        return spel.geefMonsterAttribuut(id, soort);
    }

    public boolean geefDatabank() {
        return spel.isKlein();
    }

    public List<Integer> getWaardeSchatkaart(){
        return spel.getWaardeSchatkaart();
    }

    public int getWaardeSchatkaart(int id){
        return spel.getWaardeSchatkaart(id);

    }

    public List<String> geefOverzichtSpelen() {
        return sr.geefOverzicht();
    }

    public void setSpel(Spel spel) {
        this.spel = spel;
    }

    public void laadSpel(int id) {
        sr.laadSpel(id, this);
    }

    public void verwijderOpgeslagenSpel(int id) {
        sr.verwijderOpgeslagenSpel(id);
    }

    public boolean bestaatSpel(int id) {
        return sr.bestaatSpel(id);
    }

    public BadStuff geefBadStuff(int id){
        return spel.geefBadStuff(id);
    }

    public Schatkaart geefSchatkaart(){ return spel.geefSchatkaart(); }
    
    public String toonOverzichtKaartenInHand(int speler) {
        return spel.toonOverzichtKaartenInHand(speler);
    }
        //fix
    
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
    public int gooiDobbelsteen(){
        return spel.gooiDobbelsteen();
    }

    public Speler geefSpeler(int i){
        return spel.geefSpeler(i);
    }

    public int geefAantalSpelers() {
        return spel.getAantalSpelers();
    }

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

    public List<Integer> geefIDKaartenInHand(String naam) {
        return spel.geefIDKaartenInHand(naam);
    }



    public void setMonsterBattlePoints(int monsterBattlePoints) {
        spel.setMonsterBattlePoints(monsterBattlePoints);
    }

    public void setSpelerBattlePoints(int spelerBattlePoints) {
        spel.setSpelerBattlePoints(spelerBattlePoints);
    }

    public int getMonsterBattlePoints() {
        return spel.getMonsterBattlePoints();
    }

    public int getSpelerBattlePoints() {
        return spel.getSpelerBattlePoints();
    }

    public void gooiKaartenWeg(String naam, List<Integer> gekozenKaarten) {
        spel.gooiKaartenWeg(naam, gekozenKaarten);
    }

    public void verkoopKaarten(String naam, List<Integer> gekozenKaarten) {
        spel.verkoopKaarten(naam, gekozenKaarten);
    }

    public void verplaatsNaarItems(String naam, List<Integer> gekozenKaarten) {
        spel.verplaatsNaarItems(naam, gekozenKaarten);
    }
}
