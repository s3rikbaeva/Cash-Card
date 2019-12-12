package sample;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import java.io.*;
import java.util.*;
import javafx.scene.text.*;
import java.util.Scanner;

public class Main extends Application{
    VBox vboxPnum;
    Pane pane ;
    Stage Stage;
    public static PriceType type; //Has-a
    public static Price pTenge ;
    public static Price pDollar;
    public static PrePrice price;
    public static HashMap<Person, Long> numbers;
    Person newPerson ;
    Collection<Person> persons;

    @Override
    public void start(Stage primaryStage) throws Exception{
        startProject();
    }


    public void Card()  throws FileNotFoundException,Exception{
        Stage stage1 =new Stage();
        Button btFound=new Button("Found");
        btFound.setOnAction(q -> System.exit(1));
        String pnumbers="";
        try(FileReader reader = new FileReader("C:\\Users\\Admin\\Desktop\\cash.txt")){
            int c;
            while((c=reader.read())!=-1){
                pnumbers+=(char)c;
            }
            Label text = new Label("You can contact them");
            Label attention = new Label("If you haven't found the money then leave your details, we will call you back.");
            Label name = new Label("      Your name:");
            TextField getName = new TextField();
            getName.setStyle("-fx-border-color: green");
            Label phone = new Label("Phone number:");
            TextField getPhone = new TextField();
            getPhone.setStyle("-fx-border-color: green");
            Label price = new Label("                Price:");
            TextField getPrice = new TextField();
            String textOfPrice = getPrice.getText();
            for(int i = 0;i < textOfPrice.length() ; i++){
                if((textOfPrice.charAt(i)+"").contains("tg")){
                    pTenge = new Tenge(textOfPrice);
                    type = new PriceType(pTenge.typeOfPrice());
                }
                else if((textOfPrice.charAt(i)+"").contains("$")){
                    pDollar = new Dollar(textOfPrice);
                    type = new PriceType(pDollar.typeOfPrice());
                }
                else{
                    throw new Exception("This measure can't found. Sorry :(");
                }
            }
            getPrice.setStyle("-fx-border-color: green");
            HBox hboxPrice=new HBox(10,price,getPrice);
            hboxPrice.setAlignment(Pos.CENTER);
            HBox hboxName=new HBox(10,name,getName);
            hboxName.setAlignment(Pos.CENTER);
            HBox hboxPhone=new HBox(10,phone,getPhone);
            hboxPhone.setAlignment(Pos.CENTER);


            sortPrice(textOfPrice);
//for finding Person if we have this Person
           // Optional<Person> optionalPerson = Optional.of(newPerson.name);

            text.setFont(Font.font("Pirou",FontWeight.BOLD, FontPosture.REGULAR,20));
            attention.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.REGULAR,20));

            Button btLogin = new Button("Done");
            btLogin.setOnAction(a -> {
                String info = "";
                if(Long.parseLong(getPrice.getText())<=100000) {
                    info += getName.getText() + " " + getPhone.getText() + " " + getPrice.getText();
                    numbers.put(newPerson, Long.parseLong(getPhone.getText()));
                    persons= Arrays.asList(
                            new Person(getName.getText(), Integer.parseInt(getPhone.getText())),
                            new Person(getName.getText(), Integer.parseInt(getPhone.getText())),
                            new Person(getName.getText(), Integer.parseInt(getPhone.getText()))
                    );
                    String filePath = "card.txt";
                    try {
                        FileWriter writer = new FileWriter(filePath, true);
                        BufferedWriter bufferWriter = new BufferedWriter(writer);
                        bufferWriter.write("\n" + info);
                        bufferWriter.close();
                        System.exit(1);
                    } catch (IOException m) {
                        System.out.println(m);
                    }
                }
                else{
                    getPrice.setStyle("-fx-border-color: red");
                }
            });
            String personsName = persons.stream().filter((p) -> p.getName() != null ).max((p1, p2)
            -> p1.getMoney().compareTo(p2.getMoney())).get().getName();


