import java.lang.String;
import java.awt.*;
import javax.swing.*;

public class StrTransform {
    /*public static void main(String[] args) {
        StrTransform example = new StrTransform();
        String inputString = "\\г\\4КЭЦУ\\\\\\2\\ркимэру,\\чкимеру";
        System.out.println(inputString);
        System.out.println(example.toNormalString(inputString));
        System.out.println(example.toFormattedString(inputString));
    }*/
    public String toNormalString(String inputString){
        char[] input = inputString.toCharArray();
        String output="";
        char lastPushedSymb = ' ';
        for (int i=0; i < inputString.length(); i++) {
            if (lastPushedSymb == '\\'){
                switch (input[i]) {
                    case '1': case '2': case '3': case '4': case '5': case '6':
                    case 'р': case 'о': case 'з': case 'г': case 'с': case 'ф': case 'ч': case 'е':
                    case '\\':
                       // if (i != 1 && input[i+1] != '\\')
                            //output += '';
                        lastPushedSymb = ' ';
                        continue;
                }
            }
            if (input[i] == '\\'){
                lastPushedSymb = input[i];
                continue;
            }
            output += input[i];
        }
        return output;
    }

    public int count=0;
    public word[] formattedWords = new word[10];
    public word bufferWord;

    public void formatSwitch(char c){
        switch (c){
            case '1': bufferWord.sizeMode = 1; break;
            case '2': bufferWord.sizeMode = 2;break;
            case '3': bufferWord.sizeMode = 3;break;
            case '4': bufferWord.sizeMode = 4;break;
            case '5': bufferWord.sizeMode = 5;break;
            case '6': bufferWord.sizeMode = 6;break;
            case 'р': case 'о': case 'з': case 'г': case 'с': case 'ф': case 'ч': case 'е':
                bufferWord.setColor(c); break;
            case '\\': bufferWord.hasLineBreak = true; break;
        }
    }

    public String toFormattedString(String inputString){
        inputString += ' ';
        char[] input = inputString.toCharArray();
        String output="";
        char lastPushedSymb =' ';
        String text = "";
        for (int i=0; i < inputString.length(); i++) {
            if (lastPushedSymb == '\\'){
                if (bufferWord == null || bufferWord.text.equals("dododo")) {
                    bufferWord = new word();
                }
                formatSwitch(input[i]);

                for (int j = i+1; input[j] != '\\' && j != input.length-1 ;j++){
                    i++;
                    text+=input[j];
                }
                if (text.equals("")){
                    i++;
                    continue;
                }
                bufferWord.text = text;
                text = "" ;
                System.out.println(bufferWord.text);
                System.out.println(bufferWord.sizeMode);
                System.out.println(bufferWord.hasLineBreak);
                if (bufferWord.sizeMode != 0 || bufferWord.color != null || !bufferWord.text.equals("")) {
                    System.out.println("new Word coming");
                    formattedWords[count] = bufferWord;
                    count++;
                    bufferWord.text = "dododo";
                }
                lastPushedSymb = ' ';
                continue;
            }
            if (input[i] == '\\'){
                lastPushedSymb = input[i];
                continue;
            }
        }
        return output;
    }

    public void buttonTransform(JButton button, String input){
        toFormattedString(input);
        //button.setLayout();
        for (int i = 0; i < formattedWords.length; i++) {
            Font font = new Font("APJapanesefont", Font.PLAIN, 2*bufferWord.sizeMode+20);
            JLabel label = new JLabel(formattedWords[i].text);
            label.setForeground(formattedWords[i].color);
        }
    }
}

class word {
    int sizeMode = 6;
    boolean hasLineBreak = false;
    Color color;
    String text = "";
    public void setColor (char symb){
        switch (symb) {
            case 'р': color = new Color(252,15,192); break;
            case 'о': color = new Color(255,104,0); break;
            case 'з': color = new Color(0,255,0); break;
            case 'г': color = new Color(0,184,217); break;
            case 'с': color = new Color(0,0,255); break;
            case 'ф': color = new Color(148,0,211); break;
            case 'е': color = new Color(71,74,81); break;
            default: color = new Color(0,0,0); break;
        }
    }

}