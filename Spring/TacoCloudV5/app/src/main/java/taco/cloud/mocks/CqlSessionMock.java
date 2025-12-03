package taco.cloud.mocks;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.context.DriverContext;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.metadata.Metadata;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.oss.driver.api.core.metadata.TokenMap;
import com.datastax.oss.driver.api.core.metadata.schema.KeyspaceMetadata;
import com.datastax.oss.driver.api.core.metrics.Metrics;
import com.datastax.oss.driver.api.core.session.ProgrammaticArguments;
import com.datastax.oss.driver.api.core.session.Request;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import com.datastax.oss.driver.internal.core.config.typesafe.DefaultDriverConfigLoader;
import com.datastax.oss.driver.internal.core.context.DefaultDriverContext;

public class CqlSessionMock implements CqlSession {

    @Override
    public CompletionStage<Boolean> checkSchemaAgreementAsync() {
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public <RequestT extends Request, ResultT> ResultT execute(RequestT arg0, GenericType<ResultT> arg1) {
        return null;
    }

    public ResultSet execute(String query) {
        if (query == null || query.isEmpty()) {
            return null;
        }

        return new ResultSetMock(query);
    }

    @Override
    public DriverContext getContext() {
        return new DefaultDriverContext(new DefaultDriverConfigLoader(), ProgrammaticArguments.builder().build());
    }

    @Override
    public Optional<CqlIdentifier> getKeyspace() {
        return Optional.empty();
    }

    @Override
    public Metadata getMetadata() {
        Metadata mocMetadata = new Metadata() {

            @Override
            public Optional<TokenMap> getTokenMap() {
                return Optional.empty();
            }

            @Override
            public Map<UUID, Node> getNodes() {
                return Map.of();
            }

            @Override
            public Map<CqlIdentifier, KeyspaceMetadata> getKeyspaces() {
                return Map.of();
            }
        };

        return mocMetadata;
    }

    @Override
    public Optional<Metrics> getMetrics() {
        return Optional.empty();
    }

    @Override
    public String getName() {
        return "Session mock";
    }

    @Override
    public boolean isSchemaMetadataEnabled() {
        return false;
    }

    @Override
    public CompletionStage<Metadata> refreshSchemaAsync() {
        Metadata mocMetadata = new Metadata() {

            @Override
            public Optional<TokenMap> getTokenMap() {
                return Optional.empty();
            }

            @Override
            public Map<UUID, Node> getNodes() {
                return Map.of();
            }

            @Override
            public Map<CqlIdentifier, KeyspaceMetadata> getKeyspaces() {
                return Map.of();
            }
        };

        return CompletableFuture.completedFuture(mocMetadata);
    }

    @Override
    public CompletionStage<Metadata> setSchemaMetadataEnabled(Boolean arg0) {
        Metadata mocMetadata = new Metadata() {

            @Override
            public Optional<TokenMap> getTokenMap() {
                return Optional.empty();
            }

            @Override
            public Map<UUID, Node> getNodes() {
                return Map.of();
            }

            @Override
            public Map<CqlIdentifier, KeyspaceMetadata> getKeyspaces() {
                return Map.of();
            }
        };

        return CompletableFuture.completedFuture(mocMetadata);
    }

    @Override
    public CompletionStage<Void> closeAsync() {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<Void> closeFuture() {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<Void> forceCloseAsync() {
        return CompletableFuture.completedFuture(null);
    }

}
