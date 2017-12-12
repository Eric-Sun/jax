package com.j13.jax.core;

public class ErrorCode {

    public static class Event {
        public static int EVENT_COLLECTION_EXIST = 2001;

    }

    public static class Comment {
        public static int COMMENT_COLLECTION_EXIST = 3001;

    }

    public static class FamilyMember {
        public static int EXISTED = 4001;
    }

    public static class Fetcher {
        public static int DZ_EXISTED = 5001;
    }

    public static class User {
        public static int USER_LOGIN_ERROR = 1501;
        public static int USER_MOBILE_EXISTED = 1502;
    }

    public static class Collection {
        public static int COLLECTED = 1601;
    }


}
