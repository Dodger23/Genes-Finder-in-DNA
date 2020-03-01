import edu.duke.*; 
import java.util.* ;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
public class main
{
    
    private static void writeUsingFileWriter(String data) {
        File file = new File("genes.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int findStopCodon(String dna , int startIndex , String stopCodon)
    {
        int currentIndex = dna.indexOf(stopCodon , startIndex+3);
        while(currentIndex!=-1)
        {
            int diff = currentIndex - startIndex ; 
            if(diff%3==0)
            {
                return currentIndex ;
            }
            else
            {
                currentIndex = dna.indexOf(stopCodon , currentIndex +1 ) ;
            }
        }
        
        return dna.length() ;
    }
    public String findGene(String DNA , int where)
    {
       
        int startIndex = DNA.indexOf("ATG" , where) ; 
        if(startIndex == -1)
        {
            return "" ;
        } 
        int taaIndex = findStopCodon(DNA , startIndex , "TAA" ) ;
        int tagIndex = findStopCodon(DNA , startIndex , "TAG" ) ;
        int tgaIndex = findStopCodon(DNA , startIndex , "TGA" ) ;
        int minIndex = Math.min(taaIndex , Math.min(tgaIndex , tagIndex) ) ;
        if(minIndex == DNA.length())
            return "" ;
           return DNA.substring(startIndex, minIndex+3 ) ;
    }
    
    public void AllGenes(String dna)
    {
        int genesCounter = 1 , startIndex = 0  ; 
        String currentGene = "" ;
        while(true)
        {
            currentGene = findGene(dna,startIndex) ;
            if(currentGene.isEmpty())
                break ; 
            System.out.print(currentGene);
            writeUsingFileWriter("Gene " +genesCounter+" : "+  currentGene) ;
            genesCounter++;
            startIndex = dna.indexOf(currentGene, startIndex ) + currentGene.length();

        }
        
    }
    
    public void test()
    {
        FileResource fr = new FileResource("dna.txt");
        String dna = fr.asString(); 
        System.out.println(dna);
        AllGenes(dna) ;
    }
    
    
}
