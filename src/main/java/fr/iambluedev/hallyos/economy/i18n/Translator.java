package fr.iambluedev.hallyos.economy.i18n;

import java.util.HashMap;
import java.util.Map;

public class Translator {

    private Map<Lang, Message> cache;

    public Translator(Lang... supportedLang) {
        this.cache = new HashMap<Lang, Message>();
        for (Lang lang : supportedLang) {
            this.cache.put(lang, new Message(lang));
        }
    }

    public String translate(Lang lang, String message_key, String... toReplace) {
        if (this.cache.containsKey(lang)) {
            String msg = this.cache.get(lang).getTranslatedMessage(message_key);
            for (int i = 0; i < toReplace.length; i++) {
                msg = msg.replace("{" + i + "}", toReplace[i]);
            }
            return msg.replace("&", "§");
        } else {
            return message_key;
        }
    }

    public void clearCache() {
        for (Map.Entry<Lang, Message> message : this.cache.entrySet()) {
            message.getValue().clearCache();
        }
    }


}
