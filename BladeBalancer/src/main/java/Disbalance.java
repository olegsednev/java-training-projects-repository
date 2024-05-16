public class Disbalance {

    private final Double value;
    private final Integer position;

    public Disbalance(Double value, Integer position) {
        this.value = value;
        this.position = position;
    }

    public Double getValue() {
        return value;
    }

    public Integer getPosition() {
        return position;
    }
}
