package jajabor.in.app;

import android.provider.BaseColumns;

class Contract2 {
    public Contract2() {
    }
    public static final class CartItem2 implements BaseColumns {
        public static final String TABLE_NAME = "Wishlist";
        public static final String COLUMN_PID = "pid";
        public static final String COLUMN_NAME ="name";
        public static final String COLUMN_PRICE="price";
        public static final String COLUMN_PIC ="pic";
        public static final String COLUMN_DESC="quantity";
    }
}
