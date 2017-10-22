package com.j13.jax.core;

public class ErrorCode {


    public static class User {
        public static int PASSWORD_NOT_RIGHT = 1001;
        public static int MOBILE_EXISTED = 1002;
        public static int NICKNAME_EXISTED = 1003;    // 已经去掉了
        public static int NEED_LOGIN = 1004;
    }


    public static class Account {
        public static int PASSWORD_NOT_RIGHT = 2001;
        public static int NAME_EXISTED = 2003;
    }



    public static class Item {
        public static int NO_ITEM_RETURN = 4001;
    }

}
