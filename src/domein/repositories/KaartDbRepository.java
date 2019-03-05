/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.repositories;

import domein.kaarten.Kaart;
import java.util.List;
import persistentie.KaartMapperDb;

/**
 *
 * @author ziggy
 */
public class KaartDbRepository {

    private final KaartMapperDb mapper;
    //private List<Speler> spelers; //de lijst wordt eigenlijk niet gebruikt 

    public KaartDbRepository() {
        mapper = new KaartMapperDb();
        //spelers = new ArrayList<>();
    }

    public List<Kaart> geefSpelers() {
        return mapper.geefSpelers();
    }

    
    
}
