package idioms;

public class DependencyInjectionUsing {
    public static class Dependency {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
    
    private Dependency dep;
    
    public DependencyInjectionUsing(Dependency dep) {
        this.dep = dep;
    }
    
    public void use() {
        System.out.println(dep.getId());
    }
}
