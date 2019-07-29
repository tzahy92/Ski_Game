package gui;

import factory.ArenaFactory;
import factory.RaceBuilder;
import factory.SkiCompetitionBuilder;
import game.arena.IArena;
import game.arena.WinterArena;
import game.competition.Competitor;
import game.competition.WinterCompetition;
import game.entities.decorator.SpeedySpotsman;
import game.entities.sportsman.WinterSportsman;
import game.enums.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author itzhak maymon - 204278790
 * @author Nicolas roizman - 321902306
 * A representation of a cartesian gui class MainPanel extends {@link JFrame} and implements {@link ActionListener}
 */
public class MainPanel extends JFrame implements ActionListener, Observer {


    private static RaceBuilder builder = RaceBuilder.getInstance();
    private static SkiCompetitionBuilder defaultSkiCompetition = SkiCompetitionBuilder.getInstance();

    //private JButton BuildArena;
    private JComboBox<SnowSurface> comboSnowSurface;
    private JComboBox<WeatherCondition> comboWeatherCondition;
    private JTextField ArenaLength;
    private JComboBox<String> comboArena;

    //Arena panel
    private String weather = null;
    private int length = 700;
    private int wide = 1000;

    private ArenaFactory arenaFactory = new ArenaFactory();

    //Competition panel
    private int maxComp = 10;
    private JComboBox<String> comboChooseCompetition;
    private JTextField MaxNumber;
    private JComboBox<Discipline> comboDiscipline;
    private JComboBox<League> comboLesgue;
    private JComboBox<Gender> comboGender;


    //Competitor panel
    private JTextField textName;
    private JTextField textAge;
    private JTextField textMaxSpeed;
    private JTextField textAcceleration;
    private JTextField textCompetitorNumber;
    private JComboBox<String> comboColor;
    private ImageIcon imageSporsman[] = null;
    private int sportsmanNumber;
    private String strColor;


    //Race start
    private boolean raceStarted = false;
    private boolean raceFinished = false;

    //Build Arena
    private IArena arena=null;

    //Build Competition
    private Gender gender = null;
    private Discipline discipline = null;
    private String chosenCompetition = null;
    private WinterCompetition comp = null;

    //Build Competitor
    private ArrayList<WinterSportsman> sportsmen;
    private WinterSportsman racer;
    private String name;
    private double age;
    private double maxSpeed;
    private double acceleration;
    private String sportsmanGeneder;
    private int competitorNumber;
    private Color color;

    private SpeedySpotsman addAcceleration= null;
    //info
    private JFrame infoTable = null;

