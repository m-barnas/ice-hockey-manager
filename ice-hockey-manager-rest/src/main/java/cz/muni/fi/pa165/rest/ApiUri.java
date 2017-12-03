package cz.muni.fi.pa165.rest;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class ApiUri {

    public static final String ROOT_URI = "/";

    public static final String ROOT_URI_OAUTH = "/oauth";
    public static final String ROOT_URI_HOME = "/home";
    public static final String ROOT_URI_HOCKEY_PLAYERS = "/players";
    public static final String ROOT_URI_GAMES = "/games";
    public static final String ROOT_URI_TEAMS = "/teams";
    public static final String ROOT_URI_MANAGERS = "/managers";

    public static class SubApiUri {
        public static final String CREATE = "/create";
        public static final String UPDATE = "/update";
        public static final String ALL = "/**";
        public static final String ONE_SEGMENT = "/*";
    }
}
