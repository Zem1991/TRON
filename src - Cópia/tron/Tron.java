package tron;

import javax.swing.JFrame;          //JANELA do projeto.
import javax.swing.JLabel;          //IMAGENS dos jogadores na tela.
import javax.swing.ImageIcon;
import java.awt.Container;
import java.awt.event.KeyListener;  //Memorizador de eventos para o teclado.
import java.awt.event.KeyEvent;
import java.util.ArrayList;         //Memorizador dos rastros criados por cada jogador.
import javax.swing.JOptionPane;     //Só serve pra dizer quem venceu.

/**
 * @author Felipe Lopes Zem
 */
public class Tron extends JFrame
{
    //Variáveis importantes.
    private static int windowLateral = 650; //Lateral da janela (que é QUADRADA).
    private static int xStartingPos = 50;   //Diferença do ponto de início para as bordas.
    private static int tempoPorPasso = 50;  //Tempo (em milisegundos) entre cada "passo".

    //Cria aqui 2 JLabels, um para cada jogador.
    private static JLabel player1 = new JLabel();
    private static JLabel player2 = new JLabel();
    //Boolean que impedirá que o jogo continue rodando quando deve parar.
    private static boolean stopRunning = false;
    //Variáveis que determinam as possíveis direções que cada jogador pode tomar.
    private static int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
    //Direção inicial para cada jogador.
    private static int player1Direction = RIGHT;
    private static int player2Direction = LEFT;
    //Outras coisas.
    private static Container tronContainer;
    private static ArrayList playersTrails = new ArrayList();

    public Tron()
    {
        super("Tron");
        setSize(windowLateral, windowLateral);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        tronContainer = getContentPane();
        tronContainer.setLayout(null);
        
        player1.setIcon(new ImageIcon(getClass().getResource("/Imagens/player1.png")));
        tronContainer.add(player1);
        tronContainer.setComponentZOrder(player1, 0);
        player1.setBounds(xStartingPos, (windowLateral/2)-5 , 10, 10);
        player2.setIcon(new ImageIcon(getClass().getResource("/Imagens/player2.png")));
        tronContainer.add(player2);
        tronContainer.setComponentZOrder(player2, 0);
        player2.setBounds(windowLateral-xStartingPos, (windowLateral/2)-5 , 10, 10);

        Player1 player1Thread = new Player1();
        player1Thread.start();
        Player2 player2Thread = new Player2();
        player2Thread.start();

        setContentPane(tronContainer);
    }

    public class Player1 extends Thread implements KeyListener
    {
        public void run()
        {
            addKeyListener(this);
            while(!stopRunning)
            {
                try
                {
                    //Adiciona-se aqui o rastro do jogador.
                    JLabel trail = new JLabel();
                    trail.setIcon(new ImageIcon(getClass().getResource(
                            "/Imagens/player1Trail.png")));
                    playersTrails.add(trail);
                    tronContainer.add(trail);
                    tronContainer.setComponentZOrder(trail, 1);
                    trail.setBounds(player1.getX(), player1.getY(), 10, 10);

                    //Verifica a movimentação do jogador.
                    if(player1Direction == UP)
                    {
                        player1.setBounds(player1.getX(), player1.getY()-10,
                                            10, 10);
                    }
                    if(player1Direction == LEFT)
                    {
                        player1.setBounds(player1.getX()-10, player1.getY(),
                                            10, 10);
                    }
                    if(player1Direction == DOWN)
                    {
                        player1.setBounds(player1.getX(), player1.getY()+10,
                                            10, 10);
                    }
                    if(player1Direction == RIGHT)
                    {
                        player1.setBounds(player1.getX()+10, player1.getY(),
                                            10, 10);
                    }

                    tronContainer.validate();

                    //Checa-se aqui se este jogador colidiu com as bordas da janela.
                    if((player1.getX() >= windowLateral) ||
                            (player1.getX() <= 0)    ||
                            (player1.getY() >= windowLateral) ||
                            (player1.getY() <= 0))
                    {
                        player1Lose();
                    }
                    //Checa-se aqui se este jogador colidiu com algum rastro.
                    for(int i = 0; i < playersTrails.size(); i++)
                    {
                        JLabel temp = (JLabel) playersTrails.get(i);
                        if(player1.getBounds().intersects(temp.getBounds()))
                        {
                            player1Lose();
                        }
                    }

                    Thread.sleep(tempoPorPasso);
                }
                catch(Exception e)
                {
                }
            }
        }