    /**
     * ctor of MainPanel, build the frame of the gui
     */
    public MainPanel() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel());
        this.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        this.setLocation(x, y);
        this.setVisible(true);

    }

    /**
     * control the click button command:
     * Build arena: build the arena if the competition not started or the competition is finished
     * Create competition: build the competition if the arena already built and competition not started or the competition is finished
     * Default competition: build a competition if the arena already built and competition not started or the competition is finished and build and
     * add 10 competitor to the competition
     * Add competitor: build the competitor if the arena and the competition already built and competition not started or the competition is finished
     * Copy competitor: build the competitor if the arena and the competition already built, competition not started or the competition is finished and there is attlist 1 competitor and
     * copy the last competitor and add him to the competition
     * Add acceleration: add acceleration to the last competitor if the arena and the competition already built, competition not started or the competition is finished and there is attlist 1 competitor
     * Start competition: start the competition
     * Show info: show the info of all the competitor
     * @param e click button type
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Build arena":
                String arenaType;
                if (raceStarted && !raceFinished) {
                    JOptionPane.showMessageDialog(this, "competition started! Please wait.");
                    return;
                }
                try {
                    length = Integer.parseInt(ArenaLength.getText());
                    arenaType = (String) comboArena.getSelectedItem();
                    if (length < 700 || length > 900)
                        throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.");
                    return;
                }

                weather = (comboWeatherCondition.getSelectedItem()).toString();
                switch (weather) {

                    case "SUNNY":
                        weather = "Sunny";
                        break;
                    case "CLOUDY":
                        weather = "Cloudy";
                        break;
                    case "STORMY":
                        weather = "Stormy";
                        break;
                }

                arena = arenaFactory.getArena(arenaType,length, (SnowSurface) comboSnowSurface.getSelectedItem(), (WeatherCondition) comboWeatherCondition.getSelectedItem());
                if(comp != null)
                    comp = null;
                if(sportsmen == null || !(sportsmen.isEmpty())) {
                    sportsmen = new ArrayList<>();
                    racer = null;
                    sportsmanNumber = 0;
                    imageSporsman = null;
                }
                updateFrame();
                break;

            case "Create competition":

                if (arena == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena, creat competition and add competitors!");
                    return;
                }
                if (raceStarted && !raceFinished) {
                    JOptionPane.showMessageDialog(this, "competition started! Please wait.");
                    return;
                }
                try {
                    maxComp = Integer.parseInt(MaxNumber.getText());
                    if (maxComp < 1 || maxComp > 20)
                        throw new NumberFormatException();
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(this, "Invalid input values: please try again.");
                    return;
                }
                sportsmanNumber = 0;
                raceStarted = raceFinished = false;
                imageSporsman = new ImageIcon[maxComp];
                sportsmen = new ArrayList<>();
                gender = (Gender) comboGender.getSelectedItem();
                discipline = (Discipline) comboDiscipline.getSelectedItem();
                chosenCompetition = (String) comboChooseCompetition.getSelectedItem();
                wide = 1000;
                //comp = new WinterCompetition(arena,maxComp,discipline,(League) comboLesgue.getSelectedItem(),gender);
                try {
                    if (chosenCompetition.equals("Ski")) {
                        comp = builder.buildCompetition("game.competition.SkiCompetition", arena, maxComp, discipline, (League) comboLesgue.getSelectedItem(), gender);
                    } else if (chosenCompetition.equals("Snowboard")) {
                        comp = builder.buildCompetition("game.competition.SnowboardCompetition", arena, maxComp, discipline, (League) comboLesgue.getSelectedItem(), gender);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                if(wide < (maxComp+1) * 70)
                    this.wide =(maxComp + 1) * 70;
                updateFrame();
                break;

            case "Default competition":

                if (arena == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena, creat competition and add competitors!");
                    return;
                }
                if (raceStarted && !raceFinished) {
                    JOptionPane.showMessageDialog(this, "competition started! Please wait.");
                    return;
                }
                try {
                    maxComp = Integer.parseInt(MaxNumber.getText());
                    if (maxComp < 1 || maxComp > 20)
                        throw new NumberFormatException();
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(this, "Invalid input values: please try again.");
                    return;
                }
                maxComp = 10;
                sportsmanNumber = 0;
                raceStarted = raceFinished = false;
                imageSporsman = new ImageIcon[maxComp];
                sportsmen = new ArrayList<>();
                wide = 1000;
                try {
                    comp = defaultSkiCompetition.buildCompetition(arena);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                for(Competitor c : comp.getActiveCompetitors()){
                    sportsmen.add((WinterSportsman) c);
                    ((WinterSportsman) c).addObserver(this);
                    ((WinterSportsman) c).addObserver(comp);
                    imageSporsman[sportsmanNumber] = new ImageIcon(new ImageIcon("src/icons/SkiBlack.png").getImage()
                            .getScaledInstance(70, 70, Image.SCALE_DEFAULT));
                    sportsmanNumber++;
                }
                updateFrame();
                break;
            case "Add competitor":

                if (arena == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena, creat competition and add competitors!");
                    return;
                }
                if (raceStarted && !raceFinished) {
                    JOptionPane.showMessageDialog(this, "competition started! Please wait.");
                    return;
                }
                if (comp == null) {
                    JOptionPane.showMessageDialog(this, "Please build competition and add competitors");
                    return;
                }
                if (sportsmen.size() >= maxComp) {
                    JOptionPane.showMessageDialog(this, "No more competitor can be added!");
                    return;
                }
                if(raceStarted && raceFinished){
                    JOptionPane.showMessageDialog(this, "competition finish! Please build new competition and add competitors.");
                    return;
                }

                try {
                    name = textName.getText();
                    age = Double.parseDouble(textAge.getText());
                    maxSpeed = Double.parseDouble(textMaxSpeed.getText());
                    acceleration = Double.parseDouble(textAcceleration.getText());
                    if (name.isEmpty() || maxSpeed <= 0 || acceleration <= 0 || age <= 0)
                        throw new Exception();
                    competitorNumber = Integer.parseInt(textCompetitorNumber.getText());
                    if(competitorNumber<0)
                        throw new Exception();
                    strColor = (String) comboColor.getSelectedItem();// doesnt get the color, color = null
                    switch (strColor){
                        case  "Blue":
                            color = Color.BLUE;
                            break;
                        case "Black":
                            color = Color.BLACK;
                            break;
                        case "Red":
                            color = Color.RED;
                            break;
                        case "Green":
                            color = Color.GREEN;
                            break;
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.");
                    return;
                }
                for(int i=0;i<sportsmanNumber;i++) {
                    if (competitorNumber == sportsmen.get(i).getSportsmanNumber()) {
                        JOptionPane.showMessageDialog(this, "Invalid input values! Competitor number already exist.");
                        return;
                    }
                }

                try {
                    if (chosenCompetition.equals("Ski")) {
                        racer = builder.buildSportman("game.entities.sportsman.Skier", name, age, gender, maxSpeed, acceleration, sportsmanNumber, color, discipline);
                    } else if (chosenCompetition.equals("Snowboard")) {
                        racer = builder.buildSportman("game.entities.sportsman.Snowboarder", name, age, gender, maxSpeed, acceleration, sportsmanNumber, color, discipline);
                    }
                } catch (IllegalArgumentException e1) {
                    JOptionPane.showMessageDialog(this, "competitor does not fit to competition! Choose another competitor.");
                    return;
                } catch (Exception e2) {
                    System.out.println(e2);
                }
                comp.addCompetitor(racer);
                racer.addObserver(this);
                racer.addObserver(comp);
                sportsmen.add(racer);
                if (gender == Gender.FEMALE)
                    sportsmanGeneder = "Female";
                else
                    sportsmanGeneder = "Male";
                imageSporsman[sportsmanNumber] = new ImageIcon(new ImageIcon("src/icons/"+chosenCompetition+strColor+".png").getImage()
                        .getScaledInstance(70, 70, Image.SCALE_DEFAULT));
                sportsmanNumber++;
                updateFrame();
                break;

            case "Copy competitor":

                if (arena == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena, creat competition and add competitors!");
                    return;
                }
                if (raceStarted && !raceFinished) {
                    JOptionPane.showMessageDialog(this, "competition started! Please wait.");
                    return;
                }
                if (comp == null) {
                    JOptionPane.showMessageDialog(this, "Please build competition and add competitors");
                    return;
                }
                if (sportsmen.size() >= maxComp) {
                    JOptionPane.showMessageDialog(this, "No more competitor can be added!");
                    return;
                }
                if(raceStarted && raceFinished){
                    JOptionPane.showMessageDialog(this, "competition finish! Please build new competition and add competitors.");
                    return;
                }
                if(sportsmanNumber <= 0) {
                    JOptionPane.showMessageDialog(this, "Dont have competitor to copy from, build the first competitor.");
                    return;
                }
                try{
                    competitorNumber = Integer.parseInt(textCompetitorNumber.getText());
                    if(competitorNumber<0)
                        throw new Exception();
                    strColor = (String) comboColor.getSelectedItem();// doesnt get the color, color = null
                    switch (strColor){
                        case  "Blue":
                            color = Color.BLUE;
                            break;
                        case "Black":
                            color = Color.BLACK;
                            break;
                        case "Red":
                            color = Color.RED;
                            break;
                        case "Green":
                            color = Color.GREEN;
                            break;
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(this, "Invalid input values! Please insert new competitor number.");
                    return;
                }
                for(int i=0;i<sportsmanNumber;i++) {
                    if (competitorNumber == sportsmen.get(i).getSportsmanNumber()) {
                        JOptionPane.showMessageDialog(this, "Invalid input values! Competitor number already exist.");
                        return;
                    }
                }
                WinterSportsman copyRacer = null;
                try {
                    copyRacer = racer.clone();
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
                copyRacer.setColor(color);
                copyRacer.setSportsmanNumber(competitorNumber);
                racer = copyRacer;
                copyRacer.addObserver(this);
                copyRacer.addObserver(comp);
                comp.addCompetitor(copyRacer);
                sportsmen.add(copyRacer);
                imageSporsman[sportsmanNumber] = new ImageIcon(new ImageIcon("src/icons/"+chosenCompetition+strColor+".png").getImage()
                        .getScaledInstance(70, 70, Image.SCALE_DEFAULT));
                sportsmanNumber++;
                updateFrame();
                break;

            case "Add acceleration":
                if (arena == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena, creat competition and add competitors!");
                    return;
                }
                if (raceStarted && !raceFinished) {
                    JOptionPane.showMessageDialog(this, "competition started! Please wait.");
                    return;
                }
                if (comp == null) {
                    JOptionPane.showMessageDialog(this, "Please build competition and add competitors");
                    return;
                }
                if (sportsmen.size() >= maxComp) {
                    JOptionPane.showMessageDialog(this, "No more competitor can be added!");
                    return;
                }
                if(raceStarted && raceFinished){
                    JOptionPane.showMessageDialog(this, "competition finish! Please build new competition and add competitors.");
                    return;
                }
                if(sportsmanNumber <= 0) {
                    JOptionPane.showMessageDialog(this, "Dont have competitor to add him acceleration, build the first competitor.");
                    return;
                }
                double acc = 0;
                try {
                    acc = Double.parseDouble(textAcceleration.getText());
                    if(acc <= 0)
                        throw new Exception();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(this, "Invalid input values! Please insert positive acceleration.");
                }
                addAcceleration = new SpeedySpotsman(racer);
                addAcceleration.addAccel(acc);

                updateFrame();
                break;

            case "Start competition":

                if (arena == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena, creat competition and add competitors!");
                    return;
                }
                if (comp == null) {
                    JOptionPane.showMessageDialog(this, "Please build competition first and add competitors!");
                    return;
                }
                if(sportsmen.size() == 0){
                    JOptionPane.showMessageDialog(this, "Please add competitors!");
                    return;
                }
                if (raceFinished) {
                    JOptionPane.showMessageDialog(this, "competition finished! Please build a new arena, competition and add competitor.");
                    return;
                }
                if (raceStarted) {
                    JOptionPane.showMessageDialog(this, "competition already started!");
                    return;
                }
                try {
                    raceStarted = true;
                    comp.startRace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                raceFinished = true;

                break;

            case "Show info":

                if(arena == null){
                    JOptionPane.showMessageDialog(this, "Please build arena, creat competition and add competitors!");
                    return;
                }
                if (comp == null) {
                    JOptionPane.showMessageDialog(this, "Please build competition first and add competitors!");
                    return;
                }
                if(sportsmen.size() == 0){
                    JOptionPane.showMessageDialog(this, "Please add competitors!");
                    return;
                }
                String[] columnNames = { "Name", "Speed", "Max speed","Acceleration", "Location", "Finished" };
                String[][] data = new String[sportsmen.size()][6];
                int i = 0;
                for (Competitor s : comp.getFinishedCompetitors()) {
                    data[i][0] = ((WinterSportsman)s).getName();
                    data[i][1] = "" + ((WinterSportsman)s).getSpeed();
                    data[i][2] = "" + ((WinterSportsman)s).getMaxSpeed();
                    data[i][3] = "" + ((WinterSportsman)s).getAcceleration();
                    data[i][4] = "" + s.getLocation().getY();
                    data[i][5] = "Yes";
                    i++;
                }

                for (Competitor s : comp.getActiveCompetitors()) {
                    data[i][0] = ((WinterSportsman)s).getName();
                    data[i][1] = "" + ((WinterSportsman)s).getSpeed();
                    data[i][2] = "" + ((WinterSportsman)s).getMaxSpeed();
                    data[i][3] = "" + ((WinterSportsman)s).getAcceleration();
                    data[i][4] = "" + s.getLocation().getY();
                    data[i][5] = "No";
                    i++;
                }

                for (Competitor s : comp.getInjuredCompetitors()) {
                    data[i][0] = ((WinterSportsman)s).getName();
                    data[i][1] = "" + ((WinterSportsman)s).getSpeed();
                    data[i][2] = "" + ((WinterSportsman)s).getMaxSpeed();
                    data[i][3] = "" + ((WinterSportsman)s).getAcceleration();
                    data[i][4] = "" + s.getLocation().getY();
                    data[i][5] = "Injured";
                    i++;
                }

                for (Competitor s : comp.getDisabledCompetitors()) {
                    data[i][0] = ((WinterSportsman)s).getName();
                    data[i][1] = "" + ((WinterSportsman)s).getSpeed();
                    data[i][2] = "" + ((WinterSportsman)s).getMaxSpeed();
                    data[i][3] = "" + ((WinterSportsman)s).getAcceleration();
                    data[i][4] = "" + s.getLocation().getY();
                    data[i][5] = "Disabled";
                    i++;
                }

                JTable table = new JTable(data, columnNames);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                JScrollPane scrollPane = new JScrollPane(table);

                JPanel tabPan = new JPanel();
                tabPan.add(scrollPane);

                if (infoTable != null)
                    infoTable.dispose();
                infoTable = new JFrame("Racers information");
                infoTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                infoTable.setContentPane(tabPan);
                infoTable.pack();
                infoTable.setVisible(true);

                break;
        }
    }

    /**
     * build the arena setting panel
     * @return JPanel of the arena setting
     */
    private JPanel BuildArena(){
        this.comboSnowSurface = new JComboBox<>(SnowSurface.values());
        this.comboWeatherCondition = new JComboBox<>(WeatherCondition.values());
        this.ArenaLength = new JTextField(""+length);
        JButton BuildArena = new JButton("Build arena");
        BuildArena.addActionListener(this);
        JPanel ArenaPanel = new JPanel();
        ArenaPanel.setLayout(new GridLayout(0,1));
        JLabel title = new JLabel("BUILD ARENA");
        title.setForeground(Color.BLUE);
        Font F = title.getFont();
        Map attributes = F.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        title.setFont(F.deriveFont(attributes));
        ArenaPanel.add(title);
        ArenaPanel.add(new JLabel("Arena length"));
        ArenaPanel.add(ArenaLength);
        ArenaPanel.add(new JLabel("Snow surface"));
        ArenaPanel.add(comboSnowSurface);
        ArenaPanel.add(new JLabel("Weather condition"));
        ArenaPanel.add(comboWeatherCondition);
        ArenaPanel.add(new JLabel("Arena type"));
        comboArena = new JComboBox<>(new String[]{"Summer","Winter"});
        ArenaPanel.add(comboArena);
        ArenaPanel.add(BuildArena);
        ArenaPanel.setPreferredSize(new Dimension(100,168));
        ArenaPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        return ArenaPanel;
    }

    /**
     * build the competition setting panel.
     * @return JPanel of the competition setting
     */
    private JPanel BuildCompetition(){
        this.comboChooseCompetition = new JComboBox<>(new String[]{"Ski","Snowboard"});
        this.MaxNumber = new JTextField("" + maxComp);;
        this.comboDiscipline = new JComboBox<>(Discipline.values());
        this.comboLesgue = new JComboBox<>(League.values());
        this.comboGender = new JComboBox<>(Gender.values());
        JButton creatCompetition = new JButton("Create competition");
        creatCompetition.addActionListener(this);
        JButton creatDefaultCompetition = new JButton("Default competition");
        creatDefaultCompetition.addActionListener(this);
        JPanel panelCompetition = new JPanel();
        panelCompetition.setLayout(new GridLayout(0,1));
        JLabel title = new JLabel("CREATE COMPETITION");
        title.setForeground(Color.BLUE);
        Font F = title.getFont();
        Map attributes = F.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        title.setFont(F.deriveFont(attributes));
        panelCompetition.add(title);
        panelCompetition.add(new JLabel("Choose competition"));
        panelCompetition.add(comboChooseCompetition);
        panelCompetition.add(new JLabel("Max competitors number"));
        panelCompetition.add(MaxNumber);
        panelCompetition.add(new JLabel("Discipline"));
        panelCompetition.add(comboDiscipline);
        panelCompetition.add(new JLabel("League"));
        panelCompetition.add(comboLesgue);
        panelCompetition.add(new JLabel("Gender"));
        panelCompetition.add(comboGender);
        panelCompetition.add(creatCompetition,BorderLayout.SOUTH);
        panelCompetition.add(creatDefaultCompetition,BorderLayout.SOUTH);
        panelCompetition.setPreferredSize(new Dimension(100,225));
        panelCompetition.setBorder(BorderFactory.createLoweredBevelBorder());
        return panelCompetition;
    }

    /**
     * build the competitor setting panel
     * @return JPanel of the competitor setting
     */
    private JPanel BuildCompetitor(){
        textName = new JTextField();
        textAge = new JTextField();
        textMaxSpeed = new JTextField();
        textAcceleration = new JTextField();
        textCompetitorNumber = new JTextField();
        comboColor = new JComboBox<>(new String[]{"Blue","Black","Red","Green"});
        JButton AddCompetitor = new JButton("Add competitor");
        AddCompetitor.addActionListener(this);
        JButton CopyCompetitor = new JButton("Copy competitor");
        CopyCompetitor.addActionListener(this);
        JButton addAcceleration = new JButton("Add acceleration");
        addAcceleration.addActionListener(this);
        JPanel panelCompetitor = new JPanel();
        panelCompetitor.setLayout(new GridLayout(0,1));
        JLabel title = new JLabel("ADD COMPETITOR");
        title.setForeground(Color.BLUE);
        Font F = title.getFont();
        Map attributes = F.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        title.setFont(F.deriveFont(attributes));
        panelCompetitor.add(title);
        panelCompetitor.add(new JLabel("Name"));
        panelCompetitor.add(textName);
        panelCompetitor.add(new JLabel("Age"));
        panelCompetitor.add(textAge);
        panelCompetitor.add(new JLabel("Max speed"));
        panelCompetitor.add(textMaxSpeed);
        panelCompetitor.add(new JLabel("Acceleration"));
        panelCompetitor.add(textAcceleration);
        panelCompetitor.add(new JLabel("Number"));
        panelCompetitor.add(textCompetitorNumber);
        panelCompetitor.add(new JLabel("Color"));
        panelCompetitor.add(comboColor);
        panelCompetitor.add(AddCompetitor);
        panelCompetitor.add(CopyCompetitor);
        panelCompetitor.add(addAcceleration);
        panelCompetitor.setPreferredSize(new Dimension(100,260));
        panelCompetitor.setBorder(BorderFactory.createLoweredBevelBorder());
        return panelCompetitor;
    }

    /**
     * build the button of start competition and the button of info of the competitor
     * @return JPanel of the start and info button
     */
    private JPanel getStartInfo(){
        JButton start = new JButton("Start competition");
        start.addActionListener(this);
        JButton info = new JButton("Show info");
        info.addActionListener(this);
        JPanel startInfo = new JPanel();
        startInfo.setLayout(new GridLayout(0,1));
        startInfo.add(start);
        startInfo.add(info);
        startInfo.setPreferredSize(new Dimension(100,18));
        return startInfo;
    }

    /**
     *
     * @return JPanel of the setting panel of the arena, competition,competitor and startInfo panel
     */
    private JPanel controlPanel(){
        JPanel setingPanel = new JPanel();
        setingPanel.setLayout(new BoxLayout(setingPanel,BoxLayout.Y_AXIS));
        setingPanel.setPreferredSize(new Dimension(150,700));
        setingPanel.add(BuildArena());
        setingPanel.add(BuildCompetition());
        setingPanel.add(BuildCompetitor());
        setingPanel.add(getStartInfo());
        return setingPanel;
    }

    /**
     *
     * @return JPanel of the setting panel with the arena gui
     */
    private JScrollPane mainPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(wide,length + 70));
        mainPanel.add(controlPanel(),BorderLayout.EAST);
        mainPanel.add(Canvas(),BorderLayout.WEST);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(wide+30,length + 73));
        return scrollPane;
    }

    /**
     * build the frame of the arena and place the competitor on the arena frame in their location
     * @return JPanel of the arena gui with the competitor
     */
    private JPanel Canvas(){
        ImageIcon background = new ImageIcon(new ImageIcon("src/icons/"+weather+".jpg").getImage().getScaledInstance(wide ,length + 70,Image.SCALE_DEFAULT));
        JLabel picLabel1 = new JLabel(background);
        picLabel1.setLocation(0, 0);
        picLabel1.setSize(wide , length + 70);
        JPanel canvas = new JPanel();
        canvas = new JPanel();
        for (int i = 0; i < sportsmanNumber; i++) {
            JLabel picLabel2 = new JLabel(imageSporsman[i]);
            picLabel2.setLocation((int) sportsmen.get(i).getLocation().getX(),
                    (int) sportsmen.get(i).getLocation().getY() + 5);
            picLabel2.setSize(70, 70);
            picLabel1.add(picLabel2);
        }
        canvas.add(picLabel1);
        return canvas;
    }

    /**
     * method that update the gui frame with new length arena,
     * number of the competitor and the progress of the competitors
     */
    public synchronized void updateFrame() {
        this.setContentPane(mainPanel());
        this.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        this.setLocation(x, y);
        this.setVisible(true);
    }

    /**
     * update the frame when the competitor move.
     * when all the competitor finish or disable we print a message
     * that show all the competitor that disable
     * @param o a competitor
     * @param arg the message what do do
     */
    @Override
    public void update(Observable o, Object arg) {
        this.updateFrame();
        if(comp.getActiveCompetitors().isEmpty() && arg.equals("")){
            StringBuilder disableComp = new StringBuilder();
            for (Competitor s : comp.getDisabledCompetitors()) {
                disableComp.append(((WinterSportsman) s).getName());
                disableComp.append(",");
            }
            if(!(disableComp.toString().isEmpty()))
                JOptionPane.showMessageDialog(this, "The competitors: "+disableComp+" are disables");
        }

    }
}
