package org.example.plant.realization;

import org.example.plant.protocol.Metropolis;
import org.example.plant.protocol.Search;

public class SearchTask implements Search {
    private static Search instance;

    public static Search getInstance() {
        if (instance == null) {
            instance = new SearchTask();
        }
        return instance;
    }

    @Override
    public void goSearch(Metropolis capitalWinCont, String googling) {
        capitalWinCont.search(googling);
    }
}
