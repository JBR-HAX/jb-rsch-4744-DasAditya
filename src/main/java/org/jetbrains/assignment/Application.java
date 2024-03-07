package org.jetbrains.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private List<Coordniate> coordniates = new ArrayList<>();

    @PostMapping("/locations")
    public List<Coordniate> calculateLocations(@RequestBody List<MovementOperation> movements){
        coordniates.clear();
        int x= 0;
        int y = 0;
        coordniates.add(new Coordniate(x,y));

        for(MovementOperation operation : movements){
            switch (operation.getDirection()){
                case NORTH:
                    y += operation.getSteps();
                    break;
                case SOUTH:
                    y -= operation.getSteps();
                    break;
                case EAST:
                    x += operation.getSteps();
                    break;
                case WEST:
                    x -= operation.getSteps();
                    break;
            }
            coordniates.add(new Coordniate(x,y));
        }
        return coordniates;
    }

    @PostMapping("/moves")
    public List<MovementOperation> calcMoves(@RequestBody List<Coordniate> locations){
        List<MovementOperation> moves = new ArrayList<>();
        Coordniate currentLoc = locations.get(0);

        for (int i = 1; i<locations.size(); i++) {
            Coordniate dest = locations.get(i);

            int xd = dest.getX() - currentLoc.getX();
            int yd = dest.getY() - currentLoc.getY();

            if (xd != 0){
                Direction direction = xd > 0 ? Direction.EAST : Direction.WEST;
                int steps = Math.abs(xd);
                moves.add(new MovementOperation(direction, steps));

            }

            if (yd != 0){
                Direction direction = yd > 0 ? Direction.NORTH : Direction.SOUTH;
                int steps = Math.abs(yd);
                moves.add(new MovementOperation(direction, steps));

            }

            currentLoc = dest;
        }

        return moves;
    }
}

class Coordniate{
    private int x;

    public int getX() {
        return x;
    }

    public Coordniate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;
}

enum Direction{
    NORTH,
    SOUTH,
    EAST,
    WEST
}


class MovementOperation {
    private Direction direction;
    private int steps;

    public MovementOperation(Direction direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }

    public org.jetbrains.assignment.Direction getDirection() {
        return direction;
    }

    public void setDirection(org.jetbrains.assignment.Direction direction) {
        this.direction = direction;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
