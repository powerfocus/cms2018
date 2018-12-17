package org.py.explorer;

import java.util.ArrayList;
import java.util.List;

public class Selector {
    private List<String> allowTxts;
    private List<String> allowImgs;
    public Selector() {
        allowTxts = new ArrayList<>();
        allowImgs = new ArrayList<>();
    }

    public boolean imgContains(String item) {
        return allowImgs.contains(item.trim());
    }

    public List<String> getAllowTxts() {
        return allowTxts;
    }

    public void setAllowTxts(List<String> allowTxts) {
        this.allowTxts = allowTxts;
    }

    public List<String> getAllowImgs() {
        return allowImgs;
    }

    public void setAllowImgs(List<String> allowImgs) {
        this.allowImgs = allowImgs;
    }
}
