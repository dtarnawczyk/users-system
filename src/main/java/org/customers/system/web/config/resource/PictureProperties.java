package org.customers.system.web.config.resource;

import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;

@ConfigurationProperties(prefix = "customer.profile.image")
public class PictureProperties {
    private Resource uploadPath;
    private Resource defaultPicture;

    private static final Logger log = Logger.getLogger(PictureProperties.class);

    public Resource getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = new DefaultResourceLoader().getResource(uploadPath);
        try {
            log.info("Upload path: "+ this.uploadPath.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Resource getDefaultPicture() {
        return defaultPicture;
    }

    public void setDefaultPicture(String defaultPicture) {
        this.defaultPicture = new DefaultResourceLoader().getResource(defaultPicture);
        try {
            log.info("Upload path: "+ this.defaultPicture.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
