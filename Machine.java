public class Machine {
    private String name;
    private String  capacity;
    private int cooldownInSeconds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getCooldownInSeconds() {
        return cooldownInSeconds;
    }

    public void setCooldownInSeconds(int cooldownInSeconds) {
        this.cooldownInSeconds = cooldownInSeconds;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "name='" + name + '\'' +
                ", capacity='" + capacity + '\'' +
                ", cooldownInSeconds='" + cooldownInSeconds + '\'' +
                '}';
    }
}
