import javax.swing.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.math.*;
/*
Square Root s
nth Root n
log() l
ln() m
sin() a
cos() b
tan() c
asin() d
acos() f
atan() g
sinh() h
cosh() i
tanh() j
exp() e
10^n t
Factorial !
*/

 class Conversion {
    private String infix; // The infix expression to be converted
    private Deque<Character> stack = new ArrayDeque<Character>();
    private List<String> postfix = new ArrayList<String>(); // To hold the postfix expression

    public Conversion(String expression)
    {
        infix = expression;
        convertExpression();
    }

    /* The approach is basically, if it's a number, push it to postfix list
     * else if it's an operator, push it to stack
     */
    private void convertExpression()
    {
        // Temporary string to hold the number
        StringBuilder temp = new StringBuilder();

        for(int i = 0; i != infix.length(); ++i)
        {           
            if(Character.isDigit(infix.charAt(i)))
            {
                /* If we encounter a digit, read all digit next to it and append to temp
                 * until we encounter an operator.
                 */
                temp.append(infix.charAt(i));

                while((i+1) != infix.length() && (Character.isDigit(infix.charAt(i+1)) 
                                                  || infix.charAt(i+1) == '.'))
                {
                    temp.append(infix.charAt(++i));
                }


                /* If the loop ends it means the next token is an operator or end of expression
                 * so we put temp into the postfix list and clear temp for next use
                 */
                postfix.add(temp.toString());
                temp.delete(0, temp.length());
            }
            // Getting here means the token is an operator
            else
                inputToStack(infix.charAt(i));
        }
        clearStack();
    }


    private void inputToStack(char input)
    {
        if(stack.isEmpty() || input == '(')
            stack.addLast(input);
        else 
        {
            if(input == ')')
            {
                while(!stack.getLast().equals('('))
                {
                    postfix.add(stack.removeLast().toString());
                }
                stack.removeLast();
            }
            else 
            {
                if(stack.getLast().equals('('))
                    stack.addLast(input);
                else
                {
                    while(!stack.isEmpty() && !stack.getLast().equals('(') && 
                            getPrecedence(input) <= getPrecedence(stack.getLast()))
                    {
                        postfix.add(stack.removeLast().toString());
                    }
                    stack.addLast(input);
                }
            }
        }
    }


    private int getPrecedence(char op)
    {
         if (op == '+' || op == '-')
                return 1;
         else if (op == '*' || op == '/')
                return 2;
         else if (op=='%')
                return 3;
         else if (op == '^' || op == 's' || op == 'n')
                return 4;
         else if (op == '!' || op == 'e' || op == 'l' || op == 'm' || op == 't')
                return 5;
         else if (op == 'a' ||op == 'b' || op == 'c' || op == 'd' || op == 'f' || op == 'g' || op == 'h' || op == 'i' || op == 'j')
                return 6;
         else return 0;
    }


    private void clearStack()
    {
        while(!stack.isEmpty())
        {
            postfix.add(stack.removeLast().toString());
        }
    }


    public void printExpression()
    {
        for(String str : postfix)
        {
            System.out.print(str + ' ');
        }
    }


    public List<String> getPostfixAsList()
    {
        return postfix;
    }
}
 class Evaluation {
    private List<String> expression = new ArrayList<String>();
    private Deque<Double> stack = new ArrayDeque<Double>();

    public Evaluation(List<String> postfix) {expression = postfix;}


    public BigDecimal result()
    {
        for(int i = 0; i != expression.size(); ++i)
        {
            // Determine if current element is digit or not
            if(Character.isDigit(expression.get(i).charAt(0)))
            {
                stack.addLast(Double.parseDouble(expression.get(i)));
            }
            else 
            {
                double tempResult = 0;
                double temp;
                Expression exp = new Expression();
                switch(expression.get(i))
                {
                    case "+": temp = stack.removeLast();
                              tempResult = stack.removeLast() + temp;
                              break;

                    case "-": temp = stack.removeLast();
                              tempResult = stack.removeLast() - temp;
                              break;

                    case "*": temp = stack.removeLast();
                              tempResult = stack.removeLast() * temp;
                              break;

                    case "/": temp = stack.removeLast();
                              tempResult = stack.removeLast() / temp;
                              break;
                    case "%": temp = stack.removeLast();
                              tempResult = stack.removeLast() % temp;
                              break;
                    case "^": temp = stack.removeLast();
                              tempResult = Math.pow(stack.removeLast(), temp);
                              break;
                    case "!": tempResult = exp.fact(stack.removeLast());
                              break;
                    case "s": tempResult = Math.sqrt(stack.removeLast());
                              break;
                    case "n": temp = stack.removeLast();
                              tempResult = exp.nthRoot(temp, stack.removeLast());
                              break;
                    case "e": tempResult = Math.exp(stack.removeLast());
                              break;
                    case "l": tempResult = Math.log10(stack.removeLast());
                              break;
                    //case "m": tempResult = Math.log(2.7183)(stack.removeLast());
                    case "t": temp = stack.removeLast();
                              tempResult = temp*(Math.pow(10, stack.removeLast()));
                              break;
                    case "a": tempResult = Math.sin(stack.removeLast());
                              break;
                    case "b": tempResult = Math.cos(stack.removeLast());
                              break;
                    case "c": tempResult = Math.tan(stack.removeLast());
                              break;
                    case "d": tempResult = Math.asin(stack.removeLast());
                              break;
                    case "f": tempResult = Math.acos(stack.removeLast());
                              break;
                    case "g": tempResult = Math.atan(stack.removeLast());
                              break;
                    case "h": tempResult = Math.sinh(stack.removeLast());
                              break;
                    case "i": tempResult = Math.cosh(stack.removeLast());
                              break;
                    case "j": tempResult = Math.tanh(stack.removeLast());
                              break;
                }
                stack.addLast(tempResult);
            }
        }
        return new BigDecimal(stack.removeLast()).setScale(3, BigDecimal.ROUND_HALF_UP);
    }
}
class Expression{
    String exp="";
    String prev="";
    public void addChar(String str){
        if (str!=null)
            exp+=str;
    }
    public double fact(double x){
        int f=1;
        while(x!=1){
            f*=x;
            x--;
        }
        return f;
    }
    public double nthRoot(double x, double y){
        return Math.pow(x, 1.0/y);
    }
    public void setString(String s){
        exp=s;
    }
    public void setNull(){
        exp="";
    }
    public String toString(){
        return exp;
    }
    public BigDecimal answer;
    public void setAnswer(BigDecimal a){
        answer = a;
    }
    public BigDecimal getAnswer(){
        return answer;
    }
}
public class Calaculator extends javax.swing.JFrame {
    Expression str = new Expression();
    //Conversion con = new Conversion()
    //Evaluation ev = new Evaluation();
    public Calaculator() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jFrame1 = new javax.swing.JFrame();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton37 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        toggle = new javax.swing.JToggleButton();
        jLabel6 = new javax.swing.JLabel();
        jButton38 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton6.setText("+");

