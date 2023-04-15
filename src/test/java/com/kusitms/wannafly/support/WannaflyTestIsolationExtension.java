package com.kusitms.wannafly.support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class WannaflyTestIsolationExtension implements AfterEachCallback  {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        WannaflyDatabaseManager databaseManager = getWannaflyDatabaseManager(context);
        databaseManager.truncateTables();
    }

    private WannaflyDatabaseManager getWannaflyDatabaseManager(ExtensionContext context) {
        return (WannaflyDatabaseManager) SpringExtension
                .getApplicationContext(context)
                .getBean("wannaflyDatabaseManager");
    }
}
