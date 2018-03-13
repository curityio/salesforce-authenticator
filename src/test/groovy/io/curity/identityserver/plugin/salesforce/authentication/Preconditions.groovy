package io.curity.identityserver.plugin.salesforce.authentication

import java.util.concurrent.TimeUnit

class Preconditions {
        static boolean getIsSalesForceClientSecretDefined() {
            def secret = System.getenv("SALESFORCE_CLIENT_SECRET")

            def isSet = secret != null && !secret.isEmpty()

            println("SALESFORCE_CLIENT_SECRET env. variable is set? $isSet")

            return isSet
        }

    static boolean getIsIdshAvailable() {
        def idsh = "idsh -s".execute()
        idsh.out << "quit\n"
        def isAvailable = idsh.waitFor(2, TimeUnit.SECONDS) && idsh.exitValue() == 0

        println("idsh is available? $isAvailable")

        return isAvailable
    }
}
