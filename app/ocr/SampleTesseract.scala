package ocr

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.bridj.BridJ;
import org.bridj.Pointer;

import de.vorb.tesseract.bridj.Tesseract;
import de.vorb.tesseract.bridj.Tesseract.TessBaseAPI;
import de.vorb.tesseract.bridj.OCREngineMode;
import de.vorb.tesseract.bridj.PageSegMode;

object SampleTesseract {
  
  def main(args: Array[String]) {
 
    // provide the native library file
    BridJ.setNativeLibraryFile("tesseract", new File("libtesseract303.dll"))

    val start = System.currentTimeMillis()
    // create a reference to an execution handle
    val handle: Pointer<TessBaseAPI> = Tesseract.TessBaseAPICreate()

    // init Tesseract with data path, language and OCR engine mode
    Tesseract.TessBaseAPIInit2(handle,
        Pointer.pointerToCString("E:\\Masterarbeit\\Ressourcen\\tessdata"),
        Pointer.pointerToCString("deu-frak"), OCREngineMode.DEFAULT)

    // set page segmentation mode
    Tesseract.TessBaseAPISetPageSegMode(handle, PageSegMode.AUTO)

    // read the image into memory
    val inputImage: BufferedImage = ImageIO.read(new File("input.png"))

    // get the image data
    val imageBuffer: DataBuffer = inputImage.getRaster().getDataBuffer()
    final byte[] imageData = ((DataBufferByte) imageBuffer).getData()

    // image properties
    val width = inputImage.getWidth(), height = inputImage.getHeight()
    val bitsPerPixel = inputImage.getColorModel().getPixelSize()
    val bytesPerPixel = bitsPerPixel / 8
    val bytesPerLine = (int) Math.ceil(width * bitsPerPixel / 8.0)

    // set the image
    Tesseract.TessBaseAPISetImage(handle,
        Pointer.pointerToBytes(ByteBuffer.wrap(imageData)), width, height,
        bytesPerPixel, bytesPerLine)

    // get the text result
    val txt = Tesseract.TessBaseAPIGetUTF8Text(handle).getCString()

    // print the result
    System.out.println(txt)

    // calculate the time
    System.out.println("time: " + (System.currentTimeMillis() - start) + "ms")

    // delete handle
    Tesseract.TessBaseAPIDelete(handle)
      
  }
}