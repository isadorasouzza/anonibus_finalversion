package projetofakenews;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Treinamento {
    public void CapturaTweet() throws TwitterException, InterruptedException, IOException{
        String postagem = null;
        int counttweets = 0;
        int qtdtweets = 20;
        boolean pausa = false;
        int posicao = 0;
        
        ArrayList<String> tags = new ArrayList();
            Collections.addAll(tags, "corona", "covid", "quarentena");

        
        ConfigurationBuilder cb = new ConfigurationBuilder();
                             cb.setDebugEnabled(true)
                               .setOAuthConsumerKey("5ghCjdJ3WMmrpKqcRVh7ZJiQr")
                               .setOAuthConsumerSecret("r0XlKhQQ2GZFtM8L9RU7lxc2qebAjetZugo3o9ZHtMIdJLJDr8")
                               .setOAuthAccessToken("1177476709-nUPM1SoN4lv8I1qJTELGeduWezuKgN2DGovfn9y")
                               .setOAuthAccessTokenSecret("BGzoLW9noV8txGij56D5TmRqzRB9oBrHPY2V3B0TTsRyM");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = tf.getInstance();                     
       
        
        String data_inicial = "2020-10-01";
        String data_final = "2020-10-10"; 
        
        try {
            Query query = new Query(tags.get(0));
            query.setSince(data_inicial);
            query.setUntil(data_final);
            query.setLang("pt");
            query.setCount(qtdtweets);
        
            QueryResult result = null;
            result = twitter.search(query);
            do {
                List<Status> tweets = result.getTweets();
                FileWriter arquivo = new FileWriter("src/projetofakenews/teste.arff");
                PrintWriter gravarArquivo = new PrintWriter (arquivo);
                gravarArquivo.println(
                                        "@relation fakenews \n \n"
                                        + "@attribute tweet string \n"
                                        + "@attribute fake {false, true} \n \n"
                                        + "@data \n");
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                    if (tweet.getRetweetedStatus() != null) {
                        postagem = tweet.getRetweetedStatus().getText();
                    } else if (tweet.getRetweetedStatus() == null) {
                               postagem = tweet.getText();
                    }
                    
                    gravarArquivo.append(postagem.replace(" ","-")
                                        +",\n\n\n\n");
                    counttweets++;
                    
                    //if (counttweets == qtdtweets)
                        //pausa=true;

                }
                 query = result.nextQuery();

                 posicao++;
                 if (posicao < tags.size()) {
                     query = new Query(tags.get(posicao));
                     query.setSince(data_inicial);
                     query.setUntil(data_final);
                     query.setCount(qtdtweets);
                     query.setLang("pt");
                     result = twitter.search(query);
                  }
                gravarArquivo.close();
            } while (pausa!=true);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Falha ao capturar tweets: " + te.getMessage());
            System.exit(-1);
        }
            
   }
}