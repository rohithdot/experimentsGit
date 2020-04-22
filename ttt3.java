import java.awt.* ; 
import java.awt.event.* ; 
import java.io.* ; 
import java.net.* ;
import javax.swing.* ; 
import javax.swing.border.* ; 

public class ttt3 extends JFrame 
                       {
   JButton b11,b21,b31,
           b12,b22,b32,
           b13,b23,b33 ;
   boolean myturn ; 
   BufferedReader br ; 
   BufferedWriter bw ;
   Thread connection ; 
   Process prologProcess ; 
   String prolog ; 
   String ttt ; 


   public ttt3(String prolog, String ttt) { 
      this.prolog = prolog ; 
      this.ttt = ttt ; 
      b11 = new JButton("") ; 
      b21 = new JButton("") ; 
      b31 = new JButton("") ; 
      b12 = new JButton("") ; 
      b22 = new JButton("") ; 
      b32 = new JButton("") ; 
      b13 = new JButton("") ; 
      b23 = new JButton("") ; 
      b33 = new JButton("") ; 

      Font f = new Font("monospaced",Font.PLAIN,64) ;
      b11.setFont(f) ; 
      b21.setFont(f) ; 
      b31.setFont(f) ; 
      b12.setFont(f) ; 
      b22.setFont(f) ; 
      b32.setFont(f) ; 
      b13.setFont(f) ; 
      b23.setFont(f) ; 
      b33.setFont(f) ; 

      JPanel panel = new JPanel() ; 
      panel.setLayout(new GridLayout(3,3)) ; 
      panel.add(b11) ; 
      panel.add(b21) ; 
      panel.add(b31) ; 
      panel.add(b12) ; 
      panel.add(b22) ; 
      panel.add(b32) ; 
      panel.add(b13) ; 
      panel.add(b23) ; 
      panel.add(b33) ; 

      this.setTitle("Tic Tac Toe") ; 
      Border panelborder = BorderFactory.createLoweredBevelBorder() ; 
      panel.setBorder(panelborder) ; 
      this.getContentPane().add(panel) ; 
      this.setSize(300,300) ;
      this.setLocation(900,300) ; 
      this.myturn = true ; 

      Connector connector = new Connector(54321) ; 
      connector.start() ; 

      Socket sock ;
      try {
         sock = new Socket("127.0.0.1",54321) ;
         br = new BufferedReader(new InputStreamReader(sock.getInputStream())) ; 
         bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())) ; 

      } catch(Exception x) { System.out.println(x) ; }

      connection = new Thread() {
         public void run() { 
            while(true) {
               try{
                  if (!myturn)
                     break;

                  bw.write("(2,2)." + "\n");
                  bw.flush();
                  myturn = false;
                  if (winner()) {
                     connection.stop();
                     System.out.println("H_stopped!");
                  }
                  String s = br.readLine() ;
                  computer_move(s) ; 


               } catch(Exception xx) { System.out.println(xx) ; }
            }  
         }
      } ;
      connection.start() ;

      Thread shows = new Thread() { 
         public void run() { 
            setVisible(true) ;
         }
      } ;
      EventQueue.invokeLater(shows);



      try { 
         prologProcess = 
           Runtime.getRuntime().exec(prolog + " -f " + ttt) ; 
      } catch(Exception xx) {System.out.println(xx) ; }


      this.addWindowListener(new WindowAdapter() { 
         public void windowClosing(WindowEvent w) { 
            if (prologProcess != null) prologProcess.destroy() ;
            System.exit(0) ; 
         }
      }) ; 

   } 


   public static void main(String[] args) { 
      String prolog = "/usr/local/bin/swipl" ;
      String ttt = "/Users/rohith/Desktop/assignment4/ttt3A/ttt3.pl";
      boolean noargs = true ; 
      try { 
         prolog = args[0] ;
         ttt = args[1] ;
         noargs = false ; 
      } 
      catch (Exception xx) {
         System.out.println("usage: java TicTactoe  <where prolog>  <where ttt>") ; 
      }
      if (noargs) { 
         Object[] message = new Object[4] ; 
         message[0] = new Label("Path to prolog") ;
         message[1] = new JTextField(prolog) ; 
         message[2] = new Label("Path to ttt.pl") ;
         message[3] = new JTextField(ttt) ; 
         try { 
            int I = JOptionPane.showConfirmDialog(null,message,"Where are Prolog and ttt.pl? ",JOptionPane.OK_CANCEL_OPTION) ;  
            if (I == 2 | I == 1) System.exit(0) ;
            System.out.println(I) ; 
            new ttt3(((JTextField)message[1]).getText().trim(),((JTextField)message[3]).getText().trim()) ; 
         } catch(Exception yy) {} 
      }
      else
         new ttt3(prolog,ttt) ; 
   }




   void computer_move(String s) {
      String[] c = s.split(",") ; 
      int x = Integer.parseInt(c[0].trim()), 
          y = Integer.parseInt(c[1].trim()) ;
      int u = Integer.parseInt(c[2].trim()), 
          v = Integer.parseInt(c[3].trim()); 
      int p = Integer.parseInt(c[4].trim()), 
          q = Integer.parseInt(c[5].trim());


      if(p!=x && q!=y) {
         System.out.println("Player 1 blocked move:[" + p + "," + q + "]");
      }

      System.out.println("Player 1 selects:["+ x +"," + y +"]");

      System.out.println("Player 2 selects:["+ u +"," + v +"]");


      System.out.println("");

      if (p == 1) {
         if (q == 1) b11.setText("*") ;
         else if (q == 2) b12.setText("*") ;
         else if (q == 3) b13.setText("*") ;
      }
      else if (p == 2) {
         if (q == 1) b21.setText("*") ;
         else if (q == 2) b22.setText("*") ;
         else if (q == 3) b23.setText("*") ;
      }
      else if (p == 3) {
         if (q == 1) b31.setText("*") ;
         else if (q== 2) b32.setText("*") ;
         else if (q == 3) b33.setText("*") ;
      }


      if (x == 1) {
         if (y == 1) b11.setText("X") ; 
         else if (y == 2) b12.setText("X") ; 
         else if (y == 3) b13.setText("X") ; 
      }
      else if (x == 2) {
         if (y == 1) b21.setText("X") ;
         else if (y == 2) b22.setText("X") ; 
         else if (y == 3) b23.setText("X") ; 
      }
      else if (x == 3) { 
         if (y == 1) b31.setText("X") ;
         else if (y == 2) b32.setText("X") ; 
         else if (y == 3) b33.setText("X") ; 
      }



      if (winner()) {connection.stop(); System.out.println("stopped!");} 
      else{
         myturn = true ;

         if (u == 1) {
            if (v == 1)
               b11.setText("O");
            else if (v == 2)
               b12.setText("O");
            else if (v == 3)
               b13.setText("O");
         } else if (u == 2) {
            if (v == 1)
               b21.setText("O");
            else if (v == 2)
               b22.setText("O");
            else if (v == 3)
               b23.setText("O");
         } else if (u == 3) {
            if (v == 1)
               b31.setText("O");
            else if (v == 2)
               b32.setText("O");
            else if (v == 3)
               b33.setText("O");
         }
      }
      
      
      
      if (winner()) {connection.stop(); System.out.println("stopped!");} 
      else  myturn = true ;

   }
   

   public void actionPerformed(ActionEvent act) {

      if (!myturn) return ;
      String s = ((JButton)act.getSource()).getText() ; 
      if (!s.equals("")) return  ; 

      try { 

         bw.write(act.getActionCommand() + "\n") ;
         bw.flush();
      } catch(Exception xx) { System.out.println(xx) ; } 
      myturn = false ; 

      if (winner()) {connection.stop() ;
         System.out.println("H_stopped!");}
   }


   boolean winner() { 
      return  line(b11,b21,b31) ||
         line(b12,b22,b32) ||
         line(b13,b23,b33) ||
         line(b11,b12,b13) ||
         line(b21,b22,b23) ||
         line(b31,b32,b33) ||
         line(b11,b22,b33) ||
         line(b13,b22,b31)  ;
   }


   boolean line(JButton b, JButton c, JButton d) {        
      if (!b.getText().equals("") &&b.getText().equals(c.getText()) &&
                c.getText().equals(d.getText()))  {
                  if (b.getText().equals("O")) { 
                     b.setBackground(Color.red) ;
                     b.setOpaque(true);
                     b.setBorderPainted(false);
         
                     c.setBackground(Color.red) ;
                     c.setOpaque(true);
                     c.setBorderPainted(false);
         
                     d.setBackground(Color.red) ;
                     d.setOpaque(true);
                     d.setBorderPainted(false); 
                  } 
                  else { 
                     b.setBackground(Color.green) ;
                     b.setOpaque(true);
                     b.setBorderPainted(false);
         
                     c.setBackground(Color.green) ;
                     c.setOpaque(true);
                     c.setBorderPainted(false);
         
                     d.setBackground(Color.green) ;
                     d.setOpaque(true);
                     d.setBorderPainted(false); 
                  }
         return true ;  
      } else return false;
   }

}
  

