package com.chaiy.memento.database.columns;

import net.simonvt.schematic.annotation.DataType;

/**
 * Created by Chaiy on 12/4/2016.
 */

public interface NewsColumns {

    @DataType(DataType.Type.TEXT)
    String headLine = "headLine";

    @DataType(DataType.Type.TEXT)
    String publishedDate = "publishedDate";

    @DataType(DataType.Type.TEXT)
    String snippet = "snippet";

    @DataType(DataType.Type.TEXT)
    String imagePath = "imagePath";

    @DataType(DataType.Type.TEXT)
    String source = "source";

    @DataType(DataType.Type.TEXT)
    String url = "url";

}
