package com.chaiy.memento.database.columns;

import net.simonvt.schematic.annotation.DataType;

/**
 * Created by Chaiy on 12/4/2016.
 */

public interface MovieColumns {

    @DataType(DataType.Type.TEXT)
    String _id = "id";

    @DataType(DataType.Type.TEXT)
    String movieName = "movieName";

    @DataType(DataType.Type.TEXT)
    String releaseDate = "releaseDate";

    @DataType(DataType.Type.TEXT)
    String overview = "overview";

    @DataType(DataType.Type.TEXT)
    String language = "language";

    @DataType(DataType.Type.TEXT)
    String rating = "rating";

    @DataType(DataType.Type.TEXT)
    String ratingSource = "ratingSource";

    @DataType(DataType.Type.TEXT)
    String posterPath = "posterPath";

    @DataType(DataType.Type.TEXT)
    String backdropPath = "backdropPath";

}
