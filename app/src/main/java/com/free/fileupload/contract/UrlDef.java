package com.free.fileupload.contract;

public class UrlDef {
    //192.168.3.48:8089
    public static final String URL_UPLOAD_FILE_DEBUG = "http://192.168.3.61:8089/Log/uploadLog";

   // public static final String BASE_DEBUG_URL = "http://192.168.3.61:8089/Log/";
    public static final String BASE_RELEASE_URL = "http://log.free.svipss.top/Log/";

    public static final String BASE_DEBUG_URL = BASE_RELEASE_URL;

    public static final String URL_UPLOAD_FILE = "http://log.free.svipss.top/Log/uploadLog";
    public static final String URL_QUERY_FILE_LIST = "http://log.free.svipss.top/Log/queryFileList";
}
