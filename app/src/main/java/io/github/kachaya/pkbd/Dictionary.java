package io.github.kachaya.pkbd;

import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Dictionary {
    private final String TAG = "Dictionary";

    private static final String MAP_NAME = "dic";

    private final Context mContext;
    private final String mFilesDirPath;

    private DB mMainDicDB;
    private DB mUserDicDB;
    private HTreeMap<String, String> mMainDicMap;
    private HTreeMap<String, String> mUserDicMap;

    public Dictionary(Context context) {
        mContext = context;
        mFilesDirPath = mContext.getFilesDir().getAbsolutePath();
        initMainDic(mFilesDirPath + "/main_dic.db");
        initUserDic(mFilesDirPath + "/user_dic.db");
    }

    private void initUserDic(String fileName) {
        File f = new File(fileName);
        mUserDicDB = DBMaker.newFileDB(f).closeOnJvmShutdown().make();
        mUserDicMap = mUserDicDB.getHashMap(MAP_NAME);
    }

    private void initMainDic(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            try {
                InputStream is = mContext.getAssets().open("main_dic.zip");
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
                ZipEntry ze;
                byte[] buf = new byte[16 * 1024];
                int size;
                while ((ze = zis.getNextEntry()) != null) {
                    String fn = mFilesDirPath + "/" + ze.getName();
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fn));
                    while ((size = zis.read(buf, 0, buf.length)) > 0) {
                        bos.write(buf, 0, size);
                    }
                    bos.flush();
                    bos.close();
                }
                zis.closeEntry();
                zis.close();
            } catch (IOException e) {
                Log.e("initMainDic", "IOException");
            }
            f = new File(fileName);
        }
        mMainDicDB = DBMaker.newFileDB(f).closeOnJvmShutdown().readOnly().make();
        mMainDicMap = mMainDicDB.getHashMap(MAP_NAME);
    }

    public void commit() {
        mUserDicDB.commit();
    }

    public void add(@NonNull CharSequence keyword, String kanji) {
        String key = keyword.toString();
        String oldVal;
        StringBuilder sb = new StringBuilder(kanji);      // 今回のものを先頭にする
        oldVal = mUserDicMap.get(key);
        if (oldVal != null) {
            String[] ss = oldVal.split("\t");
            // 重複するとしても一つだけなのでループで処理
            for (String s : ss) {
                if (!kanji.equals(s)) {
                    sb.append("\t");
                    sb.append(s);
                }
            }
        }
        mUserDicMap.put(key, sb.toString());
    }

    // 候補検索
    public String[] search(@NonNull CharSequence keyword) {
        String key = keyword.toString();
        String userValue = mUserDicMap.get(key);
        String mainValue = mMainDicMap.get(key);
        if (userValue == null) {
            if (mainValue == null) {
                return null;
            } else {
                return mainValue.split("\t");
            }
        } else {
            if (mainValue == null) {
                return userValue.split("\t");
            } else {
                Set<String> lhs = new LinkedHashSet<>();
                lhs.addAll(Arrays.asList(userValue.split("\t")));
                lhs.addAll(Arrays.asList(mainValue.split("\t")));
                return lhs.toArray(new String[0]);
            }
        }
    }
}
