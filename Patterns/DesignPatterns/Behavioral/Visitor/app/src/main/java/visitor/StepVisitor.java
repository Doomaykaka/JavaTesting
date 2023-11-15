package visitor;

public class StepVisitor implements Visitor {
    @Override
    public void visitDot(Dot d) {
        d.move(10, 10);
        d.draw();
    }

    @Override
    public void visitCircle(Circle c) {
        c.move(10, 10);
        c.draw();
    }
}
