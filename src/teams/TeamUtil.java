package teams;

import java.util.Arrays;
import java.util.Optional;

public class TeamUtil {
    // public TeamUtil() {

    // }

    public enum TeamName {
        CELTICS(2, "Celtics"), NETS(3, "Nets"), KNICKS(20, "Knicks"), SIXERS(23, "76ers"), RAPTORS(28, "Raptors"), // atlantic
        BULLS(5, "Bullss"), CAVALIERS(6, "Cavaliers"), PISTONS(9, "PIstons"), PACERS(12, "Pacers"), BUCKS(17, "Bucks"), // central
        HAWKS(1, "Hawks"), HORNETS(4, "Hornets"), HEAT(16, "Heat"), MAGIC(22, "Magic"), WIZARDS(30, "Wizards"), // Southeast
        NUGGETS(8, "Nuggets"), TIMBERWOLVES(18, "Timberwolves"), BLAZERS(25, "Blazers"), JAZZ(29, "Jazz"),
        THUNDER(21, "Thunder"), // northwest
        WARRIORS(10, "Warriors"), CLIPPERS(13, "Clippers"), LAKERS(14, "Lakers"), SUNS(24, "Suns"), KINGS(26, "Kings"), // pacific
        MAVERICKS(7, "Mavericks"), ROCKETS(11, "Rockets"), GRIZZLIES(15, "Grizzlies"), PELICANS(19, "Pelicans"),
        SPURS(27, "Spurs"), // southwest
        UNKNOWN(0, "N/A");

        private final int tId;
        private final String tName;

        TeamName(final int id, final String name) {
            tId = id;
            tName = name;
        }

        public String getName() {
            return tName;
        }

        public int getId() {
            return tId;
        }

        public static Optional<TeamName> getTeamByName(String value) {
            return Arrays.stream(TeamName.values())
                    .filter(teamName -> teamName.tName.equals(value))
                    .findFirst();
        }

        public static Optional<TeamName> getTeamByName(int value) {
            return Arrays.stream(TeamName.values())
                    .filter(teamName -> teamName.tId == value)
                    .findFirst();
        }

        public static TeamName getById(int id) {
            for (TeamName e : values()) {
                if (e.tId == (id))
                    return e;
            }
            return UNKNOWN;
        }

        // public static int getId(TeamName name) {
        // for (TeamName e : values()) {
        // System.out.println(e);
        // if (e.equals(name))
        // return e.tId;
        // }
        // return -1;
        // }

    }

}
