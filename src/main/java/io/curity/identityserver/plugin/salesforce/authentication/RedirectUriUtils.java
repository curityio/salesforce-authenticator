package io.curity.identityserver.plugin.salesforce.authentication;

import se.curity.identityserver.sdk.errors.ErrorCode;
import se.curity.identityserver.sdk.service.ExceptionFactory;
import se.curity.identityserver.sdk.service.authentication.AuthenticatorInformationProvider;

import java.net.MalformedURLException;
import java.net.URL;

import static io.curity.identityserver.plugin.salesforce.descriptor.SalesforceAuthenticatorPluginDescriptor.CALLBACK;

final class RedirectUriUtils
{
    private RedirectUriUtils()
    {
    }

    static String createRedirectUri(AuthenticatorInformationProvider authenticatorInformationProvider, ExceptionFactory exceptionFactory)
    {
        try
        {
            var authUri = authenticatorInformationProvider.getFullyQualifiedAuthenticationUri();

            return new URL(authUri.toURL(), authUri.getPath() + "/" + CALLBACK).toString();
        } catch (MalformedURLException e)
        {
            throw exceptionFactory.internalServerException(ErrorCode.INVALID_REDIRECT_URI,
                    "Could not create redirect URI");
        }
    }
}
