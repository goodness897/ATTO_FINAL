package com.atto.developers.atto.networkdata.dbdata;

import android.provider.BaseColumns;

/**
 * Created by Tacademy on 2016-09-06.
 */
public class CategoryKeywordData  {

    public interface Cateory extends BaseColumns{
        public static final String TABLE = "category";
        public static final String CULUMN_NMAE="name";
    }

    public interface Keyword extends BaseColumns{
        public static final String TABLE = "keyword";
        public static final String CULUMN_NMAE="name";
    }
}
