package com.example.couchbaselite;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLite;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

public class CouchbaseLiteDb {
    private static Database database = null;
    private static MutableDocument mutableDoc;
    private final static String DATABASE_NAME = "myDatabase";
    private final static String FIRST_DIGIT = "FIRST_DIGIT";
    private final static String SECOND_DIGIT = "SECOND_DIGIT";

    public static void createOrReadDatabase(Context context) {
        CouchbaseLite.init(context);
        DatabaseConfiguration config = new DatabaseConfiguration();
        mutableDoc = new MutableDocument();

        if (!Database.exists(DATABASE_NAME, context.getFilesDir())) {
            mutableDoc.setInt(FIRST_DIGIT, 0)
                    .setInt(SECOND_DIGIT, 0);
            try {
                database = new Database(DATABASE_NAME, config);
                database.save(mutableDoc);
            } catch (CouchbaseLiteException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                database = new Database(DATABASE_NAME, config);
            } catch (CouchbaseLiteException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static int[] getInitNumbers() {
        int[] initNumbers = new int[2];
        int first = 0;
        int second = 0;
        Query query = QueryBuilder.select(
                SelectResult.property(FIRST_DIGIT),
                SelectResult.property(SECOND_DIGIT)
        )
                .from(DataSource.database(database));

        try {
            ResultSet rs = query.execute();
            for (Result result : rs) {
                first = result.getInt(FIRST_DIGIT);
                second = result.getInt(SECOND_DIGIT);

            }
        } catch (CouchbaseLiteException e) {
            Log.e("Sample", e.getLocalizedMessage());
        }
        initNumbers[0] = first;
        initNumbers[1] = second;
        return initNumbers;
    }

    public static void saveData(int first, int second) throws CouchbaseLiteException {
        mutableDoc.setInt(FIRST_DIGIT, first)
                .setInt(SECOND_DIGIT, second);

        database.save(mutableDoc);

    }


}
