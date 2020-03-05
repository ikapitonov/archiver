package ru.archiver;

import ru.archiver.compression.Compressor;
import ru.archiver.compression.Squeezing;
import ru.archiver.compression.utils.Helpers;
import ru.archiver.compression.utils.ResultCompression;
import ru.archiver.config.Constants;
import ru.archiver.unpack.Unpack;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {

    public static void main (String[] argc) {

        if (argc.length > 0) {
            unpack();
            return ;
        }

        try {
            File file = new File("/Users/sjamie/Desktop/push_swap.zip");

            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            Compressor compressor = new Compressor(bytes, Constants.MAX_BUFFER);
            compressor.run();

            Squeezing squeezing = new Squeezing(compressor.getResult(), bytes, Constants.MAX_BUFFER);
            squeezing.run();
            ResultCompression result =  squeezing.getResult();


            File fileW = new File("text.txt");
            BufferedOutputStream bos = null;

            try {
                FileOutputStream fos = new FileOutputStream(fileW);
                bos = new BufferedOutputStream(fos);
                bos.write("text.txt\n".getBytes());
                for (int i = 0; i < 3; i++) {
                    bos.write(0);
                } bos.write(1);
                bos.write(Helpers.getBytesFromInt2(result.getLenght()));
                bos.write(result.getArray(), 0, result.getLenght());
            }finally {
                if(bos != null) {
                    try  {
                        //flush and close the BufferedOutputStream
                        bos.flush();
                        bos.close();
                    } catch(Exception e){}
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unpack () {
        try {
            File file = new File("/Users/matruman/Desktop/text.txt");

            FileInputStream fileInputStream = new FileInputStream(file);
            new Unpack(fileInputStream).run();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

// into main ()
//         try {
//             File file = new File("/Users/sjamie/Desktop/tmp/Compressor_old.java");
//
//             byte[] bytes = new byte[(int) file.length()];
//
//             FileInputStream fileInputStream = new FileInputStream(file);
//             fileInputStream.read(bytes);
//             fileInputStream.close();
//             Compressor compressor = new Compressor(bytes, 4096);
//             compressor.run();
//
//             Squeezing squeezing = new Squeezing(compressor.getResult(), bytes,4096);
//             squeezing.run();
//             ResultCompression result =  squeezing.getResult();
//
//
//             File fileW = new File("text.txt");
//             BufferedOutputStream bos = null;
//
//             try {
//                 FileOutputStream fos = new FileOutputStream(fileW);
//                 bos = new BufferedOutputStream(fos);
//                 bos.write(result.getArray(), 0, result.getLenght());
//             }finally {
//                 if(bos != null) {
//                     try  {
//                         //flush and close the BufferedOutputStream
//                         bos.flush();
//                         bos.close();
//                     } catch(Exception e){}
//                 }
//             }
//         }
//         catch (Exception e) {
//             e.printStackTrace();
//         }