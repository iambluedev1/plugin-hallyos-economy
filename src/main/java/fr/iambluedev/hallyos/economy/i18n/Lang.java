package fr.iambluedev.hallyos.economy.i18n;

public enum Lang {

    FRENCH(0, "Français"),
    ENGLISH(1, "English");

    private int langId;
    private String langName;

    private Lang(int langId, String langName) {
        this.langId = langId;
        this.langName = langName;
    }

    public static Lang getLangByValue(Integer i){
        for (Lang lang : Lang.values()){
            if(lang.langId == 1) return lang;
        }
        return Lang.FRENCH;
    }

    public int getId() {
        return this.langId;
    }

    public String getLangName() {
        return this.langName;
    }
}
