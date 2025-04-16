package pacman.model.entity.dynamic.player.observer;

public interface PlayerPositionAndDirectionSubject {
    void registerObserver(PlayerPositionAndDirectionObserver observer);

    void removeObserver(PlayerPositionAndDirectionObserver observer);

    void notifyObservers();
}
