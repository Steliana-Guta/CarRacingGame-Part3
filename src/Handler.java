public class Handler {
    private RaceGround race;
    public MouseControl getMouseManager() {
        return race.getMouseControl();
    }
    public RaceGround getGame() {
        return race;
    }
}

