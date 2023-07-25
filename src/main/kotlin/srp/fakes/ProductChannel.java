package srp.fakes;

public enum ProductChannel {
    CUSTOMER_APP,
    AGENT_APP,
    ADMIN_COMMANDO,
    KHAATA_APP;

    public static ProductChannel of(UserChannel userChannel) {
        switch (userChannel) {
            case COMMANDO, TELESALES, CORPORATE -> {
                return ADMIN_COMMANDO;
            }
            case CUSTOMER_APP -> {
                return CUSTOMER_APP;
            }
            case AGENT_APP -> {
                return AGENT_APP;
            }
            case KHAATA_APP -> {
                return KHAATA_APP;
            }
            default -> {
                return null;
            }
        }
    }

}

