package com.adaxiom.downloaders;


public class BaseContentDownloader<T> {
    protected T beConnector;

    public BaseContentDownloader(T beConnector) {
        this.beConnector = beConnector;
    }

}
