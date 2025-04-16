# Pac-Man Game Project (Extended)

## How to Run the Code

To run the game, use the following command in your terminal:

```bash
gradle run
```

Once the game launches, the game window will appear. **Click on the application icon to start the game.**

### Known Issue

None so far.
## Design Patterns Implemented and Involved Files/Classes
### 1. **Strategy Pattern**
**Relevant Package**: `pacman.model.entity.dynamic.ghost.strategy`

The Strategy Pattern is used to manage different behaviors of ghosts in CHASE mode, with each ghost being assigned a specific chase strategy.
- **Strategy**: `ChaseStrategy`
- **Concrete Strategies**: `BlinkyChaseStrategy`, `PinkyChaseStrategy`, `InkyChaseStrategy`, `ClydeChaseStrategy`
- **Context**: `Ghost`

### 2. **State Pattern**
**Relevant Package**: `package pacman.model.entity.dynamic.ghost.state`

The State Pattern is used to define the different behaviors of the ghost in different modes.

- **State**: `GhostState`
- **Concrete States**: `ScatterState`, `ChaseState`, `FrightenedState`
- **Context**: `GhostImpl`

### 3. **Decorator Pattern**
**Relevant Package**: `pacman.model.entity.dynamic.ghost.decorator`

The Decorator Pattern is employed to wrap `Ghost` into `FrightenedGhost`, allowing them to be eaten by Pac-Man.

- **Component**: `Ghost`
- **Concrete Component**: `GhostImpl`
- **Decorator**: `GhostDecorator`
- **Concrete Decorator**: `FrightenedGhost`
