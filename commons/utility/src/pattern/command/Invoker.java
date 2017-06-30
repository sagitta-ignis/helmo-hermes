package pattern.command;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Menini (d120041) <t.menini.04.92@gmail.com> or
 * <t.menini@student.helmo.be>
 * alias Sagitta <sagitta-ignis@hotmail.com>
 */
public abstract class Invoker {

    private final List<Command> history;

    public Invoker() {
        history = new ArrayList<>();
    }

    public void storeAndExecute(Command cmd) {
        this.history.add(cmd);
        cmd.execute();
    }
    
    public Command findInstance(Class searchedClass) {
        for(int i = history.size()-1; i >= 0; i--) {
            if(searchedClass.isInstance(history.get(i))) {
                return history.get(i);
            }
        }
        return null;
    }
}