            Button back = new Button("Back");
            HBox hbBack = new HBox(back);
            back.setOnAction(a -> {
                try{
                    startProject();
                    stage1.close();
                }
                catch(Exception ex){
                    ex.getMessage();
                }


            });
            hbBack.setPadding(new Insets(30,25, 10, 15));
            Label pnum = new Label(pnumbers);
            vboxPnum=new VBox(15,text,pnum,btFound,attention,hboxPhone,hboxName,hboxPrice,btLogin, hbBack);
            vboxPnum.setAlignment(Pos.CENTER);
            vboxPnum.setStyle("-fx-background-color: WHITE");
            Scene scene = new Scene(vboxPnum,700,500);
            stage1.setScene(scene);
            stage1.setTitle("SDUBank");
            stage1.show();



        }catch(FileNotFoundException ex){
            PrintWriter newFile = new PrintWriter("card.txt" , "utf-8");
            for(int i = 0 ; i< numbers.size() ; i++){
                newFile.write(numbers.get(i)+ " ");
            }
            newFile.close();
        }
    }
    public void startProject() throws Exception{
        pane = new Pane();
        Stage = new Stage();
        Text welcome = new Text(100,80,"Welcome to SDU&cash!");
        welcome.setFont(Font.font("Courier", FontWeight.BOLD, 30));
        welcome.setStroke(Color.BLACK);
        welcome.setFill(Color.BLACK);


        VBox vbox = new VBox(15);
        CheckBox checkCard = new CheckBox("Card");
        Font cardFont = new Font("Impact",  20);
        checkCard.setFont(cardFont);
        CheckBox checkCash = new CheckBox("Cash");
        Font cardCash = new Font("Impact", 20);
        checkCash.setFont(cardCash);
        vbox.setPadding(new Insets(170, 200, 200, 220));
        vbox.getChildren().addAll(checkCard,checkCash);


        FileInputStream inputStream = new FileInputStream("C:\\Users\\Admin\\Desktop\\5-cash.gif");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(500);
        imageView.setFitWidth(700);
        pane.getChildren().addAll(imageView,welcome, vbox);

        checkCard.setOnAction(e -> {
            try{
                Card();
            }
            catch (Exception ex){
                ex.getMessage();
            }
            Stage.close();
        });
        checkCash.setOnAction(e -> {
            try{
                Cash();
            }
            catch (Exception ex){
                ex.getMessage();
            }
            Stage.close();
        });
        Scene scene = new Scene(pane,550,400);
        Stage.setScene(scene);
        Stage.setTitle("Bank");
        Stage.setResizable(false);
        Stage.show();

    }
    public void Cash() throws Exception{
        Stage stage2 =new Stage();
        Button btFound=new Button("Found");
        btFound.setOnAction(q ->System.exit(1));
        String pnumbers="";
        try(FileReader reader = new FileReader("C:\\Users\\Admin\\Desktop\\card.txt")){
            int c;
            while((c=reader.read())!=-1){
                pnumbers+=(char)c;
            }Label text = new Label("You can contact them");
            Label attention = new Label("If you haven't found the money then leave your details, we will call you back.");
            Label name = new Label("      Your name:");
            TextField getName = new TextField();
            getName.setStyle("-fx-border-color: green");
            Label phone = new Label("Phone number:");
            TextField getPhone = new TextField();
            getPhone.setStyle("-fx-border-color: green");
            Label price = new Label("               Price:");
            TextField getPrice = new TextField();
            getPrice.setStyle("-fx-border-color: green");
            HBox hboxPrice=new HBox(10,price,getPrice);
            hboxPrice.setAlignment(Pos.CENTER);
            HBox hboxName=new HBox(10,name,getName);
            hboxName.setAlignment(Pos.CENTER);
            HBox hboxPhone=new HBox(10,phone,getPhone);
            hboxPhone.setAlignment(Pos.CENTER);

            text.setFont(Font.font("Pirou",FontWeight.BOLD, FontPosture.ITALIC,20));
            attention.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.REGULAR,20));
            Button btLogin = new Button("Done");
            Button back = new Button("Back");

            btLogin.setOnAction(a -> {
                        String info = "";
                        if (Long.parseLong(getPrice.getText()) <= 100000) {
                            info += getName.getText() + " " + getPhone.getText() + " " + getPrice.getText();
                            numbers.put(newPerson, Long.parseLong(getPhone.getText()));
                            String filePath = "C:\\Users\\Admin\\Desktop\\cash.txt";
                            try {
                                FileWriter writer = new FileWriter(filePath, true);
                                BufferedWriter bufferWriter = new BufferedWriter(writer);
                                bufferWriter.write("\n" + info);
                                bufferWriter.close();
                            } catch (IOException m) {
                                System.out.println(m);
                            }
                            System.exit(1);
                        }
                        else{
                            getPrice.setStyle("-fx-border-color: red");
                        }
                    }
            );
            //SQL crate table
            back.setOnAction(a -> {
                try{
                    startProject();
                    stage2.close();
                }
                catch(Exception ex){
                    ex.getMessage();
                }
            });
            Label pnum = new Label(pnumbers);
            HBox hbBack = new HBox(back);
            hbBack.setPadding(new Insets(30,25, 10, 15));
            vboxPnum=new VBox(15,text,pnum,btFound,attention,hboxPhone,hboxName,hboxPrice,btLogin, hbBack);
            vboxPnum.setAlignment(Pos.CENTER);
            vboxPnum.setStyle("-fx-background-color: WHITE");
            Scene scene = new Scene(vboxPnum,700,500);
            stage2.setScene(scene);
            stage2.setTitle("SDUBank");
            stage2.show();


        }catch(IOException ex){
            System.out.println(ex.getMessage());

        }

    }


    public void sortPrice(String price){
        class PriceComparator implements Comparator<String>{
            public int compare(String longNumber1,String longNumber2){
                int k = longNumber1.length() - longNumber2.length();
                return k;
            }
        }
    }




    public static void main(String[] args) {
        launch(args);
    }
}
