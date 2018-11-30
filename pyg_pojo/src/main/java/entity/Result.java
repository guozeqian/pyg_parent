package entity;

import java.io.Serializable;

public class Result implements Serializable {
    private boolean success;//操作成功或失败
    private String message;//消息是什么

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
