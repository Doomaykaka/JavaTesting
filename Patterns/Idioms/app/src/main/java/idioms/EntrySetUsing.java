package idioms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EntrySetUsing {
    public static void generateEntrySet() {
        Map<Integer, String> data = new HashMap<Integer, String>();
        data.put("Hello".hashCode(), "Hello");
        data.put("World".hashCode(), "World");
        
        Set<Entry<Integer, String>> entry = data.entrySet();
        for(Entry<Integer, String> keyVal:entry) {
            System.out.println("Key:"
                               + keyVal.getKey()
                               + ", Value:"
                               + keyVal.getValue());
        }
    }
}
