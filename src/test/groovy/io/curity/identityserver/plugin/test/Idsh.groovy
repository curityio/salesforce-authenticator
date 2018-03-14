package io.curity.identityserver.plugin.test

import java.util.concurrent.TimeUnit

import static com.google.common.base.Preconditions.checkState

final class Idsh {
    private Idsh() { }

    static void loadTestConfig(String resourceFile, String extraCommands = "") {
        def tempFile = copyTestConfigToTempFile(resourceFile)
        def idsh = "idsh -s".execute()
        idsh.out << """
                configure
                load merge $tempFile                                         
                $extraCommands
                commit
                quit
                quit
            """
        checkState(idsh.waitFor(2, TimeUnit.SECONDS), "Idsh didn't shutdown in a timely manner")
        
        def exitValue = idsh.exitValue()

        if (exitValue != 0) {
            System.err.println(idsh.text)
        }

        checkState(exitValue == 0, "Idsh exited with a non-zero exit status of $exitValue")
    }

    static void rollback() {
        def idsh = "idsh -s".execute()
        idsh << """
                configure
                rollback 0
                commit
                quit
                quit
            """
    }

    private static String copyTestConfigToTempFile(String resoureFile) {
        def temp = File.createTempFile("temp", ".scrap");
        temp.deleteOnExit()
        temp << getClass().getResourceAsStream(resoureFile)
        temp.absolutePath
    }
}
