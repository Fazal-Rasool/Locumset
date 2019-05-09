package com.adaxiom.manager;

import com.adaxiom.downloaders.GeneralDownloader;
import com.adaxiom.network.BackendConnector;

public class DownloaderManager {
    static DownloaderManager instance;

    GeneralDownloader generalDownloader;

    public DownloaderManager(BackendConnector beConnector) {
        instance = this;
        generalDownloader = new GeneralDownloader(beConnector.getGeneralDownloader());

    }

    public static GeneralDownloader getGeneralDownloader() {
        return instance.generalDownloader;
    }


}
