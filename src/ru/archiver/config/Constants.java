package ru.archiver.config;

public class Constants {
    public final static int COMPRESS_MIN = 7;
    public final static int COMPRESS_MAX = 20;
    public final static int MAX_BYTE = 127;
    public final static int BUFF_SIZE = 4096;
    public final static int LENGTH_ADDRESS = 2;

    public final static int FILE_MAX_LENGTH = 100001;

    public final static int MAX_THREAD = 4;

    public final static String PATH = "/Users/matruman/Desktop/arh/test.unpack"; // System.getProperty("user.dir"); //"/Users/sjamie/Desktop/tessst/";

    public final static String INVALIDE_READ = "INVALIDE_READ_FILE";
    public final static String INVALIDE_ARGS = "INVALIDE_ARGS";
    public final static String INVALIDE_UNPACKING = "Invalide pack";
    public final static String INVALID_ARCHIVE = "Invalid archive file";
    public final static String EMPTY_FILES_IN_DIR = "Empty dir";
    public final static String NOT_FOUNT_FILES_IN_ARGS = "NOT_FOUNT_FILES_IN_ARGS";

    public final static String OPEN_ERROR = "Cannot open the file";

    public final static String FILE_EXTENSION = "compress";
}
