package traffic;

public class Road {
    private String name;
    private int seconds;
    private boolean isOpen;

    public Road(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        String str = "Road \"" + name + "\" will be ";
        if (isOpen) str += "\u001B[32m" + "open for " + seconds + "s." + "\u001B[0m";
        else str += "\u001B[31m" + "closed for " + seconds + "s." + "\u001B[0m";
        return str;
    }
}
