package utils;

/**
 *
 */
public enum GameConstant {
    TABLE_WIDTH(11),        //number of slot widths
    TABLE_HEIGHT(11),       //number of slot height
    CELL_SIZE(60),          //gap size
    RED_BALL_COUNT(5),      //
    BLUE_BALL_COUNT(5),     //
    UPPER_SIDE_HEIGHT(2 * CELL_SIZE.value),
    BOTTOM_SIDE_HEIGHT(2 * CELL_SIZE.value),
    GRAVITY(1),



    ;

    private final int value;

    GameConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
