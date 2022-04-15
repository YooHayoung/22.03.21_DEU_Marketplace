package com.deu.marketplace.utils;

public class FileUtils {

    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String CATEGORY_PREFIX = "/";
    private static final String TIME_SEPARATOR = "_";
    private static final String MEMBER_SEPARATOR = "_";
    private static final String ITEM_SEPARATOR = "_";

    public static String buildFileName(String category, String originalFileName, Long itemId, Long memberId) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return category + CATEGORY_PREFIX + itemId + ITEM_SEPARATOR + memberId + MEMBER_SEPARATOR + fileName + TIME_SEPARATOR + now + fileExtension;
    }
}
