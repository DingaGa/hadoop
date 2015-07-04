/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.ClosedChannelException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.Test;

public class TestClose {

    @Test
    public void testWriteAfterClose() throws IOException {
        Configuration conf = new Configuration();
        MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf)
                .build();

        try {
            final byte[] data = "foo".getBytes();

            FileSystem fs = FileSystem.get(conf);
            OutputStream out = fs.create(new Path("/test"));

            out.write(data);
            out.close();
            try {
                // Should fail.
                out.write(data);
                fail("Should not have been able to write more data after file is closed.");
            } catch (ClosedChannelException cce) {
                // We got the correct exception. Ignoring.
            }
            // Should succeed. Double closes are OK.
            out.close();
        } finally {
            if (cluster != null) {
                cluster.shutdown();
            }
        }
    }

}
