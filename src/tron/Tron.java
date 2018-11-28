package tron;

import java.awt.Color;              //Para definir a cor do rastro dos jogadores.
import java.awt.event.MouseEvent;   //Verifica o uso do mouse nos componentes do JFrame.
import java.awt.event.MouseListener;//mouse no JFrame
import javax.swing.JFrame;          //JANELA do projeto.
import javax.swing.JLabel;          //IMAGENS dos jogadores na tela.
import java.awt.Font;               //Uso de fontes customizáveis em componentes.
import javax.swing.ButtonGroup;     //Agrupador de JRadioButton
import javax.swing.JButton;         //Botão comum
import javax.swing.JRadioButton;    //Botão
import javax.swing.JSpinner;        //Campo numércio com botões para aumentar e diminuir valor.
import javax.swing.JTextArea;       //Campo de texto customizável.
import javax.swing.SpinnerNumberModel;  //Modelo para o uso de JSpinner

/**
 * @author Felipe Lopes Zem
 */
public class Tron extends JFrame implements MouseListener
{
    //Variáveis importantes.
    private static String projectName = "Tron"; //Nome do projeto.
    protected static int windowWidth  = 550;  //Dimensão horizontal da janela.
    protected static int windowHeight = 550;  //Dimensão vertical da janela.

    //Botões e etc.
    private static JLabel playerSet, colorSet, speedSet, sizeSet;
    private static ButtonGroup playerButtons;
    private static JRadioButton pl2, pl3, pl4;
    private static JButton p1Cor, p2Cor, p3Cor, p4Cor, iniciar;
    private static JSpinner p1Vel, p2Vel, p3Vel, p4Vel,
            p1Size, p2Size, p3Size, p4Size;
    private static JTextArea descricao;

    //Relacionados ao jogo em si.
    private static int playersCount = 2;    //Conta quantos jogadores existirão na partida.
    protected static Color p1Color = Color.blue, p2Color = Color.red,
            p3Color = Color.green, p4Color = Color.yellow;  //A cor de cada jogador.
    protected static int p1velocidade, p2velocidade, p3velocidade, p4velocidade;
    protected static int p1tamanho, p2tamanho, p3tamanho, p4tamanho;