        jButton34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton34.setText("tanhx");

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton11.setText("cosx");

        jButton29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton29.setText("sinx");

        jButton30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton30.setText("tanx");

        jButton31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton31.setText("sinhx");

        jButton32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton32.setText("coshx");

        jButton33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton33.setText("tanhx");

        jButton35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton35.setText("asinx");

        jButton36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton36.setText("acosx");

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Calculator");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton1.setText("1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton16.setText("3");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton17.setText("2");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton18.setText("6");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton19.setText("5");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton20.setText("4");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton21.setText(".");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton22.setText("8");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton23.setText("7");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jLabel1.setText(" Digits");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("|x|");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton3.setText("*");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton4.setText("/");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton5.setText("-");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setText("-x");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton8.setText("+");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton9.setText("%");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel2.setText("Basic Arithmetic");

        jButton25.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton25.setText("0");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton26.setText("9");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton10.setText("CLEAR");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel3.setText("Algebraic");

        jRadioButton1.setText(" Degree");

        jRadioButton2.setText("Radian");

        jButton37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton37.setForeground(new java.awt.Color(255, 0, 0));
        jButton37.setText("<--");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jButton39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton39.setText("x^2");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton40.setText("x^n");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton41.setText(" √ x");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jButton42.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton42.setText("10^x");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jButton43.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton43.setText("exp(x)");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jButton44.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton44.setText("n √ x");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        jButton45.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jButton45.setText("log(x)");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        jButton46.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jButton46.setText("ln(x)");

