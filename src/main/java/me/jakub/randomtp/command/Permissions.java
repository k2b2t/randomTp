package me.jakub.randomtp.command;

@SuppressWarnings("unused")
public enum Permissions {
    RTP_SELF("randomTp.rtp"),
    RTP_TEST("randomTp.rtp.test"),
    RTP_OTHERS("randomTp.rtp.others"),
    RTP_EVERYONE("randomTp.rtp.everyone"),
    RTP_ON_DEATH("randomTp.rtp.onDeath"),
    MNG_SETBORDER("randomTp.setborder"),
    MNG_RELOAD("randomTp.reload"),
    BYPASS_COOLDOWN("randomTp.cooldown.bypass"),
    BYPASS_COUNTDOWN("randomTp.countdown.bypass"),
    BYPASS_PRICE("randomTp.price.bypass"),
    SIGN_USE("randomTp.sign.use"),
    SIGN_CREATE("randomTp.sign.create"),
    SIGN_DESTROY("randomTp.sign.break");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String get() {
        return permission;
    }
}
