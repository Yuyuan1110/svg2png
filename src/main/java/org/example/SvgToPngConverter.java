package org.example;

import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SvgToPngConverter {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: SvgToPngConverter <directory>");
            System.exit(1);
        }

        File directory = new File(args[0]);
        if (!directory.isDirectory()) {
            System.err.println(args[0] + " is not a directory.");
            System.exit(1);
        }

        File[] svgFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".svg"));
        if (svgFiles == null || svgFiles.length == 0) {
            System.err.println("No SVG files found in " + args[0]);
            System.exit(1);
        }

        for (File svgFile : svgFiles) {
            convertSvgToPng(svgFile);
        }
    }

    private static void convertSvgToPng(File svgFile) {
        try {
            Transcoder transcoder = new PNGTranscoder();

            String pngFileName = svgFile.getName().replace(".svg", ".png");
            File pngFile = new File(svgFile.getParent(), pngFileName);

            FileInputStream svgInputStream = new FileInputStream(svgFile);
            FileOutputStream pngOutputStream = new FileOutputStream(pngFile);

            TranscoderInput transcoderInput = new TranscoderInput(svgInputStream);
            TranscoderOutput transcoderOutput = new TranscoderOutput(pngOutputStream);

            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, 975F);
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, 300F);
            transcoder.transcode(transcoderInput, transcoderOutput);


            System.out.println("Converted " + svgFile.getName() + " to " + pngFileName);
        } catch (IOException | TranscoderException e) {
            e.printStackTrace();
        }
    }
}