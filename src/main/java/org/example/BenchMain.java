package org.example;

import org.apache.hc.core5.http.NotImplementedException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@State(Scope.Benchmark)
public class BenchMain {
    // 1K, 1M, 10M
    @Param({"1000", "1000000", "10000000"})
    int respSize;

    public static class UnknownContentLengthByteArrayEntity4 extends org.apache.http.entity.AbstractHttpEntity {
        private final ByteArrayInputStream is;

        public UnknownContentLengthByteArrayEntity4(byte[] b) {
            is = new ByteArrayInputStream(b);
        }

        @Override
        public boolean isRepeatable() {
            return false;
        }

        @Override
        public long getContentLength() {
            return -1;
        }

        @Override
        public InputStream getContent() {
            return is;
        }

        @Override
        public void writeTo(OutputStream outStream) {
            throw new RuntimeException(new NotImplementedException());
        }

        @Override
        public boolean isStreaming() {
            return false;
        }
    }

    public static class UnknownContentLengthByteArrayEntity5 extends org.apache.hc.core5.http.io.entity.AbstractHttpEntity {
        private final ByteArrayInputStream inputStream;

        public UnknownContentLengthByteArrayEntity5(byte[] b) {
            super("application/octet-stream", null);
            inputStream = new ByteArrayInputStream(b);
        }

        @Override
        public InputStream getContent() {
            return inputStream;
        }

        @Override
        public boolean isStreaming() {
            return false;
        }

        @Override
        public void close() throws IOException {
            inputStream.close();
        }

        @Override
        public long getContentLength() {
            return -1;
        }
    }

    private static UnknownContentLengthByteArrayEntity5 buildEntity5(int n) {
        final byte[] buf = new byte[n];
        return new UnknownContentLengthByteArrayEntity5(buf);
    }

    private static UnknownContentLengthByteArrayEntity4 buildEntity4(int n) {
        final byte[] buf = new byte[n];
        return new UnknownContentLengthByteArrayEntity4(buf);
    }

    @Benchmark
    public void entityUtils5(Blackhole bh) throws IOException {
        try (final var entity = buildEntity5(respSize)) {
            final byte[] content = org.apache.hc.core5.http.io.entity.EntityUtils.toByteArray(entity);
            bh.consume(content);
        }
    }

    @Benchmark
    public void entityUtils4(Blackhole bh) throws IOException {
        final var entity = buildEntity4(respSize);
        final byte[] content = org.apache.http.util.EntityUtils.toByteArray(entity);
        bh.consume(content);
    }

    @Benchmark
    public void inputStreamReadNBytes(Blackhole bh) throws IOException {
        try (final var entity = buildEntity5(respSize)) {
            final byte[] content = entity.getContent().readAllBytes();
            bh.consume(content);
        }
    }

}
