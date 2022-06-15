public class Zeit {
    private int minuten;
    private int stunden;

    public Zeit(int minuten, int stunden) {
        this.minuten = minuten;
        this.stunden = stunden;
    }

    public int getMinuten() {
        return minuten;
    }

    public int getstunden() {
        return stunden;
    }

    public Zeit diff(Zeit z) {
        int diffMinuten = this.minuten - z.getMinuten();
        int diffStunden = this.stunden - z.getstunden();
        if (diffMinuten < 0) {
            diffMinuten += 60;
            diffStunden--;
        }
        return new Zeit(diffMinuten, diffStunden);
    }

    public String toString() {
        return String.format("%02d:%02d", stunden, minuten);
    }

}