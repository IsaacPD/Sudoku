package Actions;

import java.util.ArrayList;

public class Actions extends ArrayList<Action> {
    int currentAction;

    @Override
    public boolean add(Action action) {
        action.doit();
        currentAction++;
        if (currentAction <= size())
            removeRange(currentAction - 1, size());

        return super.add(action);
    }

    public void redo() {
        if (currentAction < size())
            get(currentAction++).doit();
    }

    public void undo() {
        if (currentAction != 0)
            get(--currentAction).undo();
    }
}
