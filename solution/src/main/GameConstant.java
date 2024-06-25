public enum GameConstant {
    TABLE_WIDTH(11),
    TABLE_HEIGHT(10);




    private int value;

    GameConstant(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
