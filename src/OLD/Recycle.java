package OLD;

import javax.swing.JFrame;          //JANELA do projeto
import javax.swing.JLabel;          //IMAGENS dos jogadores na tela
import javax.swing.ImageIcon;       //imagens
import java.awt.Container;          //Container para os elementos gráficos
import java.awt.event.KeyListener;  //Memorizador de eventos para o teclado.
import java.awt.event.KeyEvent;     //memorizador
import java.util.ArrayList;         //Memorizador dos rastros criados por cada jogador.
import javax.swing.JOptionPane;     //Mostra o vencedor

/**
 * @author Felipe Lopes Zem
 */
public class Recycle extends JFrame
{
    //Variáveis importantes.
    private static int windowWidth  = 550;  //Dimensão horizontal da janela.
    private static int windowHeight = 550;  //Dimensão vertical da janela.
    private static int arenaWidth   = 550;  //Dimensão H. da arena (imagem de fundo)
    private static int arenaHeight  = 550;  //Dimensão V. da arena (imagem de fundo)
    private static int movementDelay = 50;  //Tempo (em milisegundos) entre cada "passo".
    //OBS: Posições iniciais agora são hard-coded.

    //Para um máximo de 2 jogadores em uma partida, 2 JLabels são criados.
    private static JLabel player1 = new JLabel();
    private static JLabel player2 = new JLabel();
    //Direção inicial para cada jogador (a definir abaixo).
    private static int player1Direction;
    private static int player2Direction;
    
    //Variáveis que determinam as possíveis direções que cada jogador pode tomar.
    private static int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
    //Boolean que impedirá que o jogo continue rodando quando deve parar.
    private static boolean stopRunning = false; //VERDADEIRO para parar o jogo

    //Outras coisas.
    private static Container tronContainer;     //Elementos gráficos
    private static ArrayList playersTrails = new ArrayList();       //Rastros

    /**
     * Construtor padrão
     */
    public Recycle()
    {
        super("Tron");
        setSize(windowWidth, windowHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        tronContainer = getContentPane();
        tronContainer.setLayout(null);

        //Pega as imagens de cada jogador e posiciona elas na tela.
        player1.setIcon(new ImageIcon(getClass().getResource("/Imagens/player1.png")));
        tronContainer.add(player1);
        tronContainer.setComponentZOrder(player1, 0);
        player1.setBounds(120, 270, 10, 10);
        player2.setIcon(new ImageIcon(getClass().getResource("/Imagens/player2.png")));
        tronContainer.add(player2);
        tronContainer.setComponentZOrder(player2, 0);
        player2.setBounds(420, 270, 10, 10);

        //Adiciona-se aqui uma imagem de fundo, que fica ABAIXO das demais imagens.
        JLabel hud = new JLabel();
        hud.setIcon(new ImageIcon(getClass().getResource("/Imagens/HUD.png")));
        tronContainer.add(hud);
        tronContainer.setComponentZOrder(hud, 2);   //Põe o fundo abaixo do resto.
        hud.setBounds(0, 0, windowWidth, windowHeight);

        //Inicializa cada jogador, com sua direção inicial como parâmetro.
        Player1 player1Thread = new Player1(RIGHT);
        player1Thread.start();
        Player2 player2Thread = new Player2(LEFT);
        player2Thread.start();

        //Aplica o contâiner ao JFrame.
        setContentPane(tronContainer);
    }

    public class Player1 extends Thread implements KeyListener
    {
        /**
         * Construtor padrão.
         * @param direction é a direção inicial.
         */
        Player1(int direction)
        {
            player1Direction = direction;
        }
        /**
         * Comando usado para inicializar uma Thread.
         */
        public void run()
        {
            addKeyListener(this);   //Aplica o KeyListener
            while(!stopRunning)     //Thread ativa enquanto o jogo estiver rodando.
            {
                try     //O comando "Thread.sleep(int x)" necessita de try-catch
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

                    //Validate para ordenar e ajustar as imagens no contâiner.
                    tronContainer.validate();

                    //Checa-se aqui se este jogador colidiu com as bordas da janela.
                    if((player1.getX() >= arenaWidth-49) ||
                            (player1.getX() <= 49)    ||
                            (player1.getY() >= arenaHeight-49) ||
                            (player1.getY() <= 24))
                    {
                        player1Lose();
                    }
                    //Checa-se aqui se este jogador colidiu com algum rastro.
                    for(int i = 0; i < playersTrails.size(); i++)
                    {
                        //Cria-se aqui um JLabel temporário usado para comparar com cada
                        //elemento adicionado no ArrayList de rastros.
                        JLabel temp = (JLabel) playersTrails.get(i);
                        if(player1.getBounds().intersects(temp.getBounds()))
                        {
                            player1Lose();
                        }
                    }
                    //Pôe a Thread em espera para finalizar a movimentação do jogador.
                    Thread.sleep(movementDelay);
                }
                catch(Exception e)
                {
                }
            }
        }

        /**
         * Método usado para forçar o jogo a parar quando este jogador
         * for identificado numa colisão.
         */
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

    //Demais classes de jogadores são cópias exatas da classe Player1.
    //As únicas alterações são nos NOMES de métodos e variáveis.
    public class Player2 extends Thread implements KeyListener
    {
        Player2(int direction)
        {
            player2Direction = direction;
        }
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
                    if((player2.getX() >= arenaWidth-49) ||
                            (player2.getX() <= 49)    ||
                            (player2.getY() >= arenaHeight-49) ||
                            (player2.getY() <= 24))
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

                    Thread.sleep(movementDelay);
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

    //MÉTODO MAIN.
    public static void main(String[] args)
    {
        new Recycle();
    }
}