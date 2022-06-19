import java.io.PrintWriter;

public class ArbeitsTag {
    private String date;
    private Zeit start;
    private Zeit end;
    private Zeit diff;
    private Zeit net;

    public ArbeitsTag(String date) {
        this.date = date;
    }

    public void print(PrintWriter out) {
        out.print(date + ";");
        if (start != null && end != null) {
            out.print(start + ";" + end + ";" + diff + ";" + net + ";");
        } else if (end == null) {
            out.print(start + ";");
        }
        out.println();
    }

    public void setStart(Zeit start) {
        this.start = start;
    }

    public void setEnd(Zeit end) {
        this.end = end;
    }

    public void loadFullDay(Zeit start, Zeit end, Zeit diff, Zeit net) {
        this.start = start;
        this.end = end;
        this.diff = diff;
        this.net = net;
    }

    public void calc() {
        if (start != null || end != null) {
            diff = end.diff(start);
            net = diff.diff(new Zeit(7, 12));
        } else {
            throw new IllegalArgumentException("Start und Ende müssen gesetzt sein um Differenz zu berechnen");
        }

    }

    public String getDate() {
        return date;
    }

    public Zeit getStart() {
        return start;
    }

    public Zeit getEnd() {
        return end;
    }

    public Zeit getDiff() {
        return diff;
    }

    public Zeit getNet() {
        return net;
    }

}
