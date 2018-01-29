package com.pavandc.omdbsearchmovies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.fail;

/**
 * Created by pavandc on 2018-01-27.
 */

public class ResourceHelper {


public static String getResource(String path) {
    String content = "";
    try {
        InputStream inputStream = getInstrumentation().getTargetContext().getAssets().open(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String mLine;
        while ((mLine = bufferedReader.readLine()) != null) {
            content += mLine;
        }
        bufferedReader.close();
    } catch (IOException e ) {
        e.printStackTrace();
        content = "";
        fail("Please switch to espresso flavour or add the necessary mock file.");
    }

    return content;
}

}
