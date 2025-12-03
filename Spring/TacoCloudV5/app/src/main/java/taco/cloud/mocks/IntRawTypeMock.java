package taco.cloud.mocks;

import com.datastax.oss.protocol.internal.PrimitiveCodec;
import com.datastax.oss.protocol.internal.response.result.RawType.RawCustom;

public class IntRawTypeMock extends RawCustom {
    private String id;

    protected IntRawTypeMock(String id) {
        super(id);

        this.setId(id);
    }

    @Override
    public <B> void encode(B arg0, PrimitiveCodec<B> arg1, int arg2) {
    }

    @Override
    public int encodedSize(int arg0) {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