        public void player1Lose()
        {
            stopRunning = true;
            tronContainer.setComponentZOrder(player1, 0);
            JOptionPane.showMessageDialog(null, "Player 2 venceu!");
        }

        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar()=='w')
            {
                if(player1Direction != DOWN)
                {
                    player1Direction = UP;
                }
            }
            if(e.getKeyChar()=='d')
            {
                if(player1Direction != LEFT)
                {
                    player1Direction = RIGHT;
                }
            }
            if(e.getKeyChar()=='s')
            {
                if(player1Direction != UP)
                {
                    player1Direction = DOWN;
                }
            }
            if(e.getKeyChar()=='a')
            {
                if(player1Direction != RIGHT)
                {
                    player1Direction = LEFT;
                }
            }
        }
        public void keyTyped(KeyEvent e)
        {}
        public void keyReleased(KeyEvent e)
        {}
    }

    public class Player2 extends Thread implements KeyListener
    {
        public void run()
        {
            addKeyListener(this);
            while(!stopRunning)
            {
                try
                {
                    //Adiciona-se aqui o rastro do jogador.
                    JLabel trail = new JLabel();
                    trail.setIcon(new ImageIcon(getClass().getResource(
                            "/Imagens/player2Trail.png")));
                    playersTrails.add(trail);
                    tronContainer.add(trail);
                    tronContainer.setComponentZOrder(trail, 1);
                    trail.setBounds(player2.getX(), player2.getY(), 10, 10);
                    
                    //Verifica a movimentação do jogador.
                    if(player2Direction == UP)
                    {
                        player2.setBounds(player2.getX(), player2.getY()-10,
                                            10, 10);
                    }
                    if(player2Direction == LEFT)
                    {
                        player2.setBounds(player2.getX()-10, player2.getY(),
                                            10, 10);
                    }
                    if(player2Direction == DOWN)
                    {
                        player2.setBounds(player2.getX(), player2.getY()+10,
                                            10, 10);
                    }
                    if(player2Direction == RIGHT)
                    {
                        player2.setBounds(player2.getX()+10, player2.getY(),
                                            10, 10);
                    }

                    tronContainer.validate();

                    //Checa-se aqui se este jogador colidiu com as bordas da janela.
                    if((player2.getX() >= windowLateral) ||
                            (player2.getX() <= 0)    ||
                            (player2.getY() >= windowLateral) ||
                            (player2.getY() <= 0))
                    {
                        player2Lose();
                    }
                    //Checa-se aqui se este jogador colidiu com algum rastro.
                    for(int i = 0; i < playersTrails.size(); i++)
                    {
                        JLabel temp = (JLabel) playersTrails.get(i);
                        if(player2.getBounds().intersects(temp.getBounds()))
                        {
                            player2Lose();
                        }
                    }

                    Thread.sleep(tempoPorPasso);
                }
                catch(Exception e)
                {
                }
            }
        }

        public void player2Lose()
        {
            stopRunning = true;
            tronContainer.setComponentZOrder(player2, 0);
            JOptionPane.showMessageDialog(null, "Player 1 venceu!");
        }

        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_UP)
            {
                if(player2Direction != DOWN)
                {
                    player2Direction = UP;
                }
            }
            if(e.getKeyCode()== KeyEvent.VK_RIGHT)
            {
                if(player2Direction != LEFT)
                {
                    player2Direction = RIGHT;
                }
            }
            if(e.getKeyCode()== KeyEvent.VK_DOWN)
            {
                if(player2Direction != UP)
                {
                    player2Direction = DOWN;
                }
            }
            if(e.getKeyCode()== KeyEvent.VK_LEFT)
            {
                if(player2Direction != RIGHT)
                {
                    player2Direction = LEFT;
                }
            }
        }
        public void keyTyped(KeyEvent e)
        {}
        public void keyReleased(KeyEvent e)
        {}
    }

    public static void main(String[] args) {
        new Tron();
    }
}