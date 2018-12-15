package com.aspire.zkclient.queue;

/**
 * 自定义 --- 节点数据为null异常
 *
 * @author JustryDeng
 * @date 2018/12/12 15:23
 */
public class NodeDataIsNullException extends Exception {

    public NodeDataIsNullException() {
    }

    public NodeDataIsNullException(String message) {
        super(message);
    }

    public NodeDataIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeDataIsNullException(Throwable cause) {
        super(cause);
    }

    public NodeDataIsNullException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
