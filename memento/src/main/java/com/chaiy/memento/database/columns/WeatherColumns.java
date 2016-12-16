package com.chaiy.memento.database.columns;

import net.simonvt.schematic.annotation.DataType;

/**
 * Created by Chaiy on 12/4/2016.
 */

public interface WeatherColumns {

    @DataType(DataType.Type.TEXT)
    String summary = "summary";

    @DataType(DataType.Type.TEXT)
    String temperatureMax = "temperatureMax";

    @DataType(DataType.Type.TEXT)
    String icon = "icon";

    @DataType(DataType.Type.TEXT)
    String temperatureMin = "temperatureMin";

    @DataType(DataType.Type.TEXT)
    String precipType = "precipType";

    @DataType(DataType.Type.TEXT)
    String humidity = "humidity";

    @DataType(DataType.Type.TEXT)
    String windSpeed = "windSpeed";


}
