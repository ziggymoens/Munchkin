package domein.repositories;

import connection.Connection;
import domein.DomeinController;
import domein.Spel;
import persistentie.mappers.PersistentieController;

import java.util.List;

public class SpelDbRepository {
    private PersistentieController pc;

    public SpelDbRepository() {
        if (Connection.isConnected()) {
            pc = new PersistentieController();
        }
    }

    public void spelOpslaan(Spel spel) {
        pc.spelOpslaan(spel);
    }

    public List<String> geefOverzicht() {
        return pc.getOverzicht();
    }

    public void laadSpel(int id, DomeinController dc) {
        dc.setSpel(pc.laadSpel(id));
    }

    public void verwijderOpgeslagenSpel(int id) {
        pc.remove(id);
    }

    public boolean bestaatSpel(int id) {
        return pc.bestaatSpel(id);
    }

    public void haalKaartenUitDb() {
        pc.haalKaartenUitDb();
    }
}
