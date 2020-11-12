package jajabor.in.app.Helper;

import android.provider.BaseColumns;

public class Contract3 {
    public Contract3() {
    }
    public static final class CartItem implements BaseColumns {
        public static final String TABLE_NAME = "Custom";
        public static final String COLUMN_PID = "pid";
        public static final String COLUMN_NAME ="name";
        public static final String COLUMN_PRICE="price";
        public static final String COLUMN_PIC ="pic";
        public static final String COLUMN_QUANTITY="quantity";
        public static final String COLUMN_SIZE="size";
        public static final String COLUMN_COLOR="color";
    }
}
