package nl.hsleiden.vault.vault.connectionNutteloos;

import android.content.Context;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by Perseus on 11-03-16.
 */

public class ModifiedHostNameVerifier implements HostnameVerifier {
    boolean bAccepted;
    public ModifiedHostNameVerifier(Context m) {
        bAccepted=false;
    }

    public void setAccepted(boolean acc) {
        bAccepted = acc;
    }
    // verify is only called to compare hostname from DNS to SSL Certificate issued-to name. Do what the user wants and return.

    // NOTE: in the more generic case where the user is connecting all over the place, you’ll need to get the default behavior,

    // same as we do in the X509 code below – otherwise they will be blocked on all calls not set to accepted, even ones with

    // matching hostnames.
    public boolean verify(String string,SSLSession ssls) {

        return bAccepted;
    }
}