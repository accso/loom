package de.accso.loom.part2_scopedvalues;

import de.accso.loom.part2_scopedvalues.context.RegionCode;
import de.accso.loom.part2_scopedvalues.context.User;
import de.accso.loom.part2_scopedvalues.framework.Application;
import de.accso.loom.part2_scopedvalues.framework.Framework;
import de.accso.loom.part2_scopedvalues.framework.Request;

import static de.accso.loom.part2_scopedvalues.context.RegionCode.EU;

public class ScopedValueExample {
    public static void main(String[] args) {
        Framework framework = new Framework( new MyApp() );

        sendRequest(framework);
    }

    private static void sendRequest(Framework framework) {
        // these values could come from a config setting or from a user login, respectively
        RegionCode regionCode = EU;
        User user = new User("john123", true);

        Request request = new Request("John", "Doe", "Frankfurt");
        framework.serveRequest(regionCode, user, request);
    }
}
