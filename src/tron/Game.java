package tron;

import java.awt.Color;              //Cor de cada jogador
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
public class Game extends JFrame
{
    //Variáveis importantes.
    private static int windowWidth  = Tron.windowWidth;  //Dimensão horizontal da janela.
    private static int windowHeight = Tron.windowHeight;  //Dimensão vertical da janela.
    private static int arenaWidth   = 550;  //Dimensão H. da arena (imagem de fundo posicionada)
    private static int arenaHeight  = 550;  //Dimensão V. da arena (imagem de fundo posicionada)

    //Para um máximo de 4 jogadores em uma partida, 4 JLabels são criados.
    private static JLabel player1 = new JLabel();
    private static JLabel player2 = new JLabel();
    private static JLabel player3 = new JLabel();
    private static JLabel player4 = new JLabel();
    //Direção inicial para cada jogador (a definir abaixo).
    private static int player1Direction;
    private static int player2Direction;
    private static int player3Direction;
    private static int player4Direction;
    
    //Variáveis que determinam as possíveis direções que cada jogador pode tomar.
    private static int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
    //Boolean que impedirá que o jogo continue rodando quando deve parar.
    protected static boolean stopRunning = false;

    //Outras coisas.
    private static Container tronContainer;
    private static ArrayList playersTrails = new ArrayList();

    //Relacionados ao jogo em si.
    private static int jogadoresAtivos;
    private static boolean p1Ativo = true, p2Ativo = true, p3Ativo = true, p4Ativo = true;
    private static String p1Cor, p2Cor, p3Cor, p4Cor;
    private static Player1 player1Thread;
    private static Player2 player2Thread;
    private static Player3 player3Thread;
    private static Player4 player4Thread;

