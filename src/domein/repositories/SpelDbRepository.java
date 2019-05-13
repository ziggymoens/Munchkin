package domein.repositories;

import connection.Connection;
import domein.DomeinController;
import domein.Spel;
import persistentie.mappers.PersistentieController;

import java.util.List;

@SuppressWarnings("unused")

public class SpelDbRepository {
    private PersistentieController pc;

    /**
     * Constructor voor SpelDbRepository
     */
    public SpelDbRepository() {
        if (Connection.isConnected()) {
            pc = new PersistentieController();
        }
    }

    /**
     * Methode om een spel op te slaan
     *
     * @param spel het huidige spel
     */
    public void spelOpslaan(Spel spel) {
        pc.spelOpslaan(spel);
    }

    /**
     * geeft overzicht van de spelen in de databank
     *
     * @return list met stings
     */
    public List<String> geefOverzicht() {
        return pc.getOverzicht();
    }

    /**
     * laad een gekozen spel uit de databank
     *
     * @param id id van het spel
     * @param dc huidige domeincontroler
     */
    public void laadSpel(int id, DomeinController dc) {
        dc.setSpel(pc.laadSpel(id));
    }

    /**
     * verwijderd een opgeslagen spel
     *
     * @param id id van het gekozen spel
     */
    public void verwijderOpgeslagenSpel(int id) {
        pc.remove(id);
    }

    /**
     * methode die controleer of een bepaald spel bestaat
     *
     * @param id id van spel
     * @return true = bestaat
     */
    public boolean bestaatSpel(int id) {
        return pc.bestaatSpel(id);
    }

    /**
     * haalt kaarten uit db
     */
    public void haalKaartenUitDb() {
        pc.haalKaartenUitDb();
    }
}
