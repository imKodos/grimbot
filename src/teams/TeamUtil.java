package teams;

public class TeamUtil {
    // public TeamUtil() {

    // }

    public enum TeamName {
        CELTICS(2), NETS(3), KNICKS(20), SIXERS(23), RAPTORS(28), // atlantic
        BULLS(5), CAVALIERS(6), PISTONS(9), PACERS(12), BUCKS(17), // central
        HAWKS(1), HORNETS(4), HEAT(16), MAGIC(22), WIZARDS(30), // Southeast
        NUGGETS(8), TIMBERWOLVES(18), BLAZERS(25), JAZZ(29), THUNDER(21), // northwest
        WARRIORS(10), CLIPPERS(13), LAKERS(14), SUNS(24), KINGS(26), // pacific
        MAVERICKS(7), ROCKETS(11), GRIZZLIES(15), PELICANS(19), SPURS(27); // southwest

        public int getId() {
            return tId;
        }

        TeamName(final int id) {

            tId = id;
        }

        private final int tId;
    }
}
