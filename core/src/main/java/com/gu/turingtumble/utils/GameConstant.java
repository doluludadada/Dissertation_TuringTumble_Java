package com.gu.turingtumble.utils;

/**
 *
 */
public enum GameConstant {
    TABLE_WIDTH(11),                            //number of slot widths
    TABLE_HEIGHT(11),                           //number of slot height
    CELL_SIZE(60),                              //gap size
    RED_BALL_COUNT(5),                          //
    BLUE_BALL_COUNT(5),                         //
    UPPER_SIDE_HEIGHT(2 * CELL_SIZE.getValue()),
    BOTTOM_SIDE_HEIGHT(2 * CELL_SIZE.getValue()),
    GRAVITY(1),
    ENERGY_LOSS_VALUE(0.1);

    ;

    private final int intValue;
    private final double doubleValue;

    GameConstant(int value) {
        this.intValue = value;
        this.doubleValue = value;
    }

    GameConstant(double value) {
        this.intValue = 0;
        this.doubleValue = value;
    }

    public int getValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

}
