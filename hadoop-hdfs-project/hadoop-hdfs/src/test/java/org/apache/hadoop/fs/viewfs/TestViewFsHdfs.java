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
package org.apache.hadoop.fs.viewfs;


import java.io.IOException;
import java.net.URISyntaxException;

import javax.security.auth.login.LoginException;

import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FileContextTestHelper;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class TestViewFsHdfs extends ViewFsBaseTest {

    private static MiniDFSCluster cluster;
    private static final HdfsConfiguration CONF = new HdfsConfiguration();
    private static FileContext fc;

    @Override
    protected FileContextTestHelper createFileContextHelper() {
        return new FileContextTestHelper("/tmp/TestViewFsHdfs");
    }


    @BeforeClass
    public static void clusterSetupAtBegining() throws IOException,
            LoginException, URISyntaxException {
        SupportsBlocks = true;
        CONF.setBoolean(
                DFSConfigKeys.DFS_NAMENODE_DELEGATION_TOKEN_ALWAYS_USE_KEY, true);

        cluster = new MiniDFSCluster.Builder(CONF).numDataNodes(2).build();
        cluster.waitClusterUp();
        fc = FileContext.getFileContext(cluster.getURI(0), CONF);
        Path defaultWorkingDirectory = fc.makeQualified(new Path("/user/" +
                UserGroupInformation.getCurrentUser().getShortUserName()));
        fc.mkdir(defaultWorkingDirectory, FileContext.DEFAULT_PERM, true);
    }


    @AfterClass
    public static void ClusterShutdownAtEnd() throws Exception {
        cluster.shutdown();
    }

    @Override
    @Before
    public void setUp() throws Exception {
        // create the test root on local_fs
        fcTarget = fc;
        super.setUp();
    }

    /**
     * This overrides the default implementation since hdfs does have delegation
     * tokens.
     */
    @Override
    int getExpectedDelegationTokenCount() {
        return 8;
    }

}