    /**
     * Construtor padrão.
     * @param numDeJogadores é o numero de jogadores.
     */
    public Game(int numDeJogadores)
    {
        super("TRON");
        setSize(windowWidth, windowHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        tronContainer = getContentPane();
        tronContainer.setLayout(null);

        //PRIMEIRO SWITCH: Posiciona cada jogador na tela.
        //1x1   = Um de frente pro outro, NO MEIO.
        //Outro = Cada um no seu quadrante.
        switch (numDeJogadores)
        {
            case 2:
            player1.setIcon(new ImageIcon(getClass().getResource("/Imagens/player1.png")));
            tronContainer.add(player1);
            tronContainer.setComponentZOrder(player1, 0);
            player1.setBounds(120, 270, Tron.p1tamanho, Tron.p1tamanho);
            player2.setIcon(new ImageIcon(getClass().getResource("/Imagens/player2.png")));
            tronContainer.add(player2);
            tronContainer.setComponentZOrder(player2, 0);
            player2.setBounds(420, 270, Tron.p2tamanho, Tron.p2tamanho);
            break;
            case 3:
            player1.setIcon(new ImageIcon(getClass().getResource("/Imagens/player1.png")));
            tronContainer.add(player1);
            tronContainer.setComponentZOrder(player1, 0);
            player1.setBounds(120, 120, Tron.p1tamanho, Tron.p1tamanho);
            player2.setIcon(new ImageIcon(getClass().getResource("/Imagens/player2.png")));
            tronContainer.add(player2);
            tronContainer.setComponentZOrder(player2, 0);
            player2.setBounds(420, 120, Tron.p2tamanho, Tron.p2tamanho);
            player3.setIcon(new ImageIcon(getClass().getResource("/Imagens/player3.png")));
            tronContainer.add(player3);
            tronContainer.setComponentZOrder(player3, 0);
            player3.setBounds(420, 420, Tron.p3tamanho, Tron.p3tamanho);
            break;
            case 4:
            player1.setIcon(new ImageIcon(getClass().getResource("/Imagens/player1.png")));
            tronContainer.add(player1);
            tronContainer.setComponentZOrder(player1, 0);
            player1.setBounds(120, 120, Tron.p1tamanho, Tron.p1tamanho);
            player2.setIcon(new ImageIcon(getClass().getResource("/Imagens/player2.png")));
            tronContainer.add(player2);
            tronContainer.setComponentZOrder(player2, 0);
            player2.setBounds(420, 120, Tron.p2tamanho, Tron.p2tamanho);
            player3.setIcon(new ImageIcon(getClass().getResource("/Imagens/player3.png")));
            tronContainer.add(player3);
            tronContainer.setComponentZOrder(player3, 0);
            player3.setBounds(420, 420, Tron.p3tamanho, Tron.p3tamanho);
            player4.setIcon(new ImageIcon(getClass().getResource("/Imagens/player4.png")));
            tronContainer.add(player4);
            tronContainer.setComponentZOrder(player4, 0);
            player4.setBounds(120, 420, Tron.p4tamanho, Tron.p4tamanho);
            break;
        }

        //Adiciona-se aqui uma imagem de fundo.
        JLabel hud = new JLabel();
        hud.setIcon(new ImageIcon(getClass().getResource("/Imagens/HUD.png")));
        tronContainer.add(hud);
        tronContainer.setComponentZOrder(hud, 2);       //Bota ela abaixo de tudo.
        hud.setBounds(0, 0, windowWidth, windowHeight);

        jogadoresAtivos = numDeJogadores;
        setColors();        //Método que busca a cor de cada joador antes de inicializá-los.

        //SEGUNDO SWITCH: Inicializa a Thread de cada jogador.
        switch (numDeJogadores)
        {
            case 2:
            player1Thread = new Player1(RIGHT);
            player1Thread.start();
            player2Thread = new Player2(LEFT);
            player2Thread.start();
            break;
            case 3:
            player1Thread = new Player1(RIGHT);
            player1Thread.start();
            player2Thread = new Player2(DOWN);
            player2Thread.start();
            player3Thread = new Player3(LEFT);
            player3Thread.start();
            break;
            case 4:
            player1Thread = new Player1(RIGHT);
            player1Thread.start();
            player2Thread = new Player2(DOWN);
            player2Thread.start();
            player3Thread = new Player3(LEFT);
            player3Thread.start();
            player4Thread = new Player4(UP);
            player4Thread.start();
            break;
        }

        //Aplica o contâiner com os elementos gráficos.
        setContentPane(tronContainer);
    }
    /**
     * Define as cores utilizadas por cada jogador (para o uso de JLabels).
     */
    public void setColors()
    {
        //JOGADOR 1
        if (Tron.p1Color == Color.blue)
        {
            p1Cor = "blue";
        }
        if (Tron.p1Color == Color.red)
        {
            p1Cor = "red";
        }
        if (Tron.p1Color == Color.green)
        {
            p1Cor = "green";
        }
        if (Tron.p1Color == Color.yellow)
        {
            p1Cor = "yellow";
        }
        if (Tron.p1Color == Color.cyan)
        {
            p1Cor = "cyan";
        }
        if (Tron.p1Color == Color.magenta)
        {
            p1Cor = "magenta";
        }
        if (Tron.p1Color == Color.black)
        {
            p1Cor = "black";
        }
        if (Tron.p1Color == Color.white)
        {
            p1Cor = "white";
        }
        //JOGADOR 2
        if (Tron.p2Color == Color.blue)
        {
            p2Cor = "blue";
        }
        if (Tron.p2Color == Color.red)
        {
            p2Cor = "red";
        }
        if (Tron.p2Color == Color.green)
        {
            p2Cor = "green";
        }
        if (Tron.p2Color == Color.yellow)
        {
            p2Cor = "yellow";
        }
        if (Tron.p2Color == Color.cyan)
        {
            p2Cor = "cyan";
        }
        if (Tron.p2Color == Color.magenta)
        {
            p2Cor = "magenta";
        }
        if (Tron.p2Color == Color.black)
        {
            p2Cor = "black";
        }
        if (Tron.p2Color == Color.white)
        {
            p2Cor = "white";
        }
        //JOGADOR 3
        if (Tron.p3Color == Color.blue)
        {
            p3Cor = "blue";
        }
        if (Tron.p3Color == Color.red)
        {
            p3Cor = "red";
        }
        if (Tron.p3Color == Color.green)
        {
            p3Cor = "green";
        }
        if (Tron.p3Color == Color.yellow)
        {
            p3Cor = "yellow";
        }
        if (Tron.p3Color == Color.cyan)
        {
            p3Cor = "cyan";
        }
        if (Tron.p3Color == Color.magenta)
        {
            p3Cor = "magenta";
        }
        if (Tron.p3Color == Color.black)
        {
            p3Cor = "black";
        }
        if (Tron.p3Color == Color.white)
        {
            p3Cor = "white";
        }
        //JOGADOR 4
        if (Tron.p4Color == Color.blue)
        {
            p4Cor = "blue";
        }
        if (Tron.p4Color == Color.red)
        {
            p4Cor = "red";
        }
        if (Tron.p4Color == Color.green)
        {
            p4Cor = "green";
        }
        if (Tron.p4Color == Color.yellow)
        {
            p4Cor = "yellow";
        }
        if (Tron.p4Color == Color.cyan)
        {
            p4Cor = "cyan";
        }
        if (Tron.p4Color == Color.magenta)
        {
            p4Cor = "magenta";
        }
        if (Tron.p4Color == Color.black)
        {
            p4Cor = "black";
        }
        if (Tron.p4Color == Color.white)
        {
            p4Cor = "white";
        }
    }
    /**
     * Este método deve ser chamado SEMPRE que um jogador perder.
     * Quando apenas um jogador sobrar ativo, o jogo encerra.
     */
    void gameOver()
    {
        jogadoresAtivos--;
        System.out.println("Jogadores ativos: "+jogadoresAtivos);
        if (jogadoresAtivos == 1)
        {
            if (p1Ativo == true)
            {
                //Jogador 1 venceu!
                stopRunning = true;
                tronContainer.setComponentZOrder(player1, 0);
                JOptionPane.showMessageDialog(null, "Jogador 1 venceu!");
            }
            else if (p2Ativo == true)
            {
                //Jogador 2 venceu!
                stopRunning = true;
                tronContainer.setComponentZOrder(player2, 0);
                JOptionPane.showMessageDialog(null, "Jogador 2 venceu!");
            }
            else if (p3Ativo == true)
            {
                //Jogador 3 venceu!
                stopRunning = true;
                tronContainer.setComponentZOrder(player3, 0);
                JOptionPane.showMessageDialog(null, "Jogador 3 venceu!");
            }
            else if (p4Ativo == true)
            {
                //Jogador 4 venceu!
                stopRunning = true;
                tronContainer.setComponentZOrder(player4, 0);
                JOptionPane.showMessageDialog(null, "Jogador 4 venceu!");
            }
        }
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
        @Override
        public void run()
        {
            addKeyListener(this);
            //Thread ativa enquanto este jogador estiver OK ou enquanto o jogo não encerrar.
            while((p1Ativo) && (!stopRunning))
            {
                try //O comando "Thread.sleep(int x)" precisa de try-catch.
                {
                    //Adiciona-se aqui o rastro do jogador.
                    JLabel trail = new JLabel();
                    trail.setIcon(new ImageIcon(getClass().getResource(
                            "/Imagens/"+p1Cor+".png")));
                    playersTrails.add(trail);
                    tronContainer.add(trail);
                    tronContainer.setComponentZOrder(trail, 1);
                    trail.setBounds(player1.getX(), player1.getY(),
                            Tron.p1tamanho, Tron.p1tamanho);

                    //Verifica a movimentação do jogador.
                    if(player1Direction == UP)
                    {
                        player1.setBounds(player1.getX(), player1.getY()-Tron.p1tamanho,
                                            Tron.p1tamanho, Tron.p1tamanho);
                    }
                    if(player1Direction == LEFT)
                    {
                        player1.setBounds(player1.getX()-Tron.p1tamanho, player1.getY(),
                                            Tron.p1tamanho, Tron.p1tamanho);
                    }
                    if(player1Direction == DOWN)
                    {
                        player1.setBounds(player1.getX(), player1.getY()+Tron.p1tamanho,
                                            Tron.p1tamanho, Tron.p1tamanho);
                    }
                    if(player1Direction == RIGHT)
                    {
                        player1.setBounds(player1.getX()+Tron.p1tamanho, player1.getY(),
                                            Tron.p1tamanho, Tron.p1tamanho);
                    }

                    //Organiza os elementos do contâiner.
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
                        //JLabel temporário para comparar colisões com cada elemento.
                        JLabel temp = (JLabel) playersTrails.get(i);
                        if(player1.getBounds().intersects(temp.getBounds()))
                        {
                            player1Lose();
                        }
                    }

                    Thread.sleep(Tron.p1velocidade);    //Isto aqui precisa de try-catch.
                }
                catch(Exception e)
                {
                }
            }
        }

        /**
         * Método usado para mostrar que este jogador perdeu.
         */
        public void player1Lose()
        {
            p1Ativo = false;
            gameOver();
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
        @Override
        public void run()
        {
            addKeyListener(this);
            while((p2Ativo) && (!stopRunning))
            {
                try
                {
                    //Adiciona-se aqui o rastro do jogador.
                    JLabel trail = new JLabel();
                    trail.setIcon(new ImageIcon(getClass().getResource(
                            "/Imagens/"+p2Cor+".png")));
                    playersTrails.add(trail);
                    tronContainer.add(trail);
                    tronContainer.setComponentZOrder(trail, 1);
                    trail.setBounds(player2.getX(), player2.getY(),
                            Tron.p2tamanho, Tron.p2tamanho);

                    //Verifica a movimentação do jogador.
                    if(player2Direction == UP)
                    {
                        player2.setBounds(player2.getX(), player2.getY()-Tron.p2tamanho,
                                            Tron.p2tamanho, Tron.p2tamanho);
                    }
                    if(player2Direction == LEFT)
                    {
                        player2.setBounds(player2.getX()-Tron.p2tamanho, player2.getY(),
                                            Tron.p2tamanho, Tron.p2tamanho);
                    }
                    if(player2Direction == DOWN)
                    {
                        player2.setBounds(player2.getX(), player2.getY()+Tron.p2tamanho,
                                            Tron.p2tamanho, Tron.p2tamanho);
                    }
                    if(player2Direction == RIGHT)
                    {
                        player2.setBounds(player2.getX()+Tron.p1tamanho, player2.getY(),
                                            Tron.p2tamanho, Tron.p2tamanho);
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

                    Thread.sleep(Tron.p2velocidade);    //Isto aqui precisa de try-catch.
                }
                catch(Exception e)
                {
                }
            }
        }

        public void player2Lose()
        {
            p2Ativo = false;
            gameOver();
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

    public class Player3 extends Thread implements KeyListener
    {
        Player3(int direction)
        {
            player3Direction = direction;
        }
        @Override
        public void run()
        {
            addKeyListener(this);
            while((p3Ativo) && (!stopRunning))
            {
                try
                {
                    //Adiciona-se aqui o rastro do jogador.
                    JLabel trail = new JLabel();
                    trail.setIcon(new ImageIcon(getClass().getResource(
                            "/Imagens/"+p3Cor+".png")));
                    playersTrails.add(trail);
                    tronContainer.add(trail);
                    tronContainer.setComponentZOrder(trail, 1);
                    trail.setBounds(player3.getX(), player3.getY(),
                            Tron.p3tamanho, Tron.p3tamanho);

                    //Verifica a movimentação do jogador.
                    if(player3Direction == UP)
                    {
                        player3.setBounds(player3.getX(), player3.getY()-Tron.p3tamanho,
                                            Tron.p3tamanho, Tron.p3tamanho);
                    }
                    if(player3Direction == LEFT)
                    {
                        player3.setBounds(player3.getX()-Tron.p3tamanho, player3.getY(),
                                            Tron.p3tamanho, Tron.p3tamanho);
                    }
                    if(player3Direction == DOWN)
                    {
                        player3.setBounds(player3.getX(), player3.getY()+Tron.p3tamanho,
                                            Tron.p3tamanho, Tron.p3tamanho);
                    }
                    if(player3Direction == RIGHT)
                    {
                        player3.setBounds(player3.getX()+Tron.p3tamanho, player3.getY(),
                                            Tron.p3tamanho, Tron.p3tamanho);
                    }

                    tronContainer.validate();

                    //Checa-se aqui se este jogador colidiu com as bordas da janela.
                    if((player3.getX() >= arenaWidth-49) ||
                            (player3.getX() <= 49)    ||
                            (player3.getY() >= arenaHeight-49) ||
                            (player3.getY() <= 24))
                    {
                        player3Lose();
                    }
                    //Checa-se aqui se este jogador colidiu com algum rastro.
                    for(int i = 0; i < playersTrails.size(); i++)
                    {
                        JLabel temp = (JLabel) playersTrails.get(i);
                        if(player3.getBounds().intersects(temp.getBounds()))
                        {
                            player3Lose();
                        }
                    }

                    Thread.sleep(Tron.p3velocidade);    //Isto aqui precisa de try-catch.
                }
                catch(Exception e)
                {
                }
            }
        }

        public void player3Lose()
        {
            p3Ativo = false;
            gameOver();
        }

        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar()=='t')
            {
                if(player3Direction != DOWN)
                {
                    player3Direction = UP;
                }
            }
            if(e.getKeyChar()=='h')
            {
                if(player3Direction != LEFT)
                {
                    player3Direction = RIGHT;
                }
            }
            if(e.getKeyChar()=='g')
            {
                if(player3Direction != UP)
                {
                    player3Direction = DOWN;
                }
            }
            if(e.getKeyChar()=='f')
            {
                if(player3Direction != RIGHT)
                {
                    player3Direction = LEFT;
                }
            }
        }
        public void keyTyped(KeyEvent e)
        {}
        public void keyReleased(KeyEvent e)
        {}
    }

    public class Player4 extends Thread implements KeyListener
    {
        Player4(int direction)
        {
            player4Direction = direction;
        }
        @Override
        public void run()
        {
            addKeyListener(this);
            while((p4Ativo) && (!stopRunning))
            {
                try
                {
                    //Adiciona-se aqui o rastro do jogador.
                    JLabel trail = new JLabel();
                    trail.setIcon(new ImageIcon(getClass().getResource(
                            "/Imagens/"+p4Cor+".png")));
                    playersTrails.add(trail);
                    tronContainer.add(trail);
                    tronContainer.setComponentZOrder(trail, 1);
                    trail.setBounds(player4.getX(), player4.getY(),
                            Tron.p4tamanho, Tron.p4tamanho);

                    //Verifica a movimentação do jogador.
                    if(player4Direction == UP)
                    {
                        player4.setBounds(player4.getX(), player4.getY()-Tron.p4tamanho,
                                            Tron.p4tamanho, Tron.p4tamanho);
                    }
                    if(player4Direction == LEFT)
                    {
                        player4.setBounds(player4.getX()-Tron.p4tamanho, player4.getY(),
                                            Tron.p4tamanho, Tron.p4tamanho);
                    }
                    if(player4Direction == DOWN)
                    {
                        player4.setBounds(player4.getX(), player4.getY()+Tron.p4tamanho,
                                            Tron.p4tamanho, Tron.p4tamanho);
                    }
                    if(player4Direction == RIGHT)
                    {
                        player4.setBounds(player4.getX()+Tron.p4tamanho, player4.getY(),
                                            Tron.p4tamanho, Tron.p4tamanho);
                    }

                    tronContainer.validate();

                    //Checa-se aqui se este jogador colidiu com as bordas da janela.
                    if((player4.getX() >= arenaWidth-49) ||
                            (player4.getX() <= 49)    ||
                            (player4.getY() >= arenaHeight-49) ||
                            (player4.getY() <= 24))
                    {
                        player4Lose();
                    }
                    //Checa-se aqui se este jogador colidiu com algum rastro.
                    for(int i = 0; i < playersTrails.size(); i++)
                    {
                        JLabel temp = (JLabel) playersTrails.get(i);
                        if(player4.getBounds().intersects(temp.getBounds()))
                        {
                            player4Lose();
                        }
                    }

                    Thread.sleep(Tron.p4velocidade);    //Isto aqui precisa de try-catch.
                }
                catch(Exception e)
                {
                }
            }
        }

        public void player4Lose()
        {
            p4Ativo = false;
            gameOver();
        }

        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar()=='i')
            {
                if(player4Direction != DOWN)
                {
                    player4Direction = UP;
                }
            }
            if(e.getKeyChar()=='l')
            {
                if(player4Direction != LEFT)
                {
                    player4Direction = RIGHT;
                }
            }
            if(e.getKeyChar()=='k')
            {
                if(player4Direction != UP)
                {
                    player4Direction = DOWN;
                }
            }
            if(e.getKeyChar()=='j')
            {
                if(player4Direction != RIGHT)
                {
                    player4Direction = LEFT;
                }
            }
        }
        public void keyTyped(KeyEvent e)
        {}
        public void keyReleased(KeyEvent e)
        {}
    }
}