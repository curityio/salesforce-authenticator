package io.curity.identityserver.plugin.salesforce.authentication

import spock.lang.Specification

class SalesforceAuthenticationTest extends Specification
{
    def "Test foo bar"() {
        given:
        def x = 1

        when:
        x++

        then:
        x == 2
    }

    def "zort"() {
        given:
        def x = 0

        when:
        x++

        then:
        x != 2
    }
}