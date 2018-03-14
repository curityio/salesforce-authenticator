package io.curity.identityserver.plugin.test

import java.util.concurrent.TimeUnit

class TestRequirements {
        static boolean isEnvironmentVariableSet(String name) {
            def secret = System.getenv(name)
            def isSet = secret != null && !secret.isEmpty()

            if (!isSet) {
                System.err.println("$name environment variable is not set. " +
                        "Set it and retry the test")
            }

            return isSet
        }

    static boolean getIsIdshAvailable() {
        def idsh = "idsh -s".execute()
        idsh.out << "quit\n"
        def isAvailable = idsh.waitFor(2, TimeUnit.SECONDS) && idsh.exitValue() == 0

        if (!isAvailable) {
            System.err.println("idsh is not available in the system PATH. Add it and retry the test")
        }

        return isAvailable
    }
}
