package com.snowy.babycare.bean;

import java.util.Map;
import java.util.Objects;

/**
 * Created by snowy on 16/3/3.
 */
public class Result {
    private Member result;
    private int code;

    public Result() {
    }

    public void setResult(Member result) {
        this.result = result;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Member getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                ", code=" + code +
                '}';
    }
}
