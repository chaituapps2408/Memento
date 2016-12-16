package com.chaiy.memento.database.columns;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Chaiy on 12/4/2016.
 */

public interface FavouriteColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _id = "_id";

    @DataType(DataType.Type.TEXT)
    String date = "date";

}
