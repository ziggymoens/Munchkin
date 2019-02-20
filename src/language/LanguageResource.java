/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import java.util.*;

/**
 *
 * @author ziggy
 */
public class LanguageResource {

    private Locale locale;
    private ResourceBundle bundle;

    public LanguageResource() {
        this(new Locale("en"));
    }
    
    public LanguageResource(Locale locale) {
        
        bundle = ResourceBundle.getBundle("language/i18n", this.locale);
    }

    public void setLocale(Locale locale) {
        if (locale.equals(new Locale("en"))||locale.equals(new Locale("nl"))||locale.equals(new Locale("fr"))) {
            this.locale = locale;
        }else{
            this.locale = new Locale("en");
        }
        
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public Locale getLocale() {
        return locale;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
    
    public String getStringLanguage(String string, Locale locale){
        return ResourceBundle.getBundle("language/i18n", locale).getString(string);
    }
    
    public String getString(String string){
        return bundle.getString(string);
    }
    
    
}