        jButton47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton47.setText("x!");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Scientific", "Loan", "nBase" }));

        jLabel4.setText("Trigonometric");

        jButton52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton52.setText("sinx");
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton52ActionPerformed(evt);
            }
        });

        jButton53.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton53.setText("cosx");
        jButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton53ActionPerformed(evt);
            }
        });

        jButton54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton54.setText("tanx");
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });

        jButton55.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jButton55.setText("asinx");
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });

        jButton56.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton56.setText("acosx");
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });

        jButton57.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton57.setText("atanx");
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        jButton58.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jButton58.setText("sinhx");
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });

        jButton59.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jButton59.setText("coshx");
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });

        jButton60.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton60.setText("tanhx");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton12.setText("π");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton13.setText("(");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton14.setText(")");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton15.setText("HISTORY");

        jButton24.setBackground(new java.awt.Color(0, 0, 0));
        jButton24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton24.setForeground(new java.awt.Color(255, 0, 0));
        jButton24.setText("CALCULATE");
        jButton24.setToolTipText("");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setText("Mode");

        jButton27.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton27.setText("ANS");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton28.setText("LIBRARY");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea2);

        toggle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        toggle.setText("RIGHT");
        toggle.setToolTipText("RIGHT");
        toggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleActionPerformed(evt);
            }
        });

        jLabel6.setText("Alignment");

        jButton38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton38.setText("PREV");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton2)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton38)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toggle)
                                .addGap(18, 18, 18)
                                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton28, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton45)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel3)))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton58)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton37)
                            .addComponent(toggle)
                            .addComponent(jLabel6)
                            .addComponent(jButton38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton4)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"4");
                //Expression str = new Expression();
        str.addChar("4");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Negate Button
        BigDecimal x = new BigDecimal(-1.0);
        str.setAnswer(x.multiply(str.getAnswer()));
        jTextArea2.setText(jTextArea2.getText()+str.getAnswer()+"(Neg)");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"0");
               // Expression str = new Expression();
        str.addChar("0");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jTextArea2.setText("");
               // Expression str = new Expression();
        str.setNull();// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        if(str.toString()!=null){
            String org = str.toString(), small;
            small=org.substring(0, org.length()-1);
            str.setString(small);
            jTextArea2.setText(str.toString());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"ans");
        String previousAnswer = str.getAnswer().toString();
        System.out.println("Previous Answer: "+previousAnswer);
        str.setString(previousAnswer);
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"1");
        //Expression str = new Expression();
        str.addChar("1");
        //System.out.println(str.toString());// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"2"); 
                //Expression str = new Expression();
        str.addChar("2");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"3");
        //Expression str = new Expression();
        str.addChar("3");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"5");
                //Expression str = new Expression();
        str.addChar("5");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"6");
                //Expression str = new Expression();
        str.addChar("6");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"7");
               // Expression str = new Expression();
        str.addChar("7");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"8");
                //Expression str = new Expression();
        str.addChar("8");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"9");
                //Expression str = new Expression();
        str.addChar("9");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+".");
                //Expression str = new Expression();
        str.addChar(".");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"+");
                //Expression str = new Expression();
        str.addChar("+");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"-");
                //Expression str = new Expression();
        str.addChar("-");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"/");
                //Expression str = new Expression();
        str.addChar("/");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"*");
                //Expression str = new Expression();
        str.addChar("*");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"%");
                //Expression str = new Expression();
        str.addChar("%");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"!");
                //Expression str = new Expression();
        str.addChar("!");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"(");
                //Expression str = new Expression();
        str.addChar("(");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+")");
                //Expression str = new Expression();
        str.addChar(")");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"π");
                //Expression str = new Expression();
        str.addChar("3.14159");// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed

       //CALCULATE BUTTON
       
       Color color = Color.RED;
       BigDecimal ans;
       Conversion con = new Conversion(str.toString());
       Evaluation ev = new Evaluation(con.getPostfixAsList());
       ans = ev.result();
       str.setAnswer(ans);
       System.out.println(str.toString()+" = "+str.getAnswer());
       //jTextArea2.setForeground(color.RED);
       jTextArea2.setText(str.toString()+"\n"+str.getAnswer()+"(Ans)"+"\n");
       str.prev = str.toString(); 
       str.setNull();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
                    jTextArea2.setText(jTextArea2.getText()+"^");
                //Expression str = new Expression();
        str.addChar("^");           // TODO add your handling code here:
    }//GEN-LAST:event_jButton40ActionPerformed

    private void toggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleActionPerformed
        if(toggle.isSelected()){
             jTextArea2.setLineWrap(true);
             jTextArea2.setWrapStyleWord(true);
             jTextArea2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);   
        }
        else {
            jTextArea2.setLineWrap(false);
            jTextArea2.setWrapStyleWord(false);
            jTextArea2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            
        }
            // TODO add your handling code here:
    }//GEN-LAST:event_toggleActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
                // Square Button
            jTextArea2.setText(jTextArea2.getText()+"^2");
            str.addChar("^2");
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // Previous Button
        jTextArea2.setText(jTextArea2.getText()+str.prev);
        str.setString(str.prev);
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // Square Root Button
            jTextArea2.setText(jTextArea2.getText()+"√");
            str.addChar("s");
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        // nth Root Buttonn nth√ x
            jTextArea2.setText(jTextArea2.getText()+"*√");
            str.addChar("n");
    }//GEN-LAST:event_jButton44ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // Exp() Button
        jTextArea2.setText(jTextArea2.getText()+"e");
        str.addChar("e");
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        // log() BUtton
        jTextArea2.setText(jTextArea2.getText()+"log");
        str.addChar("l");        
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // Power of Ten Button
        jTextArea2.setText(jTextArea2.getText()+"x10*");
        str.addChar("t");
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton52ActionPerformed
        // sin() Button
        jTextArea2.setText(jTextArea2.getText()+"sin");
        str.addChar("a");
    }//GEN-LAST:event_jButton52ActionPerformed

    private void jButton53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton53ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"cos");
        str.addChar("b");        // cos() Button
    }//GEN-LAST:event_jButton53ActionPerformed

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"tan");
        str.addChar("c");        // tan() Button
    }//GEN-LAST:event_jButton54ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"asin");
        str.addChar("d");        // sin() Inverse Button
    }//GEN-LAST:event_jButton55ActionPerformed

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"acos");
        str.addChar("f");        // cos() Inverse Button
    }//GEN-LAST:event_jButton56ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"atan");
        str.addChar("g");        // tan() Inverse Button
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"sinh");
        str.addChar("h");        // Hyperbolic sin() Button
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"cosh");
        str.addChar("i");        // Hyperbolic cose() Button
    }//GEN-LAST:event_jButton59ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        jTextArea2.setText(jTextArea2.getText()+"tanh");
        str.addChar("j");        //Hyperbolic tan(0 Button
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Absolute Value Button
        str.setAnswer(str.getAnswer().abs());
        jTextArea2.setText(jTextArea2.getText()+str.getAnswer()+"(Abs)");
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calaculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calaculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calaculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calaculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calaculator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JToggleButton toggle;
    // End of variables declaration//GEN-END:variables

    
}
