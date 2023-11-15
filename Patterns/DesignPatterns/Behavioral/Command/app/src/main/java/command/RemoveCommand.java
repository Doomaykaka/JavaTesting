package command;

public class RemoveCommand<T> implements Command {
    private CollectionEditor<T> collection;
    private T value;
    
    public RemoveCommand(CollectionEditor<T> collection, T value){
        this.collection = collection;
        this.value = value;
    }

    @Override
    public void execute() {
        collection.getCollection().remove(value);
    }
}
