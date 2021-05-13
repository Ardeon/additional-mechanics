package ru.ardeon.additionalmechanics.configs;

public enum Setting {
    //Altar
    ALTAR_ENABLE("altar.enable",true),
    ALTAR_DISCHARGED_TEXT("altar.discharged-text","Заряд алтаря - [§cРазряжен§f]"),
    ALTAR_CHARGE_TEXT("altar.charge-text","Заряд алтаря"),
    ALTAR_MOON_TITLE_TEXT("altar.moon-title-text","Незеритовая луна"),
    ALTAR_MOON_SUBTITLE_TEXT("altar.moon-subtitle-text","перемотка времени невозможна"),
    ALTAR_BROADCAST_TEXT("altar.broadcast-text","§7Кто-то пришёл к алтарю и запустил ход времени"),
    ALTAR_DISCHARGED_MASSAGE("altar.discharged-massage","§3Алтарь разряжен. §7приходите позже")
    //AntiTarget

    //Skills

    //Sidebar

    //Horses

    //bloodmoon

    //

    ;
    private String path;
    private Object value;

    Setting(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    public boolean getBool(){
        return (boolean) value;
    }
    public String getString(){
        return value.toString();
    }
}
