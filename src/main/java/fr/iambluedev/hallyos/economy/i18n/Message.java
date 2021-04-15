package fr.iambluedev.hallyos.economy.i18n;

import fr.iambluedev.pine.api.field.IField;
import xyz.anana.pine.core.database.Database;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

import java.util.*;

public class Message {

    private Lang lang;
    private Map<String, String> cache;

    public Message(Lang lang) {
        this.lang = lang;
        this.cache = new HashMap<String, String>();
    }

    public String getTranslatedMessage(String message_key) {
        if (cache.containsKey(message_key)) {
            return cache.get(message_key);
        } else {
            cache.put(message_key, getMessage(message_key));
            return cache.get(message_key);
        }
    }

    public String getTranslatedMessage(String message_key, Boolean useCache) {
        if (useCache) {
            return getTranslatedMessage(message_key);
        } else {
            return getMessage(message_key);
        }
    }

    public String getMessage(String message_key) {
        Database database = Database.getInstance();
        Table i18n = (Table) database.getTable("hallyos_i18n");

        String msg = message_key;

        List<IField> where = new ArrayList<IField>(Arrays.asList(
                new Field("message_key", message_key),
                new Field("lang_id", this.lang.getId())
        ));

        if (i18n.exist("message_key", where)) {
            msg = (String) i18n.select("message_value", where);
        }

        return msg;
    }

    public void clearCache() {
        this.cache.clear();
    }
}