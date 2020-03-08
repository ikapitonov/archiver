package ru.archiver.config;

public class Constants {
    public final static int COMPRESS_MIN = 7;
    public final static int COMPRESS_MAX = 20;
    public final static int MAX_BYTE = 127;
    public final static int BUFF_SIZE = 5000;
    public final static int LENGTH_ADDRESS = 2;

    public final static int FILE_MAX_LENGTH = 500001;

    public final static int MAX_THREAD = 4;

    public final static String PATH = "/Users/sjamie/Desktop/teeemp/tmp/"; // System.getProperty("user.dir");

    public final static String INVALIDE_READ = "Не удалось считать из файла";
    public final static String INVALIDE_WRITE = "Не удается записать значение в файл";
    public final static String INVALIDE_ARGS = "Здесь должно быть описание";
    public final static String INVALIDE_PACK = "Не удалось что-либо сжать. Работы программы завершена";
    public final static String OPEN_ERROR = "Ошибка при открытии файла";

    public final static String BAD_CHMOD_DIR = "Не могу получить список файлов из директории. Проверьте chmod";

    public final static String EMPTY_FILES_IN_DIR = "Пустая директория. Ошибка парсинга";
    public final static String NOT_FOUNT_FILES_IN_ARGS = "Не получилось обработать файлы из аргументов. Ошибка парсинга";

    public final static String FATAL_ERROR = "Критическая ошибка. Работа программы завершена";

    public final static String INVALID_ARCHIVE = "Invalid archive file";

    public final static String FILE_EXTENSION = "compress";
    public final static String FILE_NAME = "achieve";
}
