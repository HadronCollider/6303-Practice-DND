package Logic;


import java.lang.String;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class StrTransform {

    public static void transform(JButton button, String input){
        toFormattedString(input);
        setLabelsToButton(button, createLabels());
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

    public static void formatSwitch(char c){
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
                if (bufferWord.sizeMode == 0){
                    if (formattedWords.size() != 0) {
                        for (int j = formattedWords.size() - 1; j > 0; j--) {
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

    public static ArrayList <JLabel> createLabels(){
        ArrayList <JLabel> labels = new ArrayList<JLabel>();
        for (int i = 0; i < formattedWords.size(); i++) {
            Font font;
                switch (formattedWords.get(i).sizeMode){
                    case 1: font = new Font("APJapanesefont", Font.PLAIN, 12);break;
                    case 2: font = new Font("APJapanesefont", Font.PLAIN, 15);break;
                    case 3: font = new Font("APJapanesefont", Font.PLAIN, 26);break;
                    case 4: font = new Font("APJapanesefont", Font.PLAIN, 36);break;
                    case 5: font = new Font("APJapanesefont", Font.PLAIN, 39);break;
                    case 6: font = new Font("APJapanesefont", Font.PLAIN, 51);break;
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
    }

    public static void setLabelsToButton(JButton button, ArrayList <JLabel> labels ){
        GridBagLayout gb = new GridBagLayout();
        button.setLayout(gb);
        GridBagConstraints c = new GridBagConstraints();
        int i=0;
        int j=0;
        switch (labels.size()){
            case 1:
                c.anchor = GridBagConstraints.NORTH;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.gridx = i;
                c.gridy = j;
                c.insets = new Insets(0, 0, 0, 0);
                c.ipadx = 0;
                c.ipady = 0;
                c.weightx = 0.0;
                c.weighty = 0.0;
                gb.setConstraints(labels.get(0), c);
                button.add(labels.get(0));
                break;
            case 2:
                for (JLabel label: labels) {
                    c.anchor = GridBagConstraints.NORTH;
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridheight = 1;
                    c.gridwidth = 1;
                    c.gridx = 0;
                    c.gridy = j;
                    c.insets = new Insets(0, 0, 0, 0);
                    c.ipadx = 0;
                    c.ipady = 0;
                    c.weightx = 0.0;
                    c.weighty = 0.0;
                    gb.setConstraints(label, c);
                    button.add(label);
                    j++;
                }
                break;
            case 3:
                c.anchor = GridBagConstraints.NORTH;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridheight = 1;
                c.gridwidth = 2;
                c.gridx = 0;
                c.gridy = 0;
                c.insets = new Insets(0, 0, 0, 0);
                c.ipadx = 0;
                c.ipady = 0;
                c.weightx = 0.0;
                c.weighty = 0.0;
                gb.setConstraints(labels.get(0), c);
                button.add(labels.get(0));
                c.gridwidth = 1;
                c.gridx = 0;
                c.gridy = 1;
                gb.setConstraints(labels.get(1), c);
                button.add(labels.get(1));
                c.gridx = 1;
                c.gridy = 1;
                gb.setConstraints(labels.get(2), c);
                button.add(labels.get(2));
                break;
        }
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
            case 'е': color = new Color(71,74,81); break;
            default: color = new Color(0,0,0); break;
        }
    }

}