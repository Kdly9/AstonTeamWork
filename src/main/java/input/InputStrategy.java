package input;

import collection.CustomCollection;
import visitor.ConventionVisitor;

public interface InputStrategy {
    CustomCollection<ConventionVisitor> fill();
}
