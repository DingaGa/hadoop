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

package org.apache.hadoop.yarn.server.nodemanager.containermanager.localizer;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.PositionedReadable;
import org.apache.hadoop.fs.Seekable;

/** mock streams in unit tests */
public class FakeFSDataInputStream
        extends FilterInputStream implements Seekable, PositionedReadable {
    public FakeFSDataInputStream(InputStream in) {
        super(in);
    }

    public void seek(long pos) throws IOException {
    }

    public long getPos() throws IOException {
        return -1;
    }

    public boolean seekToNewSource(long targetPos) throws IOException {
        return false;
    }

    public int read(long position, byte[] buffer, int offset, int length)
            throws IOException {
        return -1;
    }

    public void readFully(long position, byte[] buffer, int offset, int length)
            throws IOException {
    }

    public void readFully(long position, byte[] buffer) throws IOException {
    }
}
