package com.hp.it.innovation.collaboration.utilities;

import org.springframework.beans.factory.annotation.Value;

public class GlobalApplicationResources {

    private @Value("${innovation.image.host}") String imageHost;

    private @Value("${innovation.site.host}") String siteHost;

    private @Value("${innovation.image.folder}") String imageFolder;
    
    private @Value("${innovation.profile.default.head}") String defaultHeadPhoto;
    
    public String getSiteHost() {
        return siteHost;
    }

    public String getImageHost() {
        return imageHost;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public String getDefaultHeadPhoto() {
        return defaultHeadPhoto;
    }
}
