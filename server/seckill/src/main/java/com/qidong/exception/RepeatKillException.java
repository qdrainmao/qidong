package com.qidong.exception;

/**
 * spring事务机制只回滚运行期异常（不try catch）
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
