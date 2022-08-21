package controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.Data;

@Data
public class MessageManager {

    private static final String BUNDLE_NAME = "messages";
    private Locale locale;
    private final ResourceBundle resourceBundle;

    public MessageManager(Locale locale) {
        this.locale = locale;
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

}
