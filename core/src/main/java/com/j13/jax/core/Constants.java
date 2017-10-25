package com.j13.jax.core;


public class Constants {

    public static int USER_IS_MACHINE = 1;
    public static int DEFAULT_IMG_ID = 0;
    public static int ADMIN_ACCOUNT_ID = -1;

    public static class ResponseStatus {
        public static int SUCCESS = 0;
        public static int FAILURE = 1;
        public static int UNEXCEPED_FAILURE = 2;
    }

    public static class DB {
        public static int NOT_DELETED = 0;
        public static int DELETED = 1;
    }

    public static class OrderStatus {
        public static int QUERY_ALL_STATUS = -1;
        public static int ORDER_CREATED = 0;
    }

    public static class OrderActionType {
        public static final int DELETE = 3;
        public static int ADD = 0;
        public static int UPDATE_STATUS = 1;
        public static int UPDATE_BASIC_INFO = 2;
    }


    public static class Wechat {
        public static String TOKEN = "aaaa";
//        public static String APPID = "wx0d07487890a95d68";
//        public static String AppSecret = "e71556c2f7b94b916451e0fc81b04f02";

        public static String APPID = "wxe2ff872ab7c97d17";
        public static String AppSecret = "62bc146e2e9e29c8973d814949dd7a9f";
    }


    public static class Banner {
        public static int ONLINE = 1;
        public static int OFFLINE = 0;
    }

    public static class EventType {
        public static int TRIPLE_IMG = 0;
        public static int FETCHED_TRIPLE_IMG = -1;
        public static int FETCHED_DZ = -2;
        public static int MEINV = -1;
        public static int DZ = -2;
    }
}
