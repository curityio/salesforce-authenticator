package io.curity.identityserver.plugin.test

import java.util.concurrent.TimeUnit

import static com.google.common.base.Preconditions.checkState

final class Idsh {
    Idsh() { }

    private int rollbackCount = 0

    void loadTestConfig(String resourceFileName, String extraCommands = "") {
        def tempFile = copyTestConfigToTempFile(resourceFileName)
        def idsh = "idsh -s".execute()
        idsh.out << """
                configure
                load merge $tempFile                                         
                $extraCommands
                commit
                quit
                quit
            """

        waitForIdshToQuit(idsh)
        
        def exitValue = idsh.exitValue()

        if (exitValue != 0) {
            System.err.println(idsh.text)
        }

        checkState(exitValue == 0, "Idsh exited with a non-zero exit status of $exitValue")
    }

    void setValue(String path, Object value) {
        def idsh = "idsh -s".execute()
        idsh.out << """
            configure
            set $path $value
            commit
            quit
            quit
        """

        waitForIdshToQuit(idsh)
        rollbackCount++
    }

    void rollback() {
        def idsh = "idsh -s".execute()
        idsh << """
                configure
                rollback $rollbackCount
                commit
                quit
                quit
            """

        waitForIdshToQuit(idsh)
    }

    private static void waitForIdshToQuit(Process idsh) {
        checkState(idsh.waitFor(2, TimeUnit.SECONDS), "Idsh didn't shutdown in a timely manner")
    }

    private static String copyTestConfigToTempFile(String resourceFileName) {
        def temp = File.createTempFile("temp", ".scrap");
        temp.deleteOnExit()
        temp << getClass().getResourceAsStream(resourceFileName)
        temp.absolutePath
    }
}
