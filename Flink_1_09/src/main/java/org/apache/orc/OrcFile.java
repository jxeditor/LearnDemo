//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.apache.orc;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.orc.OrcProto.StripeStatistics;
import org.apache.orc.impl.HadoopShims;
import org.apache.orc.impl.HadoopShimsFactory;
import org.apache.orc.impl.MemoryManagerImpl;
import org.apache.orc.impl.OrcTail;
import org.apache.orc.impl.ReaderImpl;
import org.apache.orc.impl.WriterImpl;
import org.apache.orc.impl.WriterInternal;
import org.apache.orc.impl.writer.WriterImplV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrcFile {
    private static final Logger LOG = LoggerFactory.getLogger(OrcFile.class);
    public static final String MAGIC = "ORC";
    public static final WriterVersion CURRENT_WRITER;
    private static ThreadLocal<MemoryManager> memoryManager;

    protected OrcFile() {
    }

    public static ReaderOptions readerOptions(Configuration conf) {
        return new ReaderOptions(conf);
    }

    public static Reader createReader(Path path, ReaderOptions options) throws IOException {
        return new ReaderImpl(path, options);
    }

    public static WriterOptions writerOptions(Configuration conf) {
        return new WriterOptions((Properties)null, conf);
    }

    public static WriterOptions writerOptions(Properties tableProperties, Configuration conf) {
        return new WriterOptions(tableProperties, conf);
    }

    private static synchronized MemoryManager getStaticMemoryManager(final Configuration conf) {
        if (memoryManager == null) {
            memoryManager = new ThreadLocal<MemoryManager>() {
                protected MemoryManager initialValue() {
                    return new MemoryManagerImpl(conf);
                }
            };
        }

        return (MemoryManager)memoryManager.get();
    }

    public static Writer createWriter(Path path, WriterOptions opts) throws IOException {
        FileSystem fs = opts.getFileSystem() == null ? path.getFileSystem(opts.getConfiguration()) : opts.getFileSystem();
        switch(opts.getVersion()) {
        case V_0_11:
        case V_0_12:
            return new WriterImpl(fs, path, opts);
        case UNSTABLE_PRE_2_0:
            return new WriterImplV2(fs, path, opts);
        default:
            throw new IllegalArgumentException("Unknown version " + opts.getVersion());
        }
    }

    static boolean understandFormat(Path path, Reader reader) {
        if (reader.getFileVersion() == Version.FUTURE) {
            LOG.info("Can't merge {} because it has a future version.", path);
            return false;
        } else if (reader.getWriterVersion() == WriterVersion.FUTURE) {
            LOG.info("Can't merge {} because it has a future writerVersion.", path);
            return false;
        } else {
            return true;
        }
    }

    static boolean readerIsCompatible(TypeDescription schema, Version fileVersion, WriterVersion writerVersion, int rowIndexStride, CompressionKind compression, Map<String, ByteBuffer> userMetadata, Path path, Reader reader) {
        if (!reader.getSchema().equals(schema)) {
            LOG.info("Can't merge {} because of different schemas {} vs {}", new Object[]{path, reader.getSchema(), schema});
            return false;
        } else if (reader.getCompressionKind() != compression) {
            LOG.info("Can't merge {} because of different compression {} vs {}", new Object[]{path, reader.getCompressionKind(), compression});
            return false;
        } else if (reader.getFileVersion() != fileVersion) {
            LOG.info("Can't merge {} because of different file versions {} vs {}", new Object[]{path, reader.getFileVersion(), fileVersion});
            return false;
        } else if (reader.getWriterVersion() != writerVersion) {
            LOG.info("Can't merge {} because of different writer versions {} vs {}", new Object[]{path, reader.getFileVersion(), fileVersion});
            return false;
        } else if (reader.getRowIndexStride() != rowIndexStride) {
            LOG.info("Can't merge {} because of different row index strides {} vs {}", new Object[]{path, reader.getRowIndexStride(), rowIndexStride});
            return false;
        } else {
            Iterator var8 = reader.getMetadataKeys().iterator();

            while(var8.hasNext()) {
                String key = (String)var8.next();
                if (userMetadata.containsKey(key)) {
                    ByteBuffer currentValue = (ByteBuffer)userMetadata.get(key);
                    ByteBuffer newValue = reader.getMetadataValue(key);
                    if (!newValue.equals(currentValue)) {
                        LOG.info("Can't merge {} because of different user metadata {}", path, key);
                        return false;
                    }
                }
            }

            return true;
        }
    }

    static void mergeMetadata(Map<String, ByteBuffer> metadata, Reader reader) {
        Iterator var2 = reader.getMetadataKeys().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            metadata.put(key, reader.getMetadataValue(key));
        }

    }

    public static List<Path> mergeFiles(Path outputPath, WriterOptions options, List<Path> inputFiles) throws IOException {
        Writer output = null;
        Configuration conf = options.getConfiguration();

        try {
            byte[] buffer = new byte[0];
            TypeDescription schema = null;
            CompressionKind compression = null;
            int bufferSize = 0;
            Version fileVersion = null;
            WriterVersion writerVersion = null;
            int rowIndexStride = 0;
            List<Path> result = new ArrayList(inputFiles.size());
            Map<String, ByteBuffer> userMetadata = new HashMap();
            Iterator var14 = inputFiles.iterator();

            while(true) {
                Path input;
                FileSystem fs;
                Reader reader;
                while(true) {
                    do {
                        if (!var14.hasNext()) {
                            if (output != null) {
                                var14 = userMetadata.entrySet().iterator();

                                while(var14.hasNext()) {
                                    Entry<String, ByteBuffer> entry = (Entry)var14.next();
                                    output.addUserMetadata((String)entry.getKey(), (ByteBuffer)entry.getValue());
                                }

                                output.close();
                            }

                            return result;
                        }

                        input = (Path)var14.next();
                        fs = input.getFileSystem(conf);
                        reader = createReader(input, readerOptions(options.getConfiguration()).filesystem(fs));
                    } while(!understandFormat(input, reader));

                    if (schema == null) {
                        schema = reader.getSchema();
                        compression = reader.getCompressionKind();
                        bufferSize = reader.getCompressionSize();
                        rowIndexStride = reader.getRowIndexStride();
                        fileVersion = reader.getFileVersion();
                        writerVersion = reader.getWriterVersion();
                        options.bufferSize(bufferSize).version(fileVersion).writerVersion(writerVersion).compress(compression).rowIndexStride(rowIndexStride).setSchema(schema);
                        if (compression != CompressionKind.NONE) {
                            options.enforceBufferSize().bufferSize(bufferSize);
                        }

                        mergeMetadata(userMetadata, reader);
                        output = createWriter(outputPath, options);
                        break;
                    }

                    if (readerIsCompatible(schema, fileVersion, writerVersion, rowIndexStride, compression, userMetadata, input, reader)) {
                        mergeMetadata(userMetadata, reader);
                        if (bufferSize < reader.getCompressionSize()) {
                            bufferSize = reader.getCompressionSize();
                            ((WriterInternal)output).increaseCompressionSize(bufferSize);
                        }
                        break;
                    }
                }

                List<StripeStatistics> statList = reader.getOrcProtoStripeStatistics();
                FSDataInputStream inputStream = fs.open(input);
                Throwable var20 = null;

                try {
                    int stripeNum = 0;
                    result.add(input);
                    Iterator var22 = reader.getStripes().iterator();

                    while(var22.hasNext()) {
                        StripeInformation stripe = (StripeInformation)var22.next();
                        int length = (int)stripe.getLength();
                        if (buffer.length < length) {
                            buffer = new byte[length];
                        }

                        long offset = stripe.getOffset();
                        inputStream.readFully(offset, buffer, 0, length);
                        output.appendStripe(buffer, 0, length, stripe, (StripeStatistics)statList.get(stripeNum++));
                    }
                } catch (Throwable var39) {
                    var20 = var39;
                    throw var39;
                } finally {
                    if (inputStream != null) {
                        if (var20 != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable var38) {
                                var20.addSuppressed(var38);
                            }
                        } else {
                            inputStream.close();
                        }
                    }

                }
            }
        } catch (IOException var41) {
            if (output != null) {
                try {
                    output.close();
                } catch (Throwable var37) {
                }

                try {
                    FileSystem fs = options.getFileSystem() == null ? outputPath.getFileSystem(conf) : options.getFileSystem();
                    fs.delete(outputPath, false);
                } catch (Throwable var36) {
                }
            }

            throw var41;
        }
    }

    static {
        CURRENT_WRITER = WriterVersion.HIVE_13083;
        memoryManager = null;
    }

    public static class WriterOptions implements Cloneable {
        private final Configuration configuration;
        private FileSystem fileSystemValue = null;
        private TypeDescription schema = null;
        private long stripeSizeValue;
        private long blockSizeValue;
        private int rowIndexStrideValue;
        private int bufferSizeValue;
        private boolean enforceBufferSize = false;
        private boolean blockPaddingValue;
        private CompressionKind compressValue;
        private MemoryManager memoryManagerValue;
        private Version versionValue;
        private WriterCallback callback;
        private EncodingStrategy encodingStrategy;
        private CompressionStrategy compressionStrategy;
        private double paddingTolerance;
        private String bloomFilterColumns;
        private double bloomFilterFpp;
        private BloomFilterVersion bloomFilterVersion;
        private PhysicalWriter physicalWriter;
        private WriterVersion writerVersion;
        private boolean useUTCTimestamp;
        private boolean overwrite;
        private boolean writeVariableLengthBlocks;
        private HadoopShims shims;
        private String directEncodingColumns;

        protected WriterOptions(Properties tableProperties, Configuration conf) {
            this.writerVersion = OrcFile.CURRENT_WRITER;
            this.configuration = conf;
            this.memoryManagerValue = OrcFile.getStaticMemoryManager(conf);
            this.overwrite = OrcConf.OVERWRITE_OUTPUT_FILE.getBoolean(tableProperties, conf);
            this.stripeSizeValue = OrcConf.STRIPE_SIZE.getLong(tableProperties, conf);
            this.blockSizeValue = OrcConf.BLOCK_SIZE.getLong(tableProperties, conf);
            this.rowIndexStrideValue = (int)OrcConf.ROW_INDEX_STRIDE.getLong(tableProperties, conf);
            this.bufferSizeValue = (int)OrcConf.BUFFER_SIZE.getLong(tableProperties, conf);
            this.blockPaddingValue = OrcConf.BLOCK_PADDING.getBoolean(tableProperties, conf);
            this.compressValue = CompressionKind.valueOf(OrcConf.COMPRESS.getString(tableProperties, conf).toUpperCase());
            this.enforceBufferSize = OrcConf.ENFORCE_COMPRESSION_BUFFER_SIZE.getBoolean(tableProperties, conf);
            String versionName = OrcConf.WRITE_FORMAT.getString(tableProperties, conf);
            this.versionValue = Version.byName(versionName);
            String enString = OrcConf.ENCODING_STRATEGY.getString(tableProperties, conf);
            this.encodingStrategy = EncodingStrategy.valueOf(enString);
            String compString = OrcConf.COMPRESSION_STRATEGY.getString(tableProperties, conf);
            this.compressionStrategy = CompressionStrategy.valueOf(compString);
            this.paddingTolerance = OrcConf.BLOCK_PADDING_TOLERANCE.getDouble(tableProperties, conf);
            this.bloomFilterColumns = OrcConf.BLOOM_FILTER_COLUMNS.getString(tableProperties, conf);
            this.bloomFilterFpp = OrcConf.BLOOM_FILTER_FPP.getDouble(tableProperties, conf);
            this.bloomFilterVersion = BloomFilterVersion.fromString(OrcConf.BLOOM_FILTER_WRITE_VERSION.getString(tableProperties, conf));
            this.shims = HadoopShimsFactory.get();
            this.writeVariableLengthBlocks = OrcConf.WRITE_VARIABLE_LENGTH_BLOCKS.getBoolean(tableProperties, conf);
            this.directEncodingColumns = OrcConf.DIRECT_ENCODING_COLUMNS.getString(tableProperties, conf);
        }

        public WriterOptions clone() {
            try {
                return (WriterOptions)super.clone();
            } catch (CloneNotSupportedException var2) {
                throw new AssertionError("Expected super.clone() to work");
            }
        }

        public WriterOptions fileSystem(FileSystem value) {
            this.fileSystemValue = value;
            return this;
        }

        public WriterOptions overwrite(boolean value) {
            this.overwrite = value;
            return this;
        }

        public WriterOptions stripeSize(long value) {
            this.stripeSizeValue = value;
            return this;
        }

        public WriterOptions blockSize(long value) {
            this.blockSizeValue = value;
            return this;
        }

        public WriterOptions rowIndexStride(int value) {
            this.rowIndexStrideValue = value;
            return this;
        }

        public WriterOptions bufferSize(int value) {
            this.bufferSizeValue = value;
            return this;
        }

        public WriterOptions enforceBufferSize() {
            this.enforceBufferSize = true;
            return this;
        }

        public WriterOptions blockPadding(boolean value) {
            this.blockPaddingValue = value;
            return this;
        }

        public WriterOptions encodingStrategy(EncodingStrategy strategy) {
            this.encodingStrategy = strategy;
            return this;
        }

        public WriterOptions paddingTolerance(double value) {
            this.paddingTolerance = value;
            return this;
        }

        public WriterOptions bloomFilterColumns(String columns) {
            this.bloomFilterColumns = columns;
            return this;
        }

        public WriterOptions bloomFilterFpp(double fpp) {
            this.bloomFilterFpp = fpp;
            return this;
        }

        public WriterOptions compress(CompressionKind value) {
            this.compressValue = value;
            return this;
        }

        public WriterOptions setSchema(TypeDescription schema) {
            this.schema = schema;
            return this;
        }

        public WriterOptions version(Version value) {
            this.versionValue = value;
            return this;
        }

        public WriterOptions callback(WriterCallback callback) {
            this.callback = callback;
            return this;
        }

        public WriterOptions bloomFilterVersion(BloomFilterVersion version) {
            this.bloomFilterVersion = version;
            return this;
        }

        public WriterOptions physicalWriter(PhysicalWriter writer) {
            this.physicalWriter = writer;
            return this;
        }

        public WriterOptions memory(MemoryManager value) {
            this.memoryManagerValue = value;
            return this;
        }

        public WriterOptions writeVariableLengthBlocks(boolean value) {
            this.writeVariableLengthBlocks = value;
            return this;
        }

        public WriterOptions setShims(HadoopShims value) {
            this.shims = value;
            return this;
        }

        protected WriterOptions writerVersion(WriterVersion version) {
            if (version == WriterVersion.FUTURE) {
                throw new IllegalArgumentException("Can't write a future version.");
            } else {
                this.writerVersion = version;
                return this;
            }
        }

        public WriterOptions useUTCTimestamp(boolean value) {
            this.useUTCTimestamp = value;
            return this;
        }

        public WriterOptions directEncodingColumns(String value) {
            this.directEncodingColumns = value;
            return this;
        }

        public boolean getBlockPadding() {
            return this.blockPaddingValue;
        }

        public long getBlockSize() {
            return this.blockSizeValue;
        }

        public String getBloomFilterColumns() {
            return this.bloomFilterColumns;
        }

        public boolean getOverwrite() {
            return this.overwrite;
        }

        public FileSystem getFileSystem() {
            return this.fileSystemValue;
        }

        public Configuration getConfiguration() {
            return this.configuration;
        }

        public TypeDescription getSchema() {
            return this.schema;
        }

        public long getStripeSize() {
            return this.stripeSizeValue;
        }

        public CompressionKind getCompress() {
            return this.compressValue;
        }

        public WriterCallback getCallback() {
            return this.callback;
        }

        public Version getVersion() {
            return this.versionValue;
        }

        public MemoryManager getMemoryManager() {
            return this.memoryManagerValue;
        }

        public int getBufferSize() {
            return this.bufferSizeValue;
        }

        public boolean isEnforceBufferSize() {
            return this.enforceBufferSize;
        }

        public int getRowIndexStride() {
            return this.rowIndexStrideValue;
        }

        public CompressionStrategy getCompressionStrategy() {
            return this.compressionStrategy;
        }

        public EncodingStrategy getEncodingStrategy() {
            return this.encodingStrategy;
        }

        public double getPaddingTolerance() {
            return this.paddingTolerance;
        }

        public double getBloomFilterFpp() {
            return this.bloomFilterFpp;
        }

        public BloomFilterVersion getBloomFilterVersion() {
            return this.bloomFilterVersion;
        }

        public PhysicalWriter getPhysicalWriter() {
            return this.physicalWriter;
        }

        public WriterVersion getWriterVersion() {
            return this.writerVersion;
        }

        public boolean getWriteVariableLengthBlocks() {
            return this.writeVariableLengthBlocks;
        }

        public HadoopShims getHadoopShims() {
            return this.shims;
        }

        public boolean getUseUTCTimestamp() {
            return this.useUTCTimestamp;
        }

        public String getDirectEncodingColumns() {
            return this.directEncodingColumns;
        }
    }

    public static enum BloomFilterVersion {
        ORIGINAL("original"),
        UTF8("utf8");

        private final String id;

        private BloomFilterVersion(String id) {
            this.id = id;
        }

        public String toString() {
            return this.id;
        }

        public static BloomFilterVersion fromString(String s) {
            BloomFilterVersion[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                BloomFilterVersion version = var1[var3];
                if (version.id.equals(s)) {
                    return version;
                }
            }

            throw new IllegalArgumentException("Unknown BloomFilterVersion " + s);
        }
    }

    public interface WriterCallback {
        void preStripeWrite(WriterContext var1) throws IOException;

        void preFooterWrite(WriterContext var1) throws IOException;
    }

    public interface WriterContext {
        Writer getWriter();
    }

    public static class ReaderOptions {
        private final Configuration conf;
        private FileSystem filesystem;
        private long maxLength = 9223372036854775807L;
        private OrcTail orcTail;
        private FileMetadata fileMetadata;
        private boolean useUTCTimestamp;

        public ReaderOptions(Configuration conf) {
            this.conf = conf;
        }

        public ReaderOptions filesystem(FileSystem fs) {
            this.filesystem = fs;
            return this;
        }

        public ReaderOptions maxLength(long val) {
            this.maxLength = val;
            return this;
        }

        public ReaderOptions orcTail(OrcTail tail) {
            this.orcTail = tail;
            return this;
        }

        public Configuration getConfiguration() {
            return this.conf;
        }

        public FileSystem getFilesystem() {
            return this.filesystem;
        }

        public long getMaxLength() {
            return this.maxLength;
        }

        public OrcTail getOrcTail() {
            return this.orcTail;
        }

        public ReaderOptions fileMetadata(FileMetadata metadata) {
            this.fileMetadata = metadata;
            return this;
        }

        public FileMetadata getFileMetadata() {
            return this.fileMetadata;
        }

        public ReaderOptions useUTCTimestamp(boolean value) {
            this.useUTCTimestamp = value;
            return this;
        }

        public boolean getUseUTCTimestamp() {
            return this.useUTCTimestamp;
        }
    }

    public static enum CompressionStrategy {
        SPEED,
        COMPRESSION;

        private CompressionStrategy() {
        }
    }

    public static enum EncodingStrategy {
        SPEED,
        COMPRESSION;

        private EncodingStrategy() {
        }
    }

    public static enum WriterVersion {
        ORIGINAL(WriterImplementation.ORC_JAVA, 0),
        HIVE_8732(WriterImplementation.ORC_JAVA, 1),
        HIVE_4243(WriterImplementation.ORC_JAVA, 2),
        HIVE_12055(WriterImplementation.ORC_JAVA, 3),
        HIVE_13083(WriterImplementation.ORC_JAVA, 4),
        ORC_101(WriterImplementation.ORC_JAVA, 5),
        ORC_135(WriterImplementation.ORC_JAVA, 6),
        ORC_517(WriterImplementation.ORC_JAVA, 7),
        ORC_CPP_ORIGINAL(WriterImplementation.ORC_CPP, 6),
        PRESTO_ORIGINAL(WriterImplementation.PRESTO, 6),
        SCRITCHLEY_GO_ORIGINAL(WriterImplementation.SCRITCHLEY_GO, 6),
        FUTURE(WriterImplementation.UNKNOWN, 2147483647);

        private final int id;
        private final WriterImplementation writer;
        private static final WriterVersion[][] values = new WriterVersion[WriterImplementation.values().length][];

        public WriterImplementation getWriterImplementation() {
            return this.writer;
        }

        public int getId() {
            return this.id;
        }

        private WriterVersion(WriterImplementation writer, int id) {
            this.writer = writer;
            this.id = id;
        }

        public static WriterVersion from(WriterImplementation writer, int val) {
            if (writer == WriterImplementation.UNKNOWN) {
                return FUTURE;
            } else if (writer != WriterImplementation.ORC_JAVA && val < 6) {
                throw new IllegalArgumentException("ORC File with illegval version " + val + " for writer " + writer);
            } else {
                WriterVersion[] versions = values[writer.id];
                if (val >= 0 && versions.length >= val) {
                    WriterVersion result = versions[val];
                    return result == null ? FUTURE : result;
                } else {
                    return FUTURE;
                }
            }
        }

        public boolean includes(WriterVersion fix) {
            return this.writer != fix.writer || this.id >= fix.id;
        }

        static {
            WriterVersion[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                WriterVersion v = var0[var2];
                WriterImplementation writer = v.writer;
                if (writer != WriterImplementation.UNKNOWN) {
                    if (values[writer.id] == null) {
                        values[writer.id] = new WriterVersion[values().length];
                    }

                    if (values[writer.id][v.id] != null) {
                        throw new IllegalArgumentException("Duplicate WriterVersion id " + v);
                    }

                    values[writer.id][v.id] = v;
                }
            }

        }
    }

    public static enum WriterImplementation {
        ORC_JAVA(0),
        ORC_CPP(1),
        PRESTO(2),
        SCRITCHLEY_GO(3),
        UNKNOWN(2147483647);

        private final int id;

        private WriterImplementation(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static WriterImplementation from(int id) {
            WriterImplementation[] values = values();
            return id >= 0 && id < values.length - 1 ? values[id] : UNKNOWN;
        }
    }

    public static enum Version {
        V_0_11("0.11", 0, 11),
        V_0_12("0.12", 0, 12),
        UNSTABLE_PRE_2_0("UNSTABLE-PRE-2.0", 1, 9999),
        FUTURE("future", 2147483647, 2147483647);

        public static final Version CURRENT = V_0_12;
        private final String name;
        private final int major;
        private final int minor;

        private Version(String name, int major, int minor) {
            this.name = name;
            this.major = major;
            this.minor = minor;
        }

        public static Version byName(String name) {
            Version[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Version version = var1[var3];
                if (version.name.equals(name)) {
                    return version;
                }
            }

            throw new IllegalArgumentException("Unknown ORC version " + name);
        }

        public String getName() {
            return this.name;
        }

        public int getMajor() {
            return this.major;
        }

        public int getMinor() {
            return this.minor;
        }
    }
}
