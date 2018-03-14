package io.curity.identityserver.plugin.salesforce.authentication

import java.util.concurrent.TimeUnit

class Preconditions {
        static boolean getIsSalesForceClientSecretDefined() {
            def secret = System.getenv("SALESFORCE_CLIENT_SECRET")
            def isSet = secret != null && !secret.isEmpty()

            if (!isSet) {
                System.err.println("SALESFORCE_CLIENT_SECRET environment variable is not set. " +
                        "Set it to the secret of the Salesforce OAuth client and retry the test")
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
