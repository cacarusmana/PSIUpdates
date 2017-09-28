package com.global.imd.psiupdates.util;

/**
 * Created by Caca Rusmana on 17/09/2017.
 */

public class Constant {


    public static final String BASE_API_URL = "https://api.data.gov.sg/v1/environment/";
    public static final String PSI_URL = BASE_API_URL + "psi?";
    public static final String PM25_URL = BASE_API_URL + "pm25?";
    public static final String API_KEY = "uMebr8EaejmtS5W8TR5X5NgBW19A0o9P";
    public static final String API_KEY_NAME = "api-key";

    public static final String METHOD_GET = "GET";
    public static final Integer TIMEOUT_CONN = 60000;
    public static final Integer RESPONSE_SUCCEED = 200;

    public static final String SG_ZONE_ID = "Singapore";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_RESPONSE = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String DATE_FORMAT_UPDATE = "dd MMM yyyy hhaaa";


    // fields json of psi update response
    public static final String REGION_METADATA_FIELD = "region_metadata";
    public static final String NAME_FIELD = "name";
    public static final String LABEL_LOCATION_FIELD = "label_location";
    public static final String LONGITUDE_FIELD = "longitude";
    public static final String LATITUDE_FIELD = "latitude";
    public static final String ITEMS_FIELD = "items";
    public static final String READINGS_FIELD = "readings";
    public static final String PSI24_HOURLY_FIELD = "psi_twenty_four_hourly";
    public static final String TIMESTAMP_FIELD = "timestamp";
    public static final String ERROR_MESSAGE_FIELD = "message";
    public static final String PM25_ONE_HOURLY_FIELD = "pm25_one_hourly";

    public static final String FOCUSED_CAMERA_POSITION = "central";
    public static final String DATE_TIME_FIELD = "date_time=";

    public static final float ZOOM_VALUE = 10.5f;

}
