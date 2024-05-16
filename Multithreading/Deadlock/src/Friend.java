public class Friend implements Comparable<Friend>{

    private final String name;

    public Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void throwBallTo(Friend catcher) {
        System.out.format("%s: %s кинул мне мяч!%n", catcher.getName(), this.name);
        //устанавливаем очерёдность, блокируем по тому, кто меньше (по алфавиту)
        synchronized (compareTo(catcher) > 0 ? catcher : this) {
            catcher.throwBallTo(this);
        }
    }

    public synchronized void throwBallToWithDeadLock(Friend catcher) {
        System.out.format("%s: %s кинул мне мяч!%n", catcher.getName(), this.name);
        catcher.throwBallToWithDeadLock(this);
        //Блокировка происходит из-за того, что каждый друг (в нашем случае поток)
        // начинает ждать другой поток, пока тот бросит мяч
    }

    @Override
    public int compareTo(Friend o) {
        return this.getName().compareTo(o.getName());
    }
}
