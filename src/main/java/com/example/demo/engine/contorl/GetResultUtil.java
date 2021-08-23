package com.example.demo.engine.contorl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.engine.util.AttributeCheck;
import com.example.demo.engine.util.CopyUtil;
import com.example.demo.engine.util.TxtUtil;
import com.example.demo.engine.entity.Face;

public class GetResultUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(GetResultUtil.class);

    /**
     * Get Retrieve result json
     *
     * @author eGroupAI Team
     *
     *
     * @param startIndex
     * @return
     */
    public List<Face> allResult(String jsonFolderPath, String jsonName, int startIndex, boolean isDynamicJson, long waiteJsonMs) {
        // init func
        final Gson gson = new Gson();
        final CopyUtil copyUtil = new CopyUtil();
        final AttributeCheck attributeCheck = new AttributeCheck();

        // init variable
        final Type faceListType = new TypeToken<ArrayList<Face>>() {}.getType();
        List<Face> faceList = new ArrayList<Face>();

        // Get retrieve result
        final File sourceJson = new File(jsonFolderPath.toString() + "/" + jsonName + ".json");
        final StringBuilder jsonFileName = new StringBuilder(jsonFolderPath + "/" + jsonName + "_copy.json");
        final File destJson = new File(jsonFileName.toString());

        try {
            Thread.sleep(waiteJsonMs);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (sourceJson.exists() && sourceJson.length() > 0) {
            // init func
            final TxtUtil txtUtil = new TxtUtil();
            // init variable
            String jsonContent;
            jsonContent = txtUtil.read_content(jsonFileName.toString());

            try {
                copyUtil.copyFile(sourceJson, destJson);
            } catch (IOException e) {
                LOGGER.error(gson.toJson(e.getMessage()));
            }

            // If has data
            if (attributeCheck.stringsNotNull(jsonContent)) {
                // Get last one object
                if (isDynamicJson) {
                    int endIndex = jsonContent.lastIndexOf("}\n\t,");
                    if (endIndex == -1) {
                        endIndex = jsonContent.lastIndexOf("}\n]");
                    }
                    String json;
                    // Reorganization json
                    if (endIndex != -1 && startIndex != endIndex && startIndex < endIndex) {
                        if (startIndex > 0) {
                            json = "[" + jsonContent.toString().substring(startIndex + 2, endIndex) + "}]";
                        } else {
                            json = jsonContent.toString().substring(startIndex, endIndex) + "}]";
                        }
                        if (attributeCheck.stringsNotNull(json)) {
                            faceList = gson.fromJson(json, faceListType);
                            faceList.get(faceList.size() - 1).setEndIndex(endIndex + 2);
                        }
                    }
                } else {
                    // If has data
                    if (attributeCheck.stringsNotNull(jsonContent.toString())) {
                        faceList = gson.fromJson(jsonContent.toString(), faceListType);
                    }
                }
            }
        }

        return faceList;
    }

    /**
     * Get Retrieve result json
     *
     * @author eGroupAI Team
     *
     *
     * @return
     */
    public List<Face> cacheResult(String jsonFolderPath, String jsonName) {
        // init func
        final AttributeCheck attributeCheck = new AttributeCheck();
        // init variable
        List<Face> faceList = null;

        if (attributeCheck.stringsNotNull(jsonFolderPath, jsonName)) {
            // init func
            final Gson gson = new Gson();
            final CopyUtil copyUtil = new CopyUtil();

            // init variable
            final Type faceListType = new TypeToken<ArrayList<Face>>() {}.getType();

            // Get retrieve result
            final File sourceJson = new File(jsonFolderPath + "\\" + jsonName + ".json");
            final StringBuilder jsonFileName = new StringBuilder(jsonFolderPath + "\\" + jsonName + "_copy.json");
            final File destJson = new File(jsonFileName.toString());
            if (sourceJson.exists() && sourceJson.length() != destJson.length()) {
                // init func
                final TxtUtil txtUtil = new TxtUtil();
                // init variable
                String jsonContent;
                try {
                    copyUtil.copyFile(sourceJson, destJson);
                } catch (IOException e) {
                    LOGGER.error(gson.toJson(e.getMessage()));
                }
                jsonContent = txtUtil.read_content(jsonFileName.toString());

                // If has data
                if (attributeCheck.stringsNotNull(jsonContent)) {
                    // Get last one object
                    int endIndex = jsonContent.lastIndexOf("}\n]");
                    if (endIndex == -1) {
                        endIndex = jsonContent.lastIndexOf("}\n\t,");
                    }
                    if (endIndex > 0) {
                        final String json = jsonContent.substring(0, endIndex) + "}]";
                        faceList = gson.fromJson(json, faceListType);
                    }
                }
            }
        }
        return faceList;
    }

    /**
     * Get Retrieve result json
     *
     * @author eGroupAI Team
     *
     * @param jsonPath
     *
     * @return
     */
    public List<Face> serverPhotoResult(String jsonPath, String jsonName, Boolean deleteJson) {
        // init func
        final Gson gson = new Gson();

        // init variable
        final Type faceListType = new TypeToken<ArrayList<Face>>() {}.getType();
        List<Face> faceList = new ArrayList<Face>();

        // Get retrieve result
        final String sourceJson = jsonPath.toString() + "\\" + jsonName + ".json";
        final File sourceJson_file = new File(sourceJson);
        if (sourceJson_file.exists()) {
            // init func
            final TxtUtil txtUtil = new TxtUtil();
            final AttributeCheck attributeCheck = new AttributeCheck();
            // init variable
            final Path sourceJson_filePath = Paths.get(sourceJson);
            final StringBuilder jsonFileName = new StringBuilder(jsonPath + "\\" + jsonName + ".json");

            String jsonContent = txtUtil.read_content(jsonFileName.toString());

            if (attributeCheck.stringsNotNull(jsonContent)) {
                faceList = gson.fromJson(jsonContent, faceListType);
            }
            if (deleteJson) {
                try {
                    Files.delete(sourceJson_filePath);
                } catch (IOException e) {
                    LOGGER.error(gson.toJson(e.getMessage()));
                }
            }
        }
        return faceList;
    }
}
