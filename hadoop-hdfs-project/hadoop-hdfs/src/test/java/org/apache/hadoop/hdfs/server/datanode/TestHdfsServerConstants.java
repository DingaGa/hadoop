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
package org.apache.hadoop.hdfs.server.datanode;

import org.apache.hadoop.hdfs.server.common.HdfsServerConstants.*;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Test enumerations in TestHdfsServerConstants.
 */
public class TestHdfsServerConstants {

    /**
     * Verify that parsing a StartupOption string gives the expected results.
     * If a RollingUpgradeStartupOption is specified than it is also checked.
     *
     * @param value
     * @param expectedOption
     * @param expectedRollupOption optional, may be null.
     */
    private static void verifyStartupOptionResult(String value,
                                                  StartupOption expectedOption,
                                                  RollingUpgradeStartupOption expectedRollupOption) {

        StartupOption option = StartupOption.getEnum(value);
        assertThat(option, is(expectedOption));

        if (expectedRollupOption != null) {
            assertThat(option.getRollingUpgradeStartupOption(), is(expectedRollupOption));
        }
    }

    /**
     * Test that we can parse a StartupOption string without the optional
     * RollingUpgradeStartupOption.
     */
    @Test
    public void testStartupOptionParsing() {
        verifyStartupOptionResult("FORMAT", StartupOption.FORMAT, null);
        verifyStartupOptionResult("REGULAR", StartupOption.REGULAR, null);
        verifyStartupOptionResult("CHECKPOINT", StartupOption.CHECKPOINT, null);
        verifyStartupOptionResult("UPGRADE", StartupOption.UPGRADE, null);
        verifyStartupOptionResult("ROLLBACK", StartupOption.ROLLBACK, null);
        verifyStartupOptionResult("FINALIZE", StartupOption.FINALIZE, null);
        verifyStartupOptionResult("ROLLINGUPGRADE", StartupOption.ROLLINGUPGRADE, null);
        verifyStartupOptionResult("IMPORT", StartupOption.IMPORT, null);
        verifyStartupOptionResult("INITIALIZESHAREDEDITS", StartupOption.INITIALIZESHAREDEDITS, null);

        try {
            verifyStartupOptionResult("UNKNOWN(UNKNOWNOPTION)", StartupOption.FORMAT, null);
            fail("Failed to get expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // Expected!
        }
    }

    /**
     * Test that we can parse a StartupOption string with a
     * RollingUpgradeStartupOption.
     */
    @Test
    public void testRollingUpgradeStartupOptionParsing() {
        verifyStartupOptionResult("ROLLINGUPGRADE(ROLLBACK)",
                StartupOption.ROLLINGUPGRADE,
                RollingUpgradeStartupOption.ROLLBACK);
        verifyStartupOptionResult("ROLLINGUPGRADE(DOWNGRADE)",
                StartupOption.ROLLINGUPGRADE,
                RollingUpgradeStartupOption.DOWNGRADE);

        try {
            verifyStartupOptionResult("ROLLINGUPGRADE(UNKNOWNOPTION)", StartupOption.ROLLINGUPGRADE, null);
            fail("Failed to get expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // Expected!
        }
    }
}
