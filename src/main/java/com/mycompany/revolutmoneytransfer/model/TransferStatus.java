package com.mycompany.revolutmoneytransfer.model;


public enum TransferStatus {
    INITIATED(1), PROCESSING(2), FAILED(3), SUCCESSFUL(4);

    private int id;

    TransferStatus(int id) {
        this.id = id;
    }

    public static TransferStatus valueOf(int id) {
        for(TransferStatus e : values()) {
            if(e.id == id) return e;
        }

        return null;
    }

    public int getId() {
        return id;
    }
}
