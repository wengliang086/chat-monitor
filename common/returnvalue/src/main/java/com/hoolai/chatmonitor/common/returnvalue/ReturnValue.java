package com.hoolai.chatmonitor.common.returnvalue;

public class ReturnValue<T> extends DefaultReturnCode implements java.io.Serializable{

    /**
     * 具体的内容.
     */
    private T value;

    public ReturnValue() {
        super();
    }

    public ReturnValue(ReturnCode returnCode) {
        super(returnCode);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReturnValue{" +
                "value=" + value +
                ", group='" + group + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
