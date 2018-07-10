package Logic;


import UI.GameWindow.Square;

import java.lang.String;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class StrTransform {

    private static int height;

    public static void transform(Square button, String input, int squareHeight) {
        System.setProperty("file.encoding", "UTF-8");
        height = (int)(squareHeight * 0.8);
        toFormattedString(input);
        createButtons(button);
        formattedWords.clear();
    }

    public static String toNormalString(String inputString){
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

    public static ArrayList <word> formattedWords = new ArrayList<word>();
    public static word bufferWord;

    public static void formatSwitch(char c) {
        switch (c) {
            case '1': bufferWord.sizeMode = 1;break;
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

    public static String toFormattedString(String inputString){
        inputString += ' ';
        char[] input = inputString.toCharArray();
        String output="";
        char lastPushedSymb =' ';
        String text = "";
        int count = 0;
        for (int i=0; i < inputString.length(); i++) {
            if (lastPushedSymb == '\\'){
                if (bufferWord == null || bufferWord.isUsed) {
                    bufferWord = new word();
                    bufferWord.setColor('ж');
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
                if (bufferWord.sizeMode == 0) {
                    if (formattedWords.size() != 0) {
                        for (int j = formattedWords.size()-1; j >= 0; j--) {
                            if (formattedWords.get(j).sizeMode != 0) {
                                bufferWord.sizeMode = formattedWords.get(j).sizeMode;
                                break;
                            }
                        }
                    }
                    else bufferWord.sizeMode = 6;
                }
                bufferWord.text = text;
                text = "" ;
                if (bufferWord.sizeMode != 0 && bufferWord.color != null && !bufferWord.text.equals("")) {
                    formattedWords.add(bufferWord);
                    bufferWord.isUsed = true;
                }
                lastPushedSymb = ' ';
                continue;
            }
            if (input[i] == '\\'){
                if (i > 0 && formattedWords.size() == 0){
                    for (int j = 0; j < i; j++) {
                        text += input[j];
                    }
                    word single = new word();
                    single.text = text;
                    single.sizeMode = 6;
                    single.color = new Color(0,0,0);
                    formattedWords.add(single);
                    text = "";
                    single.isUsed = true;
                }
                lastPushedSymb = input[i];
                continue;
            }
            count++;
        }
        if (count == inputString.length() && formattedWords.size() == 0){
            word single = new word();
            single.text = inputString;
            single.sizeMode = 6;
            single.color = new Color(0,0,0);
            formattedWords.add(single);
        }
        return output;
    }

    private static void createButtons(Square button){
        int horizontal = 0;
        int vertical = 0;
       // ArrayList <JLabel> labels = new ArrayList<JLabel>();
        GridBagLayout gb = new GridBagLayout();
        button.setLayout(gb);
        GridBagConstraints c = new GridBagConstraints();
        for (int i = 0; i < formattedWords.size(); i++) {
            Font font;
            switch (formattedWords.get(i).sizeMode){
                case 1: font = new Font("MingLiU", Font.PLAIN, (int) ((double)1/6 * height));break;
                case 2: font = new Font("MingLiU", Font.PLAIN, (int) ((double)2/6 * height));break;
                case 3: font = new Font("MingLiU", Font.PLAIN, (int) ((double)3/6 * height));break;
                case 4: font = new Font("MingLiU", Font.PLAIN, (int) ((double)4/6 * height));break;
                case 5: font = new Font("MingLiU", Font.PLAIN, (int) ((double)5/6 * height));break;
                case 6: font = new Font("MingLiU", Font.PLAIN, height);break;
                default: font = new Font("MingLiU", Font.PLAIN, height);break;
            }
            JLabel label = new JLabel(formattedWords.get(i).text);
            label.setVerticalAlignment(SwingConstants.BOTTOM);
            label.setFont(font);
            if (formattedWords.get(i).color == null)
                label.setForeground(new Color(0,0,0));
            else
                label.setForeground(formattedWords.get(i).color);
            //labels.add(label);
           // c.anchor = GridBagConstraints.CENTER;
            if (formattedWords.get(i).hasLineBreak){
                vertical++;
                horizontal = 0;
                //label.setHorizontalAlignment(SwingConstants.LEFT);

            }
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.NONE;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = horizontal;
            c.gridy = vertical;
            c.insets = new Insets(0, 0, 0, 0);
            c.ipadx = 0;
            c.ipady = 0;
            c.weightx = 0.0;
            c.weighty = 1;
            gb.setConstraints(label, c);
            button.add(label);
            horizontal++;
        }
    }

    /*public static ArrayList <JLabel> createLabels(){
        ArrayList <JLabel> labels = new ArrayList<JLabel>();
        for (int i = 0; i < formattedWords.size(); i++) {
            Font font;
                switch (formattedWords.get(i).sizeMode){
                    case 1: font = new Font("APJapanesefont", Font.PLAIN, (int) ((double)1/6 * height));break;
                    case 2: font = new Font("APJapanesefont", Font.PLAIN, (int) ((double)2/6 * height));break;
                    case 3: font = new Font("APJapanesefont", Font.PLAIN, (int) ((double)3/6 * height));break;
                    case 4: font = new Font("APJapanesefont", Font.PLAIN, (int) ((double)4/6 * height));break;
                    case 5: font = new Font("APJapanesefont", Font.PLAIN, (int) ((double)5/6 * height));break;
                    case 6: font = new Font("APJapanesefont", Font.PLAIN, height);break;
                    default: font = new Font("APJapanesefont", Font.PLAIN, 36);break;
                }
                JLabel label = new JLabel(formattedWords.get(i).text);
                label.setFont(font);
                if (formattedWords.get(i).color == null)
                    label.setForeground(new Color(0,0,0));
                else
                    label.setForeground(formattedWords.get(i).color);
                labels.add(label);
        }
        return labels;
    }*/

   /* public static void setLabelsToButton(JButton button, ArrayList <JLabel> labels ){
        GridBagLayout gb = new GridBagLayout();
        button.setLayout(gb);
        GridBagConstraints c = new GridBagConstraints();
        int i=0;
        int j=0;

    }*/

    public static boolean isFormatCorrect(String inputString){
        inputString += ' ';
        char[] input = inputString.toCharArray();
        char lastPushedSymb =' ';
        for (int i=0; i < inputString.length(); i++) {
            if (lastPushedSymb == '\\'){
                switch (input[i]){
                    case '1': case '2': case '3': case '4': case '5': case '6':
                    case 'р': case 'о': case 'з': case 'г': case 'с': case 'ф': case 'ч': case 'е':
                    case '\\':
                        lastPushedSymb = ' ';
                        continue;
                    default:
                         return false;
                }

            }
            if (input[i] == '\\'){
                lastPushedSymb = input[i];
            }
        }
        return true;
    }
}

class word {
    int sizeMode = 0;
    boolean hasLineBreak = false;
    boolean isUsed = false;
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
            case 'е': color = new Color(138,138,138); break;
            default: color = new Color(0,0,0); break;
        }
    }

}