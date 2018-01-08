package cz.muni.fi.pa165.rest;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class ApiUri {
    public static final String ROOT_URI = "/rest";
    public static final String ROOT_OAUTH = "/oauth";
    public static final String ROOT_URI_HOCKEY_PLAYERS = ROOT_URI + "/players";
    public static final String ROOT_URI_GAMES = ROOT_URI + "/games";
    public static final String ROOT_URI_TEAMS = ROOT_URI + "/teams";
    public static final String ROOT_URI_MANAGERS = ROOT_URI + "/managers";

    public static class SubApiUri {
        public static final String CREATE = "/create";
        public static final String UPDATE = "/update";
        public static final String CHANGE_PASSWD = "/changepassword";
        public static final String ALL = "/**";
        public static final String ONE_SEGMENT = "/*";
    }
}
