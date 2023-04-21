public class PartOperation {
    private int time;
    private Machine machineUsed;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Machine getMachineUsed() {
        return machineUsed;
    }

    public void setMachineUsed(Machine machineUsed) {
        this.machineUsed = machineUsed;
    }

    @Override
    public String toString() {
        return "PartOperation{" +
                "time=" + time +
                ", machineUsed=" + machineUsed +
                '}';
    }
}
