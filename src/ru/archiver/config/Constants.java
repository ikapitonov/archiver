package ru.archiver.config;

public class Constants {
    public final static int COMPRESS_MIN = 7;
    public final static int COMPRESS_MAX = 20;
    public final static int MAX_BYTE = 127;
    public final static int BUFF_SIZE = 4096;
    public final static int LENGTH_ADDRESS = 2;
    public final static int MAX_BUFFER = 4096;

    public final static String PATH = System.getProperty("user.dir");

    public final static String INVALIDE_PACKING = "Invalide pack";
    public final static String INVALIDE_UNPACKING = "Invalide pack";
    public final static String EMPTY_FILES_IN_DIR = "Empty dir";
    public final static String NOT_FOUNT_FILES_IN_ARGS = "NOT_FOUNT_FILES_IN_ARGS";
}
