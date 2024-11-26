package de.accso.loom.part2_scopedvalues;

import de.accso.loom.part2_scopedvalues.context.RegionCode;
import de.accso.loom.part2_scopedvalues.context.User;
import de.accso.loom.part2_scopedvalues.framework.Application;
import de.accso.loom.part2_scopedvalues.framework.Framework;
import de.accso.loom.part2_scopedvalues.framework.Request;

import static de.accso.loom.part2_scopedvalues.context.RegionCode.EU;

public class ScopedValueExample {
    public static void main(String[] args) {
        Framework framework = setupFramework();

        sendRequest(framework);
    }

    private static Framework setupFramework() {
        Application application = new MyApp();
        Framework framework = new Framework(application);
        return framework;
    }

    private static void sendRequest(Framework framework) {
        // these scoped values could come from a config setting or from a user login, respectively
        RegionCode regionCode = EU;
        User user = new User("john123", true);

        Request request = new Request("John", "Doe", "Frankfurt");
        framework.serveRequest(regionCode, user, request);
    }
}
