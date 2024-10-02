import javax.swing.JFrame; 

public class app{

public static void main(String[] args) {
    int bwidth = 360;
    int bheight = 640;
    
    JFrame frame = new JFrame("Saloni is great");
    
    
    
    frame.setResizable(false);

    frame.setSize(bwidth, bheight);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    FlappyBird flappyBird = new FlappyBird();
    frame.add(flappyBird);
    frame.pack(); 
    flappyBird.requestFocus();
    frame.setVisible(true);

    

}

}