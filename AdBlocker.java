package com.twitchadb.app;

import java.util.Arrays;
import java.util.List;

public class AdBlocker {

    private static final List<String> AD_DOMAINS = Arrays.asList(
        "googlesyndication.com",
        "doubleclick.net",
        "googletagservices.com",
        "imasdk.googleapis.com",
        "amazon-adsystem.com",
        "ads.twitch.tv",
        "spade.twitch.tv",
        "twitchsvc.net",
        "static.ads-twitter.com",
        "tracking.twitchlabs.com",
        "pubads.g.doubleclick.net"
    );

    private static final List<String> AD_URL_PATTERNS = Arrays.asList(
        "/ads/",
        "/ad/",
        "ad_id=",
        "advertisement",
        "tracking",
        "analytics",
        "/twitchspade"
    );

    public static boolean isAdUrl(String url) {
        if (url == null) return false;
        String lowerUrl = url.toLowerCase();

        for (String domain : AD_DOMAINS) {
            if (lowerUrl.contains(domain)) return true;
        }
        for (String pattern : AD_URL_PATTERNS) {
            if (lowerUrl.contains(pattern)) return true;
        }
        return false;
    }

    public static String getAdBlockScript() {
        return
            "(function() {" +
            "  const originalFetch = window.fetch;" +
            "  window.fetch = function(url, options) {" +
            "    if (typeof url === 'string' && (" +
            "      url.includes('imasdk.googleapis.com') ||" +
            "      url.includes('doubleclick.net') ||" +
            "      url.includes('/ads') ||" +
            "      url.includes('spade.twitch.tv')" +
            "    )) {" +
            "      return Promise.reject(new Error('Blocked by AdBlock'));" +
            "    }" +
            "    return originalFetch.apply(this, arguments);" +
            "  };" +
            "  function removeAds() {" +
            "    const selectors = [" +
            "      '[data-a-target=\"ad-overlay\"]'," +
            "      '.tw-ad'," +
            "      '.ad-banner'," +
            "      '[class*=\"ad-\"]'," +
            "      '.ScAdBannerWrapper'," +
            "      '[data-test-selector=\"ad-banner-default\"]'" +
            "    ];" +
            "    selectors.forEach(sel => {" +
            "      document.querySelectorAll(sel).forEach(el => el.remove());" +
            "    });" +
            "  }" +
            "  const observer = new MutationObserver(removeAds);" +
            "  observer.observe(document.documentElement, { childList: true, subtree: true });" +
            "  removeAds();" +
            "})();";
    }
}
