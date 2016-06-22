package ocr

import com.asprise.ocr.Ocr
import java.io.File

object SampleAsprise {
  
  def main(args: Array[String]) {
    Ocr.setUp()// one time setup
    val ocr: Ocr = new Ocr() // create a new OCR engine
    ocr.startEngine("spa", Ocr.SPEED_SLOW) // English
    val s = ocr.recognize(Array(new File("/tmp/49239.jpg")), Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
    System.out.println("Result: " + s)
  }
  
}