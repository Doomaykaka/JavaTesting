package caveatemptorlite2h.configuration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class NamingStrategy extends org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl {
    
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier("ce_" + name.getText(), name.isQuoted());
    }
}
