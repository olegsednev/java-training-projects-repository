public class Blade {
    private Integer slot;
    private final String name;
    private final Integer staticMoment;
    private Boolean isFixed;

    public Blade(Integer slot, String name, Integer staticMoment, Boolean isFixed) {
        this.slot = slot;
        this.name = name;
        this.staticMoment = staticMoment;
        this.isFixed = isFixed;
    }

    public Blade(Blade blade) {
        this.slot = blade.getSlot();
        this.name = blade.getName();
        this.staticMoment = blade.getStaticMoment();
        this.isFixed = blade.isFixed();
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public Integer getStaticMoment() {
        return staticMoment;
    }

    public Boolean isFixed() {
        return isFixed;
    }

    public void fix() {
        isFixed = false;
    }

    public void unFix() {
        isFixed = true;
    }
}
