package org.aua.aoop.post.conf;

import org.aua.aoop.post.conf.annotation.PropertiesSource;
import org.aua.aoop.post.conf.annotation.Value;

/**
 * <p></p>Service Configuration Placeholder</p>
 * <p/>
 *
 * @author vahe.momjyan
 * @see AbstractConfigurationPlaceholder
 */
@PropertiesSource("config.properties")
public class AppConfig extends AbstractConfigurationPlaceholder {
    private static AppConfig INSTANCE = new AppConfig();

    // Product catalog list file name
    @Value(key = "#{org.aua.aoop.post.product.list.file.name:products.txt}")
    String productCatalogFileName;

    @Value(key = "#{org.aua.aoop.post.sales.save.file.name:salesLog.save}")
    String salesLogSaveFileName;

    public String getProductCatalogFileName() {
        return productCatalogFileName;
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public String getSalesLogSaveFileName() {
        return salesLogSaveFileName;
    }
}
