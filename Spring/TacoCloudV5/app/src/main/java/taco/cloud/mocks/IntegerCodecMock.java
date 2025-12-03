package taco.cloud.mocks;

import java.nio.ByteBuffer;

import org.apache.cassandra.utils.ByteBufferUtil;

import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

public class IntegerCodecMock implements TypeCodec<Integer> {
    @Override
    public Integer decode(ByteBuffer arg0, ProtocolVersion arg1) {
        return arg0.asIntBuffer().get(0);
    }

    @Override
    public ByteBuffer encode(Integer arg0, ProtocolVersion arg1) {
        return ByteBufferUtil.bytes(arg0);
    }

    @Override
    public String format(Integer arg0) {
        return Integer.toString(arg0);
    }

    @Override
    public DataType getCqlType() {
        return DataTypes.INT;
    }

    @Override
    public GenericType<Integer> getJavaType() {
        return GenericType.INTEGER;
    }

    @Override
    public Integer parse(String arg0) {
        return Integer.parseInt(arg0);
    }
}
