package com.github.drinkjava2.jdialects.model;

import com.github.drinkjava2.jdialects.Dialect;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * to support Sequence
 */
public class SequenceModel {

    private String name;

    private long minValue;

    private long maxValue;

    private int increment;

    private boolean cycleFlag;

    public String generateDDL(){
        throw new UnsupportedOperationException("not implemented yet.");
    }

    /**
     * for oracle: select * from user_sequences;
     * @param con
     * @param dialect
     * @return
     */
    //todo
    public static List<SequenceModel> getModelsFromDb(Connection con, Dialect dialect){
        List<SequenceModel> sequenceModels = new ArrayList<>();
        return sequenceModels;
    }
}
