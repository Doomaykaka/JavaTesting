package command;

public class AddCommand<T> implements Command {
    private CollectionEditor<T> collection;
    private T value;
    
    public AddCommand(CollectionEditor<T> collection, T value){
        this.collection = collection;
        this.value = value;
    }

    @Override
    public void execute() {
        collection.getCollection().add(value);
    }
}
