package com.example.qlbdt.fEnum;

public enum EStatusFragment {
    FRAGMENT_HOME (0),
    FRAGMENT_SEARCH (1),
    FRAGMENT_ACCOUNT (2),
    FRAGMENT_HISTORY (3),
    FRAGMENT_SUPPORT (4),
    FRAGMENT_TUTORIAL (5);

    public int getStatusFragment() {
        return statusFragment;
    }

    private int statusFragment;

    EStatusFragment(int statusFragment) {
        this.statusFragment = statusFragment;
    }


}
