public class Zeit {
    private int stunden;
    private int minuten;

    public Zeit(int stunden, int minuten) {
        if (stunden > 24 || minuten > 59) {
            throw new IllegalArgumentException("Minuten und Stunden müssen positiv und kleiner 24h und 59 min sein");
        }
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
        return new Zeit(diffStunden, diffMinuten);
    }

    public String toString() {
        return String.format("%02d:%02d", stunden, minuten);
    }

}