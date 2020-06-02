package over.config;

import java.util.ResourceBundle;

public class Language {
    private String file;
    private final String PATH = "over/config/";
    private static Language language;

    private Language() {
    }

    public static Language getLanguage() {
        if(language == null)
            language = new Language();

        return language;
    }

    public void setFile(String fileName) {
        file = fileName;
    }

    public String getFile() {
        return file;
    }

    public String getProperty(String property) {
        return ResourceBundle.getBundle(PATH + file).getString(property);
    }
}