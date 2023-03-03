package com.example.qlbdt.fEnum;

public enum EStatusFragment {
    FRAGMENT_HOME (0),
    FRAGMENT_PHONE (1),
    FRAGMENT_ACCOUNT (2);

    public int getStatusFragment() {
        return statusFragment;
    }

    private int statusFragment;

    EStatusFragment(int statusFragment) {
        this.statusFragment = statusFragment;
    }


}