    /**
     * Construtor padrão.
     */
    public Tron()
    {
        //Prepara a janela de opções.
        super(projectName+": Opções de jogo.");
        setSize(windowWidth, windowHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLayout(null);

        //OPÇÕES: JOGADORES.
        playerSet = new JLabel("Jogadores:");
        playerSet.setBounds(50, 30, 100, 20);
        add(playerSet);
        pl2 = new JRadioButton("2 jogadores");
        pl2.setBounds(50, 60, 100, 20);
        add(pl2);
        pl3 = new JRadioButton("3 jogadores");
        pl3.setBounds(50, 80, 100, 20);
        add(pl3);
        pl4 = new JRadioButton("4 jogadores");
        pl4.setBounds(50, 100, 100, 20);
        add(pl4);
        pl2.setSelected(true);
        playerButtons = new ButtonGroup();
        playerButtons.add(pl2);
        playerButtons.add(pl3);
        playerButtons.add(pl4);
        pl2.addMouseListener((MouseListener) this);
        pl3.addMouseListener((MouseListener) this);
        pl4.addMouseListener((MouseListener) this);

        //OPÇÕES: CORES.
        colorSet = new JLabel("Cores:");
        colorSet.setBounds(175, 20, 80, 20);
        add(colorSet);
        p1Cor = new JButton("Jogador 1");
        p1Cor.setBounds(175, 40, 100, 20);
        p1Cor.setBackground(p1Color);
        add(p1Cor);
        p2Cor = new JButton("Jogador 2");
        p2Cor.setBounds(175, 60, 100, 20);
        p2Cor.setBackground(p2Color);
        add(p2Cor);
        p3Cor = new JButton("Jogador 3");
        p3Cor.setBounds(175, 80, 100, 20);
        p3Cor.setBackground(p3Color);
        add(p3Cor);
        p4Cor = new JButton("Jogador 4");
        p4Cor.setBounds(175, 100, 100, 20);
        p4Cor.setBackground(p4Color);
        add(p4Cor);
        p1Cor.addMouseListener((MouseListener) this);
        p2Cor.addMouseListener((MouseListener) this);
        p3Cor.addMouseListener((MouseListener) this);
        p4Cor.addMouseListener((MouseListener) this);

        //OPÇÕES: VELOCIDADES.
        speedSet = new JLabel("Velocidades:");
        speedSet.setBounds(300, 20, 100, 20);
        add(speedSet);
        p1Vel = new JSpinner(new SpinnerNumberModel(75, 50, 100, 1));
        p1Vel.setBounds(300, 40, 100, 20);
        add(p1Vel);
        p2Vel = new JSpinner(new SpinnerNumberModel(75, 50, 100, 1));
        p2Vel.setBounds(300, 60, 100, 20);
        add(p2Vel);
        p3Vel = new JSpinner(new SpinnerNumberModel(75, 50, 100, 1));
        p3Vel.setBounds(300, 80, 100, 20);
        add(p3Vel);
        p4Vel = new JSpinner(new SpinnerNumberModel(75, 50, 100, 1));
        p4Vel.setBounds(300, 100, 100, 20);
        add(p4Vel);
        p1Vel.addMouseListener((MouseListener) this);
        p2Vel.addMouseListener((MouseListener) this);
        p3Vel.addMouseListener((MouseListener) this);
        p4Vel.addMouseListener((MouseListener) this);

        //OPÇÕES: TAMANHOS.
        sizeSet = new JLabel("Tamanhos:");
        sizeSet.setBounds(425, 20, 100, 20);
        add(sizeSet);
        p1Size = new JSpinner(new SpinnerNumberModel(10, 5, 25, 5));
        p1Size.setBounds(425, 40, 100, 20);
        add(p1Size);
        p2Size = new JSpinner(new SpinnerNumberModel(10, 5, 25, 5));
        p2Size.setBounds(425, 60, 100, 20);
        add(p2Size);
        p3Size = new JSpinner(new SpinnerNumberModel(10, 5, 25, 5));
        p3Size.setBounds(425, 80, 100, 20);
        add(p3Size);
        p4Size = new JSpinner(new SpinnerNumberModel(10, 5, 25, 5));
        p4Size.setBounds(425, 100, 100, 20);
        add(p4Size);
        p1Size.addMouseListener((MouseListener) this);
        p2Size.addMouseListener((MouseListener) this);
        p3Size.addMouseListener((MouseListener) this);
        p4Size.addMouseListener((MouseListener) this);

        //Cria um campo de texto que descreve o que cada opção implica.
        descricao = new JTextArea();
        descricao.setFont(new Font("Courrier New", Font.PLAIN, 12));
        descricao.setLineWrap(true);    //VERDADEIRO para cortar o texto no fim das linhas.
        descricao.setWrapStyleWord(true);   //VERDADEIRO para manter palavras inteiras.
        descricao.setEditable(false);   //FALSO para impedir o TextArea de ser editado.
        descricao.setBounds(50, 450, 330, 50);
        add(descricao);

        //FIM.
        iniciar = new JButton("Iniciar");
        iniciar.setBounds(400, 450, 100, 50);
        iniciar.addMouseListener((MouseListener) this);
        add(iniciar);

        //Chama o método criado checkOptions para esconder certas opções com base no número de
        //jogadores pré-selecionados (2).
        checkOptions(playersCount);
        //Repaint para que todos os elementos do JFrame apareçam.
        repaint();
    }

    /**
     * Esconde certas opções baseado no número de jogadores.
     * @param players é o número de jogadores atualmente selecionado (2, 3 ou 4).
     */
    public void checkOptions(int players)
    {
        boolean player3 = false;
        boolean player4 = false;
        if (players == 2)
        {
            player3 = false;
            player4 = false;
            p3Cor.setBackground(null);
            p4Cor.setBackground(null);
        }
        if (players == 3)
        {
            player3 = true;
            player4 = false;
            p3Cor.setBackground(p3Color);
            p4Cor.setBackground(null);
        }
        if (players == 4)
        {
            player3 = true;
            player4 = true;
            p3Cor.setBackground(p3Color);
            p4Cor.setBackground(p4Color);
        }
        p3Cor.setEnabled(player3);
        p3Vel.setEnabled(player3);
        p3Size.setEnabled(player3);
        p4Cor.setEnabled(player4);
        p4Vel.setEnabled(player4);
        p4Size.setEnabled(player4);
    }
    /**
     * Auxilia na mudança de cor de um jogador.
     * A nova cor selecionada é a seguinte à atual, na relação:
     *      BLUE, RED, GREEN, YELLOW, CYAN, MAGENTA, BLACK, WHITE, RED.
     * @param color é a cor que o jogador possui atualmente. Ela será mudada no final do método.
     */
    public Color changeColor(Color color)
    {
        if (color == Color.blue)
        {
            color = Color.red;
        }
        else if(color == Color.red)
        {
            color = Color.green;
        }
        else if(color == Color.green)
        {
            color = Color.yellow;
        }
        else if(color == Color.yellow)
        {
            color = Color.cyan;
        }
        else if(color == Color.cyan)
        {
            color = Color.magenta;
        }
        else if(color == Color.magenta)
        {
            color = Color.black;
        }
        else if(color == Color.black)
        {
            color = Color.white;
        }
        else if(color == Color.white)
        {
            color = Color.blue;
        }
        return color;
    }
    /**
     * Começa enfim uma partida com as opções atuais.
     */
    public void newGame()
    {
        setVisible(false);      //Esconde a janela de opções.
        new Game(playersCount);
    }

    //MÉTODO MAIN.
    public static void main(String[] args)
    {
        new Tron();
    }
    
    public void mouseClicked(MouseEvent e) {
        System.out.println("Ação performada.");
        if (e.getSource() == pl2)
        {
            System.out.println("2 jogadores.");
            playersCount = 2;
            checkOptions(playersCount);
        }
        if (e.getSource() == pl3)
        {
            System.out.println("3 jogadores.");
            playersCount = 3;
            checkOptions(playersCount);
        }
        if (e.getSource() == pl4)
        {
            System.out.println("4 jogadores.");
            playersCount = 4;
            checkOptions(playersCount);
        }
        if (e.getSource() == p1Cor)
        {
            System.out.println("Cor do Jogador 1 alterada.");
            p1Cor.setBackground(p1Color = changeColor(p1Color));
        }
        if (e.getSource() == p2Cor)
        {
            System.out.println("Cor do Jogador 2 alterada.");
            p2Cor.setBackground(p2Color = changeColor(p2Color));
        }
        if ((e.getSource() == p3Cor) && (p3Cor.isEnabled() == true))
        {
            System.out.println("Cor do Jogador 3 alterada.");
            p3Cor.setBackground(p3Color = changeColor(p3Color));
        }
        if ((e.getSource() == p4Cor) && (p4Cor.isEnabled() == true))
        {
            System.out.println("Cor do Jogador 4 alterada.");
            p4Cor.setBackground(p4Color = changeColor(p4Color));
        }
        if (e.getSource() == iniciar)
        {
            System.out.println("Iniciar novo jogo.");

            //Para melhor performance, os valores dos JSpinners são alterados só aqui.
            p1velocidade = Integer.parseInt(((Integer)p1Vel.getValue()).toString());
            p2velocidade = Integer.parseInt(((Integer)p2Vel.getValue()).toString());
            p3velocidade = Integer.parseInt(((Integer)p3Vel.getValue()).toString());
            p4velocidade = Integer.parseInt(((Integer)p4Vel.getValue()).toString());
            p1tamanho = Integer.parseInt(((Integer)p1Size.getValue()).toString());
            p2tamanho = Integer.parseInt(((Integer)p2Size.getValue()).toString());
            p3tamanho = Integer.parseInt(((Integer)p3Size.getValue()).toString());
            p4tamanho = Integer.parseInt(((Integer)p4Size.getValue()).toString());

            //Chama o método que inicializa o jogo em si.
            newGame();
        }
    }
    public void mousePressed(MouseEvent e)
    {}
    public void mouseReleased(MouseEvent e)
    {}
    public void mouseEntered(MouseEvent e)
    {
        if (e.getSource() == pl2)
        {
            descricao.setText("Modo para 2 jogadores.");
        }
        if (e.getSource() == pl3)
        {
            descricao.setText("Modo para 3 jogadores.");
        }
        if (e.getSource() == pl4)
        {
            descricao.setText("Modo para 4 jogadores.");
        }
        if (e.getSource() == p1Cor)
        {
            descricao.setText("Altera a cor do Jogador 1.");
        }
        if (e.getSource() == p2Cor)
        {
            descricao.setText("Altera a cor do Jogador 2.");
        }
        if (e.getSource() == p3Cor)
        {
            descricao.setText("Altera a cor do Jogador 3.");
        }
        if (e.getSource() == p4Cor)
        {
            descricao.setText("Altera a cor do Jogador 4.");
        }
        //Erros estranhos acontecem com estes elementos dependendo do ponteiro do mouse estar
        //sobre algum deles JUSTAMENTE quando se inicializa o projeto.
//        if (e.getSource() == p1Vel)
//        {
//            descricao.setText("Altera a velocidade do Jogador 1 (milisegundos).");
//        }
//        if (e.getSource() == p2Vel)
//        {
//            descricao.setText("Altera a velocidade do Jogador 2 (milisegundos).");
//        }
//        if (e.getSource() == p3Vel)
//        {
//            descricao.setText("Altera a velocidade do Jogador 3 (milisegundos).");
//        }
//        if (e.getSource() == p4Vel)
//        {
//            descricao.setText("Altera a velocidade do Jogador 4 (milisegundos).");
//        }
//        if (e.getSource() == p1Size)
//        {
//            descricao.setText("Altera o tamanho do Jogador 1 (pixels).");
//        }
//        if (e.getSource() == p2Size)
//        {
//            descricao.setText("Altera o tamanho do Jogador 2 (pixels).");
//        }
//        if (e.getSource() == p3Size)
//        {
//            descricao.setText("Altera o tamanho do Jogador 3 (pixels).");
//        }
//        if (e.getSource() == p4Size)
//        {
//            descricao.setText("Altera o tamanho do Jogador 4 (pixels).");
//        }
        if (e.getSource() == iniciar)
        {
            descricao.setText("Clique para iniciar uma partida com as configurações atuais.");
        }
    }
    public void mouseExited(MouseEvent e)
    {
        descricao.setText("");
    }
}