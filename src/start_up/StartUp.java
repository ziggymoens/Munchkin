package start_up;

import domein.DomeinController;
import gui.UseCase1;

/**
 *
 * @author g35
 */
public class StartUp {
    //class die gebruikt wordt om use cases uit te voeren
    public static void main(String[] args) {
        DomeinController dc = new DomeinController();
        new UseCase1(dc);
    }
}
