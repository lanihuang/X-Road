package ee.ria.xroad.proxy.testsuite.testcases;

import java.security.cert.X509Certificate;

import ee.ria.xroad.common.conf.globalconf.GlobalConf;
import ee.ria.xroad.common.conf.serverconf.ServerConf;
import ee.ria.xroad.common.identifier.ClientId;
import ee.ria.xroad.proxy.testsuite.Message;
import ee.ria.xroad.proxy.testsuite.SslMessageTestCase;
import ee.ria.xroad.proxy.testsuite.TestGlobalConf;
import ee.ria.xroad.proxy.testsuite.TestServerConf;

import static ee.ria.xroad.common.ErrorCodes.SERVER_CLIENTPROXY_X;
import static ee.ria.xroad.common.ErrorCodes.X_SSL_AUTH_FAILED;

/**
 * Proxy authentication certificate org does not match member.
 */
public class SslAuthCertNotMatchesOrg extends SslMessageTestCase {

    /**
     * Constructs the test case.
     */
    public SslAuthCertNotMatchesOrg() {
        requestFileName = "getstate.query";
        responseFile = "getstate.answer";
    }

    @Override
    protected void startUp() throws Exception {
        ServerConf.reload(new TestServerConf());
        GlobalConf.reload(new TestGlobalConf() {
            @Override
            public boolean authCertMatchesMember(X509Certificate cert,
                    ClientId member) {
                return false;
            }
        });
    }

    @Override
    protected void validateFaultResponse(Message receivedResponse)
            throws Exception {
        assertErrorCode(SERVER_CLIENTPROXY_X, X_SSL_AUTH_FAILED);
    }
}