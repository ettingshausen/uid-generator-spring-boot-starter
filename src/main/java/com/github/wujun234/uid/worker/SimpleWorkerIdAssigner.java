package com.github.wujun234.uid.worker;

/**
 * @author ettingshausen
 */
public class SimpleWorkerIdAssigner implements WorkerIdAssigner {
    @Override
    public long assignWorkerId() {
        // todo 根据MAC地址生成机器id
        return 1;
    }
}
