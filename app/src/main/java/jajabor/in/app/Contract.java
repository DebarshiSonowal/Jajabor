package jajabor.in.app;

import android.provider.BaseColumns;

public class Contract {
    public Contract() {
    }
    public static final class CartItem implements BaseColumns{
        public static final String TABLE_NAME = "Cart";
        public static final String COLUMN_PID = "pid";
        public static final String COLUMN_NAME ="name";
        public static final String COLUMN_PRICE="price";
        public static final String COLUMN_PIC ="pic";
        public static final String COLUMN_QUANTITY="quantity";
        public static final String COLUMN_SIZE="size";
        public static final String COLUMN_COLOR="color";
    }

}
