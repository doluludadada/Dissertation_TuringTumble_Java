

/**
 *
 *
 */
public enum GameConstant {
    TABLE_WIDTH(11),
    TABLE_HEIGHT(11),
    CELL_SIZE(60);          //gap size




    private final int value;

    GameConstant(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
