package command;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    private List<Command> commands;
    
    public CollectionUtils() {
        commands = new ArrayList<Command>();
    }
    
    public void addCommand(Command command) {
        commands.add(command);
    }
    
    public void execCommand(Command command) {
        command.execute();
    }
    
    public void execCommandsQueue() {
        for(Command cmd: commands) {
            cmd.execute();
        }
    }
}
