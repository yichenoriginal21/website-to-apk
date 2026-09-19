import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.ByteArrayInputStream;

public class AdBlockWebViewClient extends WebViewClient {

    // List of common ad and tracking domains to block
    private static final String[] BLOCKED_DOMAINS = {
        "doubleclick.net",
        "googleads.g.doubleclick.net",
        "googlesyndication.com",
        "google-analytics.com",
        "adservice.google.com",
        "taboola.com"
    };

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();

        // Check if the requested URL contains any ad domains
        for (String domain : BLOCKED_DOMAINS) {
            if (url.contains(domain)) {
                // Block the ad by returning an empty response
                return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
            }
        }

        // Allow normal content to load
        return super.shouldInterceptRequest(view, request);
    }
}