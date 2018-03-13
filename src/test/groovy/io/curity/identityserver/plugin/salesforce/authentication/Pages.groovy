package io.curity.identityserver.plugin.salesforce.authentication

import geb.Page

class SalesforceLoginPage extends Page {
    static url = "/salesforce1"
}

class SignupResultPage extends Page {

    static url = "/create"
    static at = { title == "Sample App Sign-up Result Page" }
    static content = {
        heading { $("h1").text() }
    }

}