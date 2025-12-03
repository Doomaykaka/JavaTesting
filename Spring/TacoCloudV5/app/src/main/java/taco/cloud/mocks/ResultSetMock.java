package taco.cloud.mocks;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.cassandra.utils.ByteBufferUtil;

import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ExecutionInfo;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.internal.core.cql.DefaultColumnDefinition;
import com.datastax.oss.driver.internal.core.cql.DefaultColumnDefinitions;
import com.datastax.oss.driver.internal.core.cql.DefaultRow;
import com.datastax.oss.protocol.internal.response.result.ColumnSpec;

public class ResultSetMock implements ResultSet {
    private String query;

    public ResultSetMock(String query) {
        this.query = query;
    }

    @Override
    public int getAvailableWithoutFetching() {
        return 0;
    }

    @Override
    public ColumnDefinitions getColumnDefinitions() {
        ColumnDefinitions columnDefinitions = DefaultColumnDefinitions.valueOf(List.of());

        switch (query) {
        case "SELECT now() FROM system.local":
            ColumnSpec timeColumnSpec = new ColumnSpec("time", "results", "time", 0, new IntRawTypeMock("0"));
            DefaultColumnDefinition timeColumnDef = new DefaultColumnDefinition(timeColumnSpec, null);
            columnDefinitions = DefaultColumnDefinitions.valueOf(List.of(timeColumnDef));
            break;
        }

        return columnDefinitions;
    }

    @Override
    public List<ExecutionInfo> getExecutionInfos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isFullyFetched() {
        return false;
    }

    @Override
    public Iterator<Row> iterator() {
        List<Row> result = new ArrayList<Row>();

        switch (query) {
        case "SELECT now() FROM system.local":
            ByteBuffer data = ByteBufferUtil.bytes(Instant.now().getEpochSecond());
            DefaultRow resultRow = new DefaultRow(getColumnDefinitions(), List.of(data));
            result.add(resultRow);
            break;
        }

        return result.iterator();
    }

    @Override
    public boolean wasApplied() {
        return false;
    }
}
