import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.Uri;
import java.io.ByteArrayInputStream;

public class AdBlockWebViewClient extends WebViewClient {

    // Allowlist containing Streamtape and its alternative domains / CDN nodes
    private static final String[] ALLOWED_DOMAINS = {
        "streamtape.com",
        "streamta.net",
        "streamtape.to",
        "stape.fun",
        "streamtape.xyz"
    };

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Uri uri = request.getUrl();
        String host = uri.getHost();

        // If the request has no host (e.g., local app data or assets), allow it
        if (host == null) {
            return super.shouldInterceptRequest(view, request);
        }

        // Check if the request host matches Streamtape or any of its subdomains
        boolean isAllowed = false;
        for (String allowedDomain : ALLOWED_DOMAINS) {
            if (host.equals(allowedDomain) || host.endsWith("." + allowedDomain)) {
                isAllowed = true;
                break;
            }
        }

        // If the domain is NOT Streamtape, block it instantly with an empty response
        if (!isAllowed) {
            return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
        }

        // Allow legitimate Streamtape video streams and page files to load
        return super.shouldInterceptRequest(view, request);
    }
}