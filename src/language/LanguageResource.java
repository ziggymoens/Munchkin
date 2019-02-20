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
public final class LanguageResource {

    private Locale locale;
    private ResourceBundle bundle;

    public LanguageResource() {
        setLocale(new Locale("en"));
        setBundle();
}
    
    public LanguageResource(Locale locale) {
        setLocale(locale);
        setBundle();
        
    }

    public void setLocale(Locale locale) {
        if (locale.toString().equals("en")||locale.toString().equals("fr")||locale.toString().equals("nl")) {
            this.locale = locale;
            setBundle();
        }else{
            this.locale = new Locale("en");
        }
        
    }

    public final void setBundle() {
        this.bundle = ResourceBundle.getBundle("language/i18n", getLocale());
    }

    public Locale getLocale() {
        return this.locale;
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
