package io.curity.identityserver.plugin.salesforce.authentication.browsertests

import geb.Page

class SalesforceLoginPage {
}

class SignupResultPage extends Page {

    static url = "/create"
    static at = { title == "Sample App Sign-up Result Page" }
    static content = {
        heading { $("h1").text() }
    }

}