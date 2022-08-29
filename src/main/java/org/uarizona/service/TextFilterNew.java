package org.uarizona.service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uarizona.dto.SentimentClassification;
import org.uarizona.dto.SentimentResult;
import org.uarizona.dto.TextFilterResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TextFilterNew {
    public static Path toFile = Path.of("C:\\Users\\YASH\\IdeaProjects\\TextFilter\\src\\main\\resources\\text.txt");
    public static String filePath = "C:\\Users\\YASH\\IdeaProjects\\TextFilter\\src\\main\\resources\\text.txt";

    Scanner file = new Scanner(new FileReader("C:\\Users\\YASH\\IdeaProjects\\TextFilter\\src\\main\\resources\\text.txt"));
    @Autowired
    TextFilterResult textFilterResult;

    public TextFilterNew() throws FileNotFoundException {
    }

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, lemma,ner,depparse, coref, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        String st = Files.readString(toFile);
        String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

        CoreDocument document = new CoreDocument(content);
        props.setProperty("coref.algorithm", "neural");
        pipeline.annotate(document);
        Annotation annotation = pipeline.process(content);

        // 1) Number of sentences
        int senCount = 0;
        for (CoreSentence sentence : document.sentences()) {
            senCount++;
        }
        System.out.println("Total Sentences: " + senCount + '\n');

        // 2) Number of words
        long wordCount = 0L;
        for (CoreSentence sentence : document.sentences()) {
            wordCount += Arrays.stream(sentence.text().split(" ")).count();
        }
        System.out.println("Total words in the document: " + wordCount + '\n');

        // 3) Number of nouns
        Map<String, Integer> nnpSet = new HashMap<>();
        for (CoreLabel token : document.tokens()) {
            if (token.tag().equals("NNP")) {
                if (!nnpSet.containsKey(token.word())) {
                    nnpSet.put(token.word(), 1);
                } else {
                    nnpSet.put(token.word(), nnpSet.get(token.word()) + 1);
                }
            }
        }
        System.out.println("Nouns and their counts: " + nnpSet + '\n');


        // 4) Document sentiment (Random Statistics)
        SentimentResult sentimentResult = new SentimentResult();
        SentimentClassification sentimentClass = new SentimentClassification();
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            // this is the parse tree of the current sentence
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
            String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

            sentimentClass.setVeryPositive((double) Math.round(sm.get(4) * 100d));
            sentimentClass.setPositive((double) Math.round(sm.get(3) * 100d));
            sentimentClass.setNeutral((double) Math.round(sm.get(2) * 100d));
            sentimentClass.setNegative((double) Math.round(sm.get(1) * 100d));
            sentimentClass.setVeryNegative((double) Math.round(sm.get(0) * 100d));

            sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
            sentimentResult.setSentimentType(sentimentType);
            sentimentResult.setSentimentClassification(sentimentClass);
        }

        System.out.println("Sentiment Score: " + sentimentResult.getSentimentScore());
        System.out.println("Sentiment Type: " + sentimentResult.getSentimentType());
        System.out.println("Very positive: " + sentimentResult.getSentimentClassification().getVeryPositive() + "%");
        System.out.println("Positive: " + sentimentResult.getSentimentClassification().getPositive() + "%");
        System.out.println("Neutral: " + sentimentResult.getSentimentClassification().getNeutral() + "%");
        System.out.println("Negative: " + sentimentResult.getSentimentClassification().getNegative() + "%");
        System.out.println("Very negative: " + sentimentResult.getSentimentClassification().getVeryNegative() + "%");
    }

}