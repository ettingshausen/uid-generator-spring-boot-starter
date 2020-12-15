/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wujun234.uid.worker;

import com.github.wujun234.uid.impl.DefaultUidGenerator;
import com.github.wujun234.uid.utils.DockerUtils;
import com.github.wujun234.uid.utils.NetUtils;
import com.github.wujun234.uid.worker.entity.WorkerNodeEntity;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represents a worker id assigner for {@link DefaultUidGenerator}
 *
 * @author yutianbao
 */
public interface WorkerIdAssigner {

    /**
     * Assign worker id for {@link DefaultUidGenerator}
     *
     * @return assigned worker id
     */
    long assignWorkerId();

    /**
     * Assign fake worker id
     *
     * @return assigned fake worker id
     */
    @Transactional(rollbackFor = Exception.class)
    default long assignFakeWorkerId() {
        WorkerNodeEntity workerNodeEntity = new WorkerNodeEntity();
        workerNodeEntity.setType(WorkerNodeType.FAKE.value());
        if (DockerUtils.isDocker()) {
            workerNodeEntity.setHostName(DockerUtils.getDockerHost());
            workerNodeEntity.setPort(DockerUtils.getDockerPort() + "-" + RandomUtils.nextInt(100000));
        } else {
            workerNodeEntity.setHostName(NetUtils.getLocalAddress());
            workerNodeEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
        }
        return workerNodeEntity.getId();
    }

}
