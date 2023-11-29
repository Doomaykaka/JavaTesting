package idioms;

import java.util.List;

public class ListBuilder<T> {
    T[] nums;
    List<T> list;
    
    public ListBuilder(T[] nums, List<T> emptyList) {
        this.nums = nums;
        this.list = emptyList;
    }
    
    public List<T> getList() {
        for(T num:nums) {
            list.add(num);
        }
        return list;
    }
}
