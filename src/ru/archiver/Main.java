package ru.archiver;

import ru.archiver.compression.Compressor;
import ru.archiver.compression.Squeezing;
import ru.archiver.compression.utils.ResultCompression;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {

    public static void main (String[] argc) {
        try {
            File file = new File("/Users/sjamie/Desktop/tmp/Compressor_old.java");

            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            Compressor compressor = new Compressor(bytes, 4096);
            compressor.run();

            Squeezing squeezing = new Squeezing(compressor.getResult(), bytes,4096);
            squeezing.run();
            ResultCompression result =  squeezing.getResult();


            File fileW = new File("text.txt");
            BufferedOutputStream bos = null;

            try {
                FileOutputStream fos = new FileOutputStream(fileW);
                bos = new BufferedOutputStream(fos);
                bos.write("text.txt\n".getBytes());
                bos.write(result.getLenght());
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